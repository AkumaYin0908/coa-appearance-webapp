package com.coa.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionRequest {

    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9 ]+(?:-[a-zA-Z0-9 ]+)?$", message = "numbers and any special characters(except hyphen) is not allowed!")
    @NotBlank(message = "position is required")
    @Size(min = 2,max = 50,message = "position title must be between {min} and {max} characters long")
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionRequest that = (PositionRequest) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
