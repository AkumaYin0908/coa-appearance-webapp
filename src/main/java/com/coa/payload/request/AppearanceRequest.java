package com.coa.payload.request;


import com.coa.model.Agency;
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
    private String dateIssued;
    private String dateFrom;
    private String dateTo;
    private PurposeRequest purpose;
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
