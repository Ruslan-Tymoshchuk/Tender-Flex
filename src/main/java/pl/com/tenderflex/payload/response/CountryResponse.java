package pl.com.tenderflex.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CountryResponse {

    private Integer id;
    private String countryName;
    
}