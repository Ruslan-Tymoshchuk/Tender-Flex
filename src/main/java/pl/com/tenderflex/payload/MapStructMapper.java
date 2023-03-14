package pl.com.tenderflex.payload;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.com.tenderflex.model.Country;
import pl.com.tenderflex.model.Currency;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.payload.request.TenderDetailsRequest;
import pl.com.tenderflex.payload.response.BidderTenderResponse;
import pl.com.tenderflex.payload.response.ContractorTenderDetailsResponse;
import pl.com.tenderflex.payload.response.ContractorTenderResponse;
import pl.com.tenderflex.payload.response.CountryResponse;
import pl.com.tenderflex.payload.response.CurrencyResponse;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    CountryResponse countryToCountryResponse(Country country);

    CurrencyResponse currencyToCurrencyResponce(Currency currency);

    @Mapping(target = "organization.name", source = "organizationName")
    @Mapping(target = "organization.nationalRegistrationNumber", source = "nationalRegistrationNumber")
    @Mapping(target = "organization.country.id", source = "countryId")
    @Mapping(target = "organization.city", source = "city")
    @Mapping(target = "organization.contactPerson.firstName", source = "firstName")
    @Mapping(target = "organization.contactPerson.lastName", source = "lastName")
    @Mapping(target = "organization.contactPerson.phone", source = "phone")
    @Mapping(target = "publication", source = "publication")
    @Mapping(target = "cpvCode", source = "cpvCode")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "details", source = "details")
    @Mapping(target = "minPrice", source = "minPrice")
    @Mapping(target = "maxPrice", source = "maxPrice")
    @Mapping(target = "currency.id", source = "currencyId")
    @Mapping(target = "deadline", source = "deadline", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "deadlineForSignedContract", source = "deadlineForSignedContract", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "contractUrl", source = "contractUrl")
    @Mapping(target = "awardDecisionUrl", source = "awardDecisionUrl")
    @Mapping(target = "rejectDecisionUrl", source = "rejectDecisionUrl")
    Tender tenderDetailsRequestToTender(TenderDetailsRequest tenderDetailsRequest);
    
    @Mapping(target = "tenderId", source = "id")
    @Mapping(target = "cpvCode", source = "cpvCode")
    @Mapping(target = "organizationName", source = "tender.organization.name")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "deadline", source = "deadline", dateFormat = "dd-MM-yyyy")
    ContractorTenderResponse tenderToContractorTenderResponse(Tender tender);

    @Mapping(target = "tenderId", source = "id")
    @Mapping(target = "cpvCode", source = "cpvCode")
    @Mapping(target = "organizationName", source = "tender.organization.name")
    @Mapping(target = "tenderStatus", source = "status")
    @Mapping(target = "deadline", source = "deadline", dateFormat = "dd-MM-yyyy")
    BidderTenderResponse tenderToBidderTenderResponse(Tender tender);

    @Mapping(target = "tenderId", source = "id")
    @Mapping(target = "organizationName", source = "organization.name")
    @Mapping(target = "nationalRegistrationNumber", source = "organization.nationalRegistrationNumber")
    @Mapping(target = "country", source = "organization.country.countryName")
    @Mapping(target = "city", source = "organization.city")
    @Mapping(target = "firstName", source = "organization.contactPerson.firstName")
    @Mapping(target = "lastName", source = "organization.contactPerson.lastName")
    @Mapping(target = "phone", source = "organization.contactPerson.phone")
    @Mapping(target = "cpvCode", source = "cpvCode")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "description", source = "details")
    @Mapping(target = "minTenderValue", source = "minPrice")
    @Mapping(target = "currency", source = "currency.currencyType")
    @Mapping(target = "tenderPublicationDate", source = "publication", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "deadlineForOfferSubmission", source = "deadline", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "deadlineForSignedContractSubmission", source = "deadlineForSignedContract", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "contractFileUrl", source = "contractUrl")
    @Mapping(target = "awardDecisionFileUrl", source = "awardDecisionUrl")
    @Mapping(target = "rejectDecisionFileUrl", source = "rejectDecisionUrl")
    ContractorTenderDetailsResponse tenderToContractorTenderDetailsResponse(Tender tender);
}