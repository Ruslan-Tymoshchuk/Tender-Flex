package pl.com.tenderflex.service.impl;

import static java.time.LocalDate.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.ContactPersonRepository;
import pl.com.tenderflex.dao.OfferRepository;
import pl.com.tenderflex.dao.OrganizationRepository;
import pl.com.tenderflex.dao.TenderRepository;
import pl.com.tenderflex.model.ContactPerson;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Organization;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.mapstract.OfferMapper;
import pl.com.tenderflex.payload.request.AwardDecisionRequest;
import pl.com.tenderflex.payload.request.DecisionRequest;
import pl.com.tenderflex.payload.request.OfferDetailsRequest;
import pl.com.tenderflex.payload.request.RejectDecisionRequest;
import pl.com.tenderflex.payload.response.OfferDetailsResponse;
import pl.com.tenderflex.payload.response.OfferResponse;
import pl.com.tenderflex.service.OfferService;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferMapper offerMapper;
    private final OfferRepository offerRepository;
    private final TenderRepository tenderRepository;
    private final ContactPersonRepository contactPersonRepository;
    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public void createOffer(OfferDetailsRequest offerDetailsRequest, Integer bidderId) {
        Offer offer = offerMapper.offerDetailsRequestToOffer(offerDetailsRequest);
        Organization organization = offer.getOrganization();
        ContactPerson contactPerson = contactPersonRepository.create(organization.getContactPerson());
        organization.setContactPerson(contactPerson);
        organization = organizationRepository.create(organization);
        offer.setOrganization(organization);
        offer.setBidderId(bidderId);
        offer.setPublicationDate(now());
        offerRepository.create(offer, bidderId);
    }

    @Override
    public Page<OfferResponse> getOffersByBidder(Integer bidderId, Integer currentPage, Integer offersPerPage) {
        Integer amountOffersToSkip = (currentPage - 1) * offersPerPage;
        Integer allOffersAmount = offerRepository.countOffersByBidder(bidderId);
        Integer totalPages = 1;
        if (allOffersAmount >= offersPerPage) {
            totalPages = allOffersAmount / offersPerPage;
            if (allOffersAmount % offersPerPage > 0) {
                totalPages++;
            }
        }
        return new Page<>(currentPage, totalPages,
                offerRepository.getByBidder(bidderId, offersPerPage, amountOffersToSkip).stream()
                        .map(offerMapper::offerToOfferResponse).toList());
    }

    @Override
    public Page<OfferResponse> getOffersByContractor(Integer contractorId, Integer currentPage, Integer offersPerPage) {
        Integer amountOffersToSkip = (currentPage - 1) * offersPerPage;
        Integer allOffersAmount = offerRepository.countOffersByContractor(contractorId);
        Integer totalPages = 1;
        if (allOffersAmount >= offersPerPage) {
            totalPages = allOffersAmount / offersPerPage;
            if (allOffersAmount % offersPerPage > 0) {
                totalPages++;
            }
        }
        return new Page<>(currentPage, totalPages,
                offerRepository.getByContractor(contractorId, offersPerPage, amountOffersToSkip).stream()
                        .map(offerMapper::offerToOfferResponse).toList());
    }

    @Override
    public Page<OfferResponse> getOffersByTender(Integer tenderId, Integer currentPage, Integer offersPerPage) {
        Integer amountOffersToSkip = (currentPage - 1) * offersPerPage;
        Integer allOffersAmount = offerRepository.countOffersByTender(tenderId);
        Integer totalPages = 1;
        if (allOffersAmount >= offersPerPage) {
            totalPages = allOffersAmount / offersPerPage;
            if (allOffersAmount % offersPerPage > 0) {
                totalPages++;
            }
        }
        return new Page<>(currentPage, totalPages,
                offerRepository.getByTender(tenderId, offersPerPage, amountOffersToSkip).stream()
                        .map(offerMapper::offerToOfferResponse).toList());
    }

    @Override
    public OfferDetailsResponse getById(Integer offerId) {
        return offerMapper.offerToOfferDetailsResponse(offerRepository.getById(offerId));
    }

    @Override
    public void addAwardDecisionFile(AwardDecisionRequest award) {
        Integer stageStatus = 2;
        offerRepository.addAwardDecision(award.getAwardDecisionFileName(), stageStatus, award.getOfferId());
    }

    @Override
    public void addRejectDecisionFile(RejectDecisionRequest reject) {
        Integer stageStatus = 5;
        offerRepository.addRejectDecision(reject.getRejectDecisionFileName(), stageStatus, reject.getOfferId());
    }

    @Override
    public void saveApproveDecision(DecisionRequest decision) {
        String rejectDecisionFileName = tenderRepository.getRejectDecisionFileNameByTender(decision.getTenderId());
        Integer tenderStatus = 2;
        tenderRepository.updateTenderStatus(tenderStatus, decision.getTenderId());
        Integer otherOffersStatus = 5;
        Integer statusOfActiveOffers = 2;
        offerRepository.updateOffersStatus(otherOffersStatus, rejectDecisionFileName, decision.getTenderId(),
                statusOfActiveOffers, decision.getOfferId());
        Integer offerStatus = 3;
        offerRepository.updateOfferStatus(offerStatus, decision.getOfferId());
    }

    @Override
    public void saveDeclineDecision(DecisionRequest decision) {
        Integer activeOfferStatus = 2;
        if (offerRepository.countActiveOffersByTender(decision.getTenderId(), activeOfferStatus) == 1) {
            Integer statusId = 2;
            tenderRepository.updateTenderStatus(statusId, decision.getTenderId());
        }
        Integer statusId = 4;
        offerRepository.updateOfferStatus(statusId, decision.getOfferId());
    }
}