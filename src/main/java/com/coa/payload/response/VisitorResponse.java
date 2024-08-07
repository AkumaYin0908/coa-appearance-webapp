package com.coa.payload.response;


import com.coa.payload.Reportable;
import com.coa.payload.request.AgencyRequest;
import com.coa.payload.request.address.AddressRequest;
import com.coa.payload.response.address.AddressResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorResponse implements Reportable {

    private Long id;
    private  CourtesyTitleResponse courtesyTitle;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private PositionResponse position;
    private AgencyResponse agency;
    private AddressResponse address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitorResponse that = (VisitorResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
