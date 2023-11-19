package com.coa.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitorDTO {

    private Long id;
    private String name;
    private String position;
    private String agency;
    private LocalDate dateIssued;


    public VisitorDTO(Long id, String name, String position, String agency) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.agency = agency;
    }
}
