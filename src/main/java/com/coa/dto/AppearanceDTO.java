package com.coa.dto;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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


    @Setter(AccessLevel.NONE)
    private String formattedStringDate;




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

    public void setFormattedStringDate(List<String> datesFrom, List<String> datesTo) {
        StringBuilder date= new StringBuilder();
        for(int i=0; i<datesFrom.size() && i<datesTo.size();i++){
            String formattedDateString=getFormattedDateRange(datesFrom.get(i),datesTo.get(i));
            if(i<datesFrom.size()-1 && i<datesTo.size()-1){
                date.append(formattedDateString).append(", ");
            }else{
                date.append(" & ").append(formattedDateString);
            }

        }

        this.formattedStringDate=date.toString();
    }

    public void setFormattedStringDate(String dateFromString, String dateToString) {
        this.formattedStringDate= getFormattedDateRange(dateFromString,dateToString);
    }

    public void setPurpose(List<String> purposes) {
        StringBuilder purposeBuilder=new StringBuilder();


        for(int i=0;i <purposes.size()-1;i++){
            if(!purposes.get(i).equals(purposes.get(i+1))){
                if(i<purposes.size()-1){
                    purposeBuilder.append(purposes.get(i)).append(", ");
                }else{
                    purposeBuilder.append(" & ").append(purposes.get(i));
                }
            }else{
                if(purposeBuilder.length() > 0){
                    purposeBuilder.append(", ").append(purposes.get(i));
                }else{
                    purposeBuilder=new StringBuilder(purposes.get(i));
                }

            }
        }

        this.purpose=purposeBuilder.toString();
    }
}
