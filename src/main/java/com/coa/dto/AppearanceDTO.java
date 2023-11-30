package com.coa.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppearanceDTO {

    private Long id;
    private String name;
    private String position;
    private String agency;
    private LocalDate dateIssued;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    private String purpose;

    private String StringDate;


}
