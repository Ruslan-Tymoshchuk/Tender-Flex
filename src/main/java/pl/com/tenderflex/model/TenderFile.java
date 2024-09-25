package pl.com.tenderflex.model;

import lombok.Builder;
import lombok.Data;
import pl.com.tenderflex.model.enums.EFileType;

@Data
@Builder
public class TenderFile {

    private Integer id;
    private Integer tenderId;
    private String name;
    private EFileType fileType;
    private String contentType;
    private String awsS3fileKey;
   
}