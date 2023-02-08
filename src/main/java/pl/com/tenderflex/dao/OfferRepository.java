package pl.com.tenderflex.dao;

import java.util.List;
import pl.com.tenderflex.model.Offer;

public interface OfferRepository {

    Offer create(Offer offer);

    Integer countOffersByTender(Integer tenderId);

    List<Offer> getByContractor(Integer contractorId, Integer amountOffers, Integer amountOffersToSkip);

    Offer getById(Integer offerId);

    List<Offer> getByBidder(Integer bidderId);

    Integer countOffersByContractor(Integer contractorId);

}