package pl.com.tenderflex.payload.response;

public record CurrencyResponse(
        Integer id, 
        String code,
        String symbol) {   
}