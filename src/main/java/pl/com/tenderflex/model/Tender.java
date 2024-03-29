package pl.com.tenderflex.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tender {

    private Integer id;
    private Integer contractorId;
    private Organization organization;
    private String cpvCode;
    private TenderType type;
    private String details;
    private Integer minPrice;
    private Integer maxPrice;
    private Currency currency;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate publication;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate deadline;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate deadlineForSignedContract;
    private String status;
    private String contractUrl;
    private String awardDecisionUrl;
    private String rejectDecisionUrl;
    private Integer offersAmount;
    private String offerStatus;

}