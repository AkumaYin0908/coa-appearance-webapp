package com.coa.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitorDTO {

    private Long id;

    @NotBlank(message = "must not be blank")
    @Size(min=10,message = "must have at least 10 characters")
    private String name;

    @NotBlank(message = "must not be blank")
    @Size(min=10,message = "must have at least 10 characters")
    private String position;

    @NotBlank(message = "must not be blank")
    @Size(min=10,message = "must have at least 10 characters")
    private String agency;

    private LocalDate dateIssued;


    public VisitorDTO(Long id, String name, String position, String agency) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.agency = agency;
    }
}
