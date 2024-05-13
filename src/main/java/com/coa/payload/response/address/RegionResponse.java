package com.coa.payload.response.address;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RegionResponse extends BaseResponse {

    public RegionResponse(String code, String name) {
        super(code, name);
    }

    public RegionResponse(String code) {
        super(code);
    }
}
