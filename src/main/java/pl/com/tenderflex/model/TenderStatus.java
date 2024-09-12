package pl.com.tenderflex.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TenderStatus {

    private Integer id;
    private User user;
    private Tender tender;
    private EUserTenderStatus status;
    private LocalDate lastUpdated; 
    
}