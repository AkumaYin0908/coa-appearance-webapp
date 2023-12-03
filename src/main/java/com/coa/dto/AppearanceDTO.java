package com.coa.dto;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppearanceDTO {

    private Long id;
    private String name;
    private String position;
    private String agency;

    private String dateIssued;
    private String dateFrom;
    private String dateTo;

    private String purpose;


    @Getter(AccessLevel.NONE)
    private String formattedStringDate;

    public AppearanceDTO(Long id, String dateIssued, String dateFrom, String dateTo, String purpose) {
        this.id = id;
        this.dateIssued = dateIssued;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.purpose = purpose;
    }

    public String getFormattedStringDate() {
        return getFormattedDateRange(dateFrom,dateTo);
    }

    private String getFormattedDateRange(String dateFromString, String dateToString){

        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        LocalDate dateFrom=LocalDate.parse(dateFromString,dateTimeFormatter);
        LocalDate dateTo=LocalDate.parse(dateToString, dateTimeFormatter);
        String firstDate ="";
        String lastDate="";

        String formattedDateRange="";

        LocalDate[]dates={dateFrom,dateTo};

        if(dateFrom.getMonth().equals(dateTo.getMonth())){
            if(dateFrom.equals(dateTo)){
                formattedDateRange=dateTimeFormatter.format(dateFrom);
            }else{
                DateTimeFormatter firstDayFormat= DateTimeFormatter.ofPattern("MMMM d");
                DateTimeFormatter lastDayFormat= DateTimeFormatter.ofPattern("d, yyyy");

                firstDate=firstDayFormat.format(dates[0]);
                lastDate=lastDayFormat.format(dates[1]);
                formattedDateRange=String.format("%s - %s",firstDate,lastDate);
            }
        }else{
            firstDate=dateTimeFormatter.format(dates[0]);
            lastDate=dateTimeFormatter.format(dates[1]);
            formattedDateRange=String.format("%s - %s",firstDate,lastDate);
        }

        return formattedDateRange;
    }


}
