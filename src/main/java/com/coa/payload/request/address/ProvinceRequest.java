package com.coa.payload.request.address;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ProvinceRequest extends BaseRequest{

    public ProvinceRequest(String code, String name) {
        super(code, name);
    }

    public ProvinceRequest(String code) {
        super(code);
    }
}
