package com.coa.payload.response;


import com.coa.model.Agency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppearanceResponse {

    private Long id;
    private VisitorResponse visitor;
    private String dateIssued;
    private String dateFrom;
    private String dateTo;
    private PurposeResponse purpose;
    private Agency agency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppearanceResponse that = (AppearanceResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
