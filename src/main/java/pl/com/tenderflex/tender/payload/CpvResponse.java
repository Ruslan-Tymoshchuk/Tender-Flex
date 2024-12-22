package pl.com.tenderflex.tender.payload;

import pl.com.tenderflex.tender.model.Cpv;

public record CpvResponse(Integer id, String code, String description) {

    public CpvResponse(Cpv cpv) {
        this(cpv.getId(), cpv.getCode(), cpv.getDescription());
    }

}