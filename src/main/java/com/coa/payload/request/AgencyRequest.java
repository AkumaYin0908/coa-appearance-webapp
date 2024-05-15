package com.coa.payload.request;

import com.coa.model.address.Address;
import com.coa.payload.request.address.AddressRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgencyRequest {

    private Long id;
    private String name;


}
