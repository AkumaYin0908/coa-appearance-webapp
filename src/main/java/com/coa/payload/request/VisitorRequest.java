package com.coa.payload.request;


import com.coa.payload.request.address.AddressRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorRequest {

    private Long id;

    @NotNull(message = "courtesy title is required")
    private CourtesyTitleRequest courtesyTitle;

    @Pattern(regexp = "^[a-zA-Z ]+(?:-[a-zA-Z ]+)?$", message = "numbers and any special characters(except hyphen) is not allowed!")
    @NotBlank(message = "first name is required")
    @Size(min = 2,max = 20,message = "first name must be between {min} and {max} characters long")
    private String firstName;


    @Size(min =0,max = 2,message = "middle initial must be empty or {max} characters long")
    private String middleInitial;

    @Pattern(regexp = "^[a-zA-Z ]+(?:-[a-zA-Z ]+)?$", message = "numbers and any special characters(except hyphen) is not allowed!")
    @NotBlank(message = "last name is required")
    @Size(min = 2,max = 20,message = "last name must be between {min} and {max} characters long")
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
