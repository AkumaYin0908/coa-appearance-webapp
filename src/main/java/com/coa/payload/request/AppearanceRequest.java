package com.coa.payload.request;


import com.coa.model.Agency;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppearanceRequest {

    private Long id;

    private VisitorRequest visitor;

    @NotBlank(message = "date issued is required")
    private String dateIssued;

    @NotBlank(message = "date from is required")
    private String dateFrom;

    @NotBlank(message = "date to is required")
    private String dateTo;


    private PurposeRequest purpose;

    @Pattern(regexp = "^[a-zA-Z0-9]+(?:-[a-zA-Z0-9]+)?$", message = "numbers and any special characters(except hyphen) is not allowed!")
    @Size(max = 20,message = "reference must be {max} characters long")
    private String reference;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppearanceRequest that = (AppearanceRequest) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
