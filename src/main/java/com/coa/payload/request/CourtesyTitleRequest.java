package com.coa.payload.request;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourtesyTitleRequest {


    private Long id;
    @Pattern(regexp = "^[a-zA-Z ]+(?:-[a-zA-Z ]+)?$", message = "numbers and any special characters(except hyphen) is not allowed!")
    @NotNull(message = "courtesy title is required")
    @Size(min = 2,max = 5,message = "courtesy title must be between {min} and {max} characters long")
    private String title;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourtesyTitleRequest that = (CourtesyTitleRequest) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

