package com.coa.payload.response.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
    private String code;
    private String name;

    public BaseResponse(String code) {
        this.code = code;
    }
}
