package com.coa.dto;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

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

    private String formattedStringDate;

    @Getter(AccessLevel.NONE)
    private String dateAppeared;




    public AppearanceDTO(Long id, String dateIssued, String dateFrom, String dateTo, String purpose) {
        this.id = id;
        this.dateIssued = dateIssued;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.purpose = purpose;
    }

    public AppearanceDTO(Long id, String name, String position, String agency, String dateIssued, String dateFrom, String dateTo, String purpose) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.agency = agency;
        this.dateIssued = dateIssued;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.purpose = purpose;
    }

    public AppearanceDTO(String dateFrom, String dateTo, String purpose) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.purpose = purpose;
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

    public String formatStringMultipleDate(List<String> datesFrom, List<String> datesTo) {
        StringBuilder date= new StringBuilder();
        for(int i=0; i<datesFrom.size() && i<datesTo.size();i++){
            String formattedDateString=getFormattedDateRange(datesFrom.get(i),datesTo.get(i));
            if(i<datesFrom.size()-1 && i<datesTo.size()-1){
                date.append(formattedDateString).append(", ");
            }else{
                date.append(" & ").append(formattedDateString);
            }

        }

       return date.toString();
    }

    public String formattedStringDateRange(String dateFromString, String dateToString) {
        return getFormattedDateRange(dateFromString,dateToString);
    }

    public String joinPurpose(Set<String> purposes) {


        StringBuilder purposeBuilder = new StringBuilder();


        if(purposes.size() <=1){
           return purposes.stream().findFirst().get();
        }else{
            for(String purpose : purposes){
                purposeBuilder.append(purpose).append(", ");
            }

            purposeBuilder.replace(purposeBuilder.length()-2, purposeBuilder.length()+1,"");
            int lastComma=purposeBuilder.lastIndexOf(",");
            System.out.println(lastComma);
            purposeBuilder.replace(lastComma, lastComma+1, " &");

            return purposeBuilder.toString();
        }


    }

    public String getDateAppeared() {
        return getFormattedDateRange(dateFrom,dateTo);
    }


}
