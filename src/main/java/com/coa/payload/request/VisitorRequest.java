package com.coa.payload.request;


import com.coa.payload.request.address.AddressRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorRequest {

    private Long id;
    private CourtesyTitleRequest courtesyTitleRequest;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private PositionRequest position;
    private AgencyRequest agency;
    private AddressRequest address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitorRequest that = (VisitorRequest) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
