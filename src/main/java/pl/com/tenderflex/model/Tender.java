package pl.com.tenderflex.model;

import java.time.LocalDate;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import pl.com.tenderflex.model.enums.ETenderStatus;

@Data
@Builder
public class Tender {

    private Integer id;
    private Integer contractorId;
    private CompanyDetails contractorCompanyDetails;
    private ContactPerson contactPerson;
    private Procedure procedure;
    private Cpv cpv;
    private String description;
    private ETenderStatus status; 
    private LocalDate publicationDate; 
    private LocalDate offerSubmissionDeadline;
    private Contract contract;
    private AwardDecision award;
    private RejectDecision reject;
    private Set<ProcurementProtocol> submittedProtocols;
    private Set<Submission> submissions;
        
}