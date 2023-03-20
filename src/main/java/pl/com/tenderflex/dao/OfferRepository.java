package pl.com.tenderflex.dao;

import java.util.List;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Total;

public interface OfferRepository {

    Offer create(Offer offer, Integer bidderId);
    
    List<Offer> getByBidder(Integer bidderId, Integer amountOffers, Integer amountOffersToSkip);
    
    List<Offer> getByContractor(Integer contractorId, Integer amountOffers, Integer amountOffersToSkip);
    
    List<Offer> getByTender(Integer tenderId, Integer amountOffers, Integer amountOffersToSkip);
    
    Integer countOffersByBidder(Integer bidderId);
    
    Integer countOffersByContractor(Integer contractorId);
    
    Integer countOffersByTender(Integer tenderId);
    
    Offer getById(Integer offerId);

    Total getTotalTendersAndOffersByBidder(Integer bidderId);

}