package pl.com.tenderflex.controller;

import static pl.com.tenderflex.security.SecurityRoles.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.response.OfferCountResponse;
import pl.com.tenderflex.payload.response.OfferResponse;
import pl.com.tenderflex.payload.response.OfferStatusResponse;
import pl.com.tenderflex.service.OfferService;
import pl.com.tenderflex.service.RoleBasedActionExecutor;

@RestController
@RequiredArgsConstructor
public class OfferController {

    public static final String URI_OFFERS_ID = "/api/v1/offers/{id}";
    public static final String URI_OFFERS_PAGE = "/api/v1/offers/page";
    public static final String URI_OFFERS_PAGE_TENDER_ID = "/api/v1/offers/page/{tender-id}";
    public static final String URI_OFFERS_COUNT_USER = "/api/v1/offers/count";
    public static final String URI_OFFERS_COUNT_TENDER = "/api/v1/offers/count/{tender-id}";
    public static final String URI_OFFERS_STATUS_BIDDER_ID_TENDER_ID = "/api/v1/offers/status/{user-id}/{tender-id}";
    public static final String URL_OFFERS_SELECT_OFFER = "/api/v1/offers/winning-offer";
    public static final String URL_OFFERS_SIGN_CONTRACT = "/api/v1/offers/sign-contact";

    private final OfferService offerService;
    private final RoleBasedActionExecutor roleBasedActionExecutor;

    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(URI_OFFERS_ID)
    public OfferResponse findDetailsById(@AuthenticationPrincipal User user, @PathVariable("id") Integer id) {
        return roleBasedActionExecutor.executeRoleBasedAction(user,
                contractor -> offerService.findDetailsByContractor(id), bidder -> offerService.findDetailsByBidder(id));
    }

    @Secured({ BIDDER, CONTRACTOR })
    @GetMapping(URI_OFFERS_PAGE)
    public Page<OfferResponse> findPage(@AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer offersPerPage) {
        return roleBasedActionExecutor.executeRoleBasedAction(user,
                contractor -> offerService.findPageByContractor(user.getId(), currentPage, offersPerPage),
                bidder -> offerService.findPageByBidder(user.getId(), currentPage, offersPerPage));
    }
    
    @Secured({ BIDDER, CONTRACTOR })
    @GetMapping(URI_OFFERS_PAGE_TENDER_ID)
    public Page<OfferResponse> findPage(@PathVariable("tender-id") Integer tenderId,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer offersPerPage) {
        return offerService.findPageByTender(tenderId, currentPage, offersPerPage);
    }

    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(URI_OFFERS_COUNT_USER)
    public OfferCountResponse countByUser(@AuthenticationPrincipal User user) {
        return roleBasedActionExecutor.executeRoleBasedAction(user,
                contractor -> offerService.countByContractor(contractor.getId()),
                bidder -> offerService.countByBidder(bidder.getId()));
    }

    @Secured(CONTRACTOR)
    @GetMapping(URI_OFFERS_COUNT_TENDER)
    public OfferCountResponse countByTender(@PathVariable("tender-id") Integer tenderId) {
        return offerService.countByTender(tenderId);
    }

    @Secured(BIDDER)
    @GetMapping(URI_OFFERS_STATUS_BIDDER_ID_TENDER_ID)
    public OfferStatusResponse checkOfferStatus(@PathVariable("user-id") Integer userId,
            @PathVariable("tender-id") Integer tenderId) {
        return offerService.checkOfferStatus(tenderId, userId);
    }
    
}