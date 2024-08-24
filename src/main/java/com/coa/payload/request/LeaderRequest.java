package com.coa.payload.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaderRequest {

    private Long id;

    @Pattern(regexp = "^[a-zA-Z ]+(?:-[a-zA-Z ]+)?$", message = "numbers and any special characters(except hyphen) is not allowed!")
    @NotBlank(message = "name is required")
    @Size(min = 2,max = 20,message = "name must be between {min} and {max} characters long")
    private String name;

    @Pattern(regexp = "^[a-zA-Z ]+(?:-[a-zA-Z ]+)?$", message = "numbers and any special characters(except hyphen) is not allowed!")
    @NotBlank(message = "position is required")
    private String position;
    private boolean inCharge;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaderRequest that = (LeaderRequest) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
