package pl.com.tenderflex.payload.request;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import pl.com.tenderflex.model.TenderType;

@Data
public class TenderDetailsRequest {

    private String organizationName;
    private String nationalRegistrationNumber;
    private Integer countryId;
    private String city;
    private String firstName;
    private String lastName;
    private String phone;
    private String cpvCode;
    private TenderType type;
    private String details;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer currencyId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publication;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadlineForSignedContract;
    private String contractUrl;
    private String awardDecisionUrl;
    private String rejectDecisionUrl;

}