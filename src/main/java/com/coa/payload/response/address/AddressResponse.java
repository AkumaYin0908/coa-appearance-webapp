package com.coa.payload.response.address;

import com.coa.model.address.Address;
import com.coa.payload.request.address.BarangayRequest;
import com.coa.payload.request.address.MunicipalityRequest;
import com.coa.payload.request.address.ProvinceRequest;
import com.coa.payload.request.address.RegionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {

    private Long id;
    private BarangayResponse barangay;
    private MunicipalityResponse municipality;
    private ProvinceResponse province;
    private RegionResponse region;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressResponse that = (AddressResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
