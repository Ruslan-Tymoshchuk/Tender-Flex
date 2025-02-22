package pl.com.tenderflex.service.impl;

import static pl.com.tenderflex.model.ERole.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.GrantedAuthorityRoleRepository;
import pl.com.tenderflex.dao.OfferRepository;
import pl.com.tenderflex.dao.TenderRepository;
import pl.com.tenderflex.exception.UnauthorizedAccessException;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.iresponse.OfferDetails;
import pl.com.tenderflex.payload.iresponse.response.DecisionResponse;
import pl.com.tenderflex.payload.iresponse.response.OfferInListResponse;
import pl.com.tenderflex.payload.mapstract.OfferMapper;
import pl.com.tenderflex.payload.request.AwardDecisionRequest;
import pl.com.tenderflex.payload.request.DecisionRequest;
import pl.com.tenderflex.payload.request.OfferDetailsRequest;
import pl.com.tenderflex.payload.request.RejectDecisionRequest;
import pl.com.tenderflex.service.OfferService;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferMapper offerMapper;
    private final OfferRepository offerRepository;
    private final TenderRepository tenderRepository;
    private final GrantedAuthorityRoleRepository roleRepository;

    @Override
    @Transactional
    public void createOffer(OfferDetailsRequest offerDetailsRequest, User bidder) {
        offerRepository.create(offerMapper.newOfferRequestToOffer(offerDetailsRequest, bidder));
    }

    @Override
    public Page<OfferInListResponse> getOffersByBidder(Integer bidderId, Integer currentPage, Integer offersPerPage) {
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
                offerRepository.getPageByBidder(bidderId, offersPerPage, amountOffersToSkip).stream()
                        .map(offer -> offerMapper.offerToOfferInListResponse(offer, offer.getOfferStatusBidder()))
                        .toList());
    }

    @Override
    public Page<OfferInListResponse> getOffersByContractor(Integer contractorId, Integer currentPage,
            Integer offersPerPage) {
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
                offerRepository.getPageByContractor(contractorId, offersPerPage, amountOffersToSkip).stream()
                        .map(offer -> offerMapper.offerToOfferInListResponse(offer, offer.getOfferStatusContractor()))
                        .toList());
    }

    @Override
    public OfferDetails getOfferDetails(Integer offerId, Collection<GrantedAuthority> authorities) {
        if (authorities.contains(roleRepository.getByName(CONTRACTOR))) {
            return offerMapper.offerToOfferDetailsContractorResponse(offerRepository.getById(offerId));
        } else if (authorities.contains(roleRepository.getByName(BIDDER))) {
            return offerMapper.offerToOfferDetailsBidderResponse(offerRepository.getById(offerId));
        }
        throw new UnauthorizedAccessException("User does not have the required role to access this resource");
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
    @Transactional
    public DecisionResponse saveApproveDecision(DecisionRequest decision) {
        String rejectDecisionFileName = tenderRepository.getRejectDecisionFileNameByTender(decision.getTenderId());
        Integer tenderStatus = 2;
        tenderRepository.updateTenderStatus(tenderStatus, decision.getTenderId());
        Integer otherOffersStatus = 5;
        Integer statusOfActiveOffers = 2;
        offerRepository.updateOffersStatus(otherOffersStatus, rejectDecisionFileName, decision.getTenderId(),
                statusOfActiveOffers, decision.getOfferId());
        Integer offerStatus = 3;
        offerRepository.updateOfferStatus(offerStatus, decision.getOfferId());
        return new DecisionResponse(decision.getOfferId(), "Contract approved by Bidder");
    }

    @Override
    public DecisionResponse saveDeclineDecision(DecisionRequest decision) {
        Integer activeOfferStatus = 2;
        if (offerRepository.countActiveOffersByTender(decision.getTenderId(), activeOfferStatus) == 1) {
            Integer statusId = 2;
            tenderRepository.updateTenderStatus(statusId, decision.getTenderId());
        }
        Integer statusId = 4;
        offerRepository.updateOfferStatus(statusId, decision.getOfferId());
        return new DecisionResponse(decision.getOfferId(), "Contract declined by Bidder");
    }
}