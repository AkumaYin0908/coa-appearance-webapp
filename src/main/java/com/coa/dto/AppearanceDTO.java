package com.coa.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

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

    private String StringDate;


}
