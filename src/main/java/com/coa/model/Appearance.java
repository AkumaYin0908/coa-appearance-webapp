package com.coa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "appearance")
public class Appearance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "visitor")
    private Visitor visitor;


    @Column(name = "date_issued")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateIssued;

    @Column(name = "date_from")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;

    @Column(name="date_to")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTo;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "purpose")
    private Purpose purpose;



    public Appearance(Visitor visitor, LocalDate dateFrom, LocalDate dateTo, Purpose purpose, LocalDate dateIssued) {
        this.visitor = visitor;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.purpose = purpose;
        this.dateIssued = dateIssued;
    }
}
