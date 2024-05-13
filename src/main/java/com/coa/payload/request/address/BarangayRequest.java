package com.coa.payload.request.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BarangayRequest extends BaseRequest{

    public BarangayRequest(String code) {
        super(code);
    }

    public BarangayRequest(String code, String name) {
        super(code, name);
    }



}
