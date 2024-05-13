package com.coa.payload.response.address;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BarangayResponse extends BaseResponse {

    public BarangayResponse(String code) {
        super(code);
    }

    public BarangayResponse(String code, String name) {
        super(code, name);
    }


}
