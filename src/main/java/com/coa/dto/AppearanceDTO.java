package com.coa.dto;


import lombok.*;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

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


    private String getFormattedDateRange(String dateFromString, String dateToString) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        LocalDate dateFrom = LocalDate.parse(dateFromString, dateTimeFormatter);
        LocalDate dateTo = LocalDate.parse(dateToString, dateTimeFormatter);
        String firstDate = "";
        String lastDate = "";

        String formattedDateRange = "";

        LocalDate[] dates = {dateFrom, dateTo};

        if (dateFrom.getMonth().equals(dateTo.getMonth())) {
            if (dateFrom.equals(dateTo)) {
                formattedDateRange = dateTimeFormatter.format(dateFrom);
            } else {
                DateTimeFormatter firstDayFormat = DateTimeFormatter.ofPattern("MMMM d");
                DateTimeFormatter lastDayFormat = DateTimeFormatter.ofPattern("d, yyyy");

                firstDate = firstDayFormat.format(dates[0]);
                lastDate = lastDayFormat.format(dates[1]);
                formattedDateRange = String.format("%s - %s", firstDate, lastDate);
            }
        } else {
            firstDate = dateTimeFormatter.format(dates[0]);
            lastDate = dateTimeFormatter.format(dates[1]);
            formattedDateRange = String.format("%s - %s", firstDate, lastDate);
        }

        return formattedDateRange;
    }

    public String formatStringMultipleDate(Set<String> datesFrom, Set<String> datesTo) {


        Iterator<String> iteratorFrom = datesFrom.iterator();
        Iterator<String> iteratorTo = datesTo.iterator();

        List<String> formattedDates = new ArrayList<>();
        while (iteratorFrom.hasNext() && iteratorTo.hasNext()) {
            formattedDates.add(formattedStringDateRange(iteratorFrom.next(), iteratorTo.next()));
        }
        StringBuilder builder = new StringBuilder();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        List<LocalDate> dates = formattedDates.stream()
                .map(dateStr -> LocalDate.parse(dateStr, dateTimeFormatter)).sorted().toList();


        for (int i = 0; i < dates.size(); i++) {

            if (i < dates.size() - 1) {
                LocalDate date1 = dates.get(i);
                Month month = date1.getMonth();
                int year = date1.getYear();
                String monthStr = month.name().charAt(0) + month.name().substring(1).toLowerCase();

                if (year == dates.get(i + 1).getYear()) {
                    if (i == 0) {
                        builder.append(monthStr).append(" ")
                                .append(date1.getDayOfMonth());

                    } else {
                        LocalDate date2 = dates.get(i - 1);
                        if (month.equals(date2.getMonth())) {
                            builder.append(", ").append(date1.getDayOfMonth());

                        } else {
                            builder.append(", ").append(monthStr).append(" ")
                                    .append(date1.getDayOfMonth());
                        }
                    }

                } else {
                    if (i == 0) {
                        builder.append(monthStr)
                                .append(" ")
                                .append(date1.getDayOfMonth())
                                .append(", ")
                                .append(year);
                    } else {
                        builder.append(" & ")
                                .append(monthStr)
                                .append(" ")
                                .append(date1.getDayOfMonth())
                                .append(", ")
                                .append(year);
                    }
                }
            } else {
                LocalDate date1 = dates.get(i - 1);
                LocalDate date2 = dates.get(i);

                int year = date2.getYear();
                Month month = date2.getMonth();
                String monthStr = month.name().charAt(0) + month.name().substring(1).toLowerCase();

                if (year == date1.getYear()) {
                    if (month.equals(date1.getMonth())) {
                        builder.append(" & ").append(date2.getDayOfMonth()).append(", ").append(year);
                    } else {
                        builder.append(" & ").append(monthStr).append(" ").append(date2.getDayOfMonth()).append(", ").append(year);
                    }
                } else {
                    builder.append(" & ")
                            .append(monthStr)
                            .append(" ")
                            .append(date2.getDayOfMonth())
                            .append(", ")
                            .append(year);
                }

            }

        }

        return builder.toString();


//        Collections.reverse(formattedDates);
//        if(formattedDates.size()<=1){
//            return formattedDates.stream().findFirst().get();
//        }else{
////            for(int i=0;i<formattedDates.size();i++) {
////
////            if (i == formattedDates.size() - 1) {
////                int lastComma=dateBuilder.lastIndexOf(", ");
////                dateBuilder=new StringBuilder(dateBuilder.substring(0,lastComma));
////                dateBuilder.append(" & ").append(formattedDates.get(i));
////                continue;
////            }
////
////           dateBuilder.append(formattedDates.get(i)).append(", ");
////            }
//            return String.join(", ", formattedDates.subList(0, formattedDates.size() - 1))
//                    + " & " + formattedDates.get(formattedDates.size() - 1);
//        }
//
//
//      // return dateBuilder.toString();
    }

    public String formattedStringDateRange(String dateFromString, String dateToString) {
        return getFormattedDateRange(dateFromString, dateToString);
    }

    public String joinPurpose(Set<String> purposes) {

        StringBuilder purposeBuilder = new StringBuilder();


        if (purposes.size() <= 1) {
            return purposes.stream().findFirst().get();
        } else {
            for (String purpose : purposes) {
                purposeBuilder.append(purpose).append(", ");
            }

            purposeBuilder.replace(purposeBuilder.length() - 2, purposeBuilder.length() + 1, "");
            int lastComma = purposeBuilder.lastIndexOf(",");
            purposeBuilder.replace(lastComma, lastComma + 1, " &");

            return purposeBuilder.toString();
        }


    }

    public String getDateAppeared() {
        return getFormattedDateRange(dateFrom, dateTo);
    }


}
