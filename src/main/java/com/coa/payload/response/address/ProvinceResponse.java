package com.coa.payload.response.address;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ProvinceResponse extends BaseResponse {

    public ProvinceResponse(String code, String name) {
        super(code, name);
    }

    public ProvinceResponse(String code) {
        super(code);
    }
}
