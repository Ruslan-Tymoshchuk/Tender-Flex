package pl.com.tenderflex.service.impl;

import static java.time.LocalDate.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.com.tenderflex.dao.OfferRepository;
import pl.com.tenderflex.dto.OfferDetailsResponse;
import pl.com.tenderflex.dto.OfferResponse;
import pl.com.tenderflex.dto.Page;
import pl.com.tenderflex.dto.MapStructMapper;
import pl.com.tenderflex.dto.OfferDetailsRequest;
import pl.com.tenderflex.exception.ServiceException;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.service.FileStorageService;
import pl.com.tenderflex.service.OfferService;

@Service
public class OfferServiceImpl implements OfferService {

    @Value("${offers.contractor.per.page}")
    private Integer offersContractorPerPage;
    public static final String OFFER_RECEIVED_STATUS = "OFFER RECEIVED";
    public static final String OFFER_SENT_STATUS = "OFFER SENT";

    private final FileStorageService fileStorageService;
    private final MapStructMapper offerMapper;
    private final OfferRepository offerRepository;

    public OfferServiceImpl(FileStorageService fileStorageService, MapStructMapper offerMapper,
            OfferRepository offerRepository) {
        this.fileStorageService = fileStorageService;
        this.offerMapper = offerMapper;
        this.offerRepository = offerRepository;
    }

    @Override
    @Transactional
    public void createOffer(MultipartFile document, OfferDetailsRequest offerDetailsRequest, Integer bidderId) {
        Offer offer = offerMapper.offerDetailsRequestToOffer(offerDetailsRequest);
        offer.setDocumentName(document.getOriginalFilename());
        offer.setBidderId(bidderId);
        offer.setContractorStatus(OFFER_RECEIVED_STATUS);
        offer.setPublicationDate(now());
        offer.setBidderStatus(OFFER_SENT_STATUS);
        try {
            Integer offerId = offerRepository.create(offer).getId();
            fileStorageService.upload(document, bidderId, offerId);
        } catch (DataAccessException e) {
            throw new ServiceException("Error occurred when saving the offer", e);
        }
    }

    @Override
    public Page<OfferResponse> getOffersForContractor(Integer contractorId, Integer currentPage) {
        Integer amountOffers = currentPage * offersContractorPerPage;
        Integer amountOffersToSkip = (currentPage - 1) * 5;
        Integer allOffersAmount = offerRepository.countOffersByContractor(contractorId);
        Integer totalPages = 1;
        if (allOffersAmount >= offersContractorPerPage) {
            totalPages = allOffersAmount / offersContractorPerPage;
            if (allOffersAmount % offersContractorPerPage > 0) {
                totalPages++;
            }
        }
        try {
            return new Page<>(currentPage, totalPages,
                    offerRepository.getByContractor(contractorId, amountOffers, amountOffersToSkip).stream()
                            .map(offerMapper::offerToOfferResponse).toList());
        } catch (DataAccessException e) {
            throw new ServiceException("Error occurred when getting offers by contractor", e);
        }
    }

    @Override
    public OfferDetailsResponse getById(Integer offerId) {
        try {
            return offerMapper.offerToOfferDetailsResponse(offerRepository.getById(offerId));
        } catch (DataAccessException e) {
            throw new ServiceException("Error occurred when getting offer by id", e);
        }
    }

    @Override
    public List<OfferResponse> getOffersByBidder(Integer bidderId) {
        try {
            return offerRepository.getByBidder(bidderId).stream().map(offerMapper::offerToOfferResponse).toList();
        } catch (DataAccessException e) {
            throw new ServiceException("Error occurred when getting offers by bidder", e);
        }
    }
}