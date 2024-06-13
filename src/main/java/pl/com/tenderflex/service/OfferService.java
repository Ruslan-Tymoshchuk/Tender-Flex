package pl.com.tenderflex.service;

import pl.com.tenderflex.model.User;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.request.AwardDecisionRequest;
import pl.com.tenderflex.payload.request.DecisionRequest;
import pl.com.tenderflex.payload.request.OfferDetailsRequest;
import pl.com.tenderflex.payload.request.RejectDecisionRequest;
import pl.com.tenderflex.payload.response.DecisionResponse;
import pl.com.tenderflex.payload.response.OfferDetailsResponse;
import pl.com.tenderflex.payload.response.OfferInListResponse;

public interface OfferService {

    void createOffer(OfferDetailsRequest offer, User bidder);

    OfferDetailsResponse getById(Integer offerId);

    Page<OfferInListResponse> getOffersByBidder(Integer bidderId, Integer currentPage, Integer offersPerPage);
    
    Page<OfferInListResponse> getOffersByContractor(Integer contractorId, Integer currentPage, Integer offersPerPage);

    Page<OfferInListResponse> getOffersByTender(Integer tenderId, Integer currentPage, Integer offersPerPage);

    void addAwardDecisionFile(AwardDecisionRequest award);

    void addRejectDecisionFile(RejectDecisionRequest reject);

    DecisionResponse saveApproveDecision(DecisionRequest decision);

    DecisionResponse saveDeclineDecision(DecisionRequest decision);

}