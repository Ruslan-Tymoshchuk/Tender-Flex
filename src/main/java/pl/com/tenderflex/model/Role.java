package pl.com.tenderflex.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Role {

    private Integer id;
    private ERole name;
    
}