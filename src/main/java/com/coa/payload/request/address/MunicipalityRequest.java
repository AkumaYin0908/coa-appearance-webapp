package com.coa.payload.request.address;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MunicipalityRequest extends BaseRequest{

    public MunicipalityRequest(String code, String name) {
        super(code, name);
    }

    public MunicipalityRequest(String code) {
        super(code);
    }
}
