package pl.com.tenderflex.service.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.com.tenderflex.dao.OfferRepository;
import pl.com.tenderflex.dto.MapStructMapper;
import pl.com.tenderflex.dto.OfferDetailsRequest;
import pl.com.tenderflex.exception.ServiceException;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.service.FileStorageService;
import pl.com.tenderflex.service.OfferService;

@Service
public class OfferServiceImpl implements OfferService {

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
        offer.setBidderStatus(OFFER_SENT_STATUS);
        try {
            Integer offerId = offerRepository.create(offer).getId();
            fileStorageService.upload(document, bidderId, offerId);
        } catch (DataAccessException e) {
            throw new ServiceException("Error occurred when saving the offer", e);
        }
    }
}