package pl.com.tenderflex.payload.response;

public record ContractResponse(
        Integer id, 
        ContractTypeResponse contractType, 
        String status,
        Integer minPrice, 
        Integer maxPrice,
        CurrencyResponse currency, 
        FileMetadataResponse fileMetadata, 
        String signedDeadline) {
}