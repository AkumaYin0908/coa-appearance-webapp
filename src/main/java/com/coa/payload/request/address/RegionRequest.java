package com.coa.payload.request.address;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RegionRequest extends BaseRequest{

    public RegionRequest(String code, String name) {
        super(code, name);
    }

    public RegionRequest(String code) {
        super(code);
    }
}
