package com.coa.payload.response.address;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MunicipalityResponse extends BaseResponse {

    public MunicipalityResponse(String code, String name) {
        super(code, name);
    }

    public MunicipalityResponse(String code) {
        super(code);
    }
}
