package pl.com.tenderflex.payload.response;

import lombok.Data;

@Data
public class OfferDetailsResponse {

    private Integer offerId;
    private String contractorSt;
    private String bidderSt;
    private String organizationNameByBidder;
    private String nationalRegistrationNumber;
    private String country;
    private String city;
    private String firstName;
    private String lastName;
    private String phone;
    private String bidPrice;
    private String currency;
    private String documentName;
    private String awardDecision;
    private String rejectDecision;

}