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

    public String getFormattedDateRange() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        DateTimeFormatter dateFromFormat = DateTimeFormatter.ofPattern("MMMM d");
        DateTimeFormatter dateToFormat = DateTimeFormatter.ofPattern("d, yyyy");

        LocalDate dateFrom = LocalDate.parse(this.dateFrom);
        LocalDate dateTo = LocalDate.parse(this.dateTo);

        String firstDate = "";
        String lastDate = "";

        String formattedDateRange = "";

        LocalDate[] dates = {dateFrom, dateTo};

        if (dateFrom.getYear() == dateTo.getYear()) {
            if (dateFrom.getMonth().equals(dateTo.getMonth())) {
                if (dateFrom.equals(dateTo)) {
                    formattedDateRange = dateTimeFormatter.format(dateFrom);
                } else {
                    firstDate = dateFromFormat.format(dates[0]);
                    lastDate = dateToFormat.format(dates[1]);
                    formattedDateRange = String.format("%s - %s", firstDate, lastDate);
                }
            } else {
                firstDate = dateFromFormat.format(dates[0]);
                lastDate = dateTimeFormatter.format(dates[1]);
                formattedDateRange = String.format("%s - %s", firstDate, lastDate);
            }
        } else {
            firstDate = dateTimeFormatter.format(dates[0]);
            lastDate = dateTimeFormatter.format(dates[1]);
            formattedDateRange = String.format("%s - %s", firstDate, lastDate);
        }

        return formattedDateRange;
    }
}
