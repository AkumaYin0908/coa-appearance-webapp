package com.coa.payload.request.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRequest {
    private String code;
    private String name;

    public BaseRequest(String code) {
        this.code = code;
    }
}
