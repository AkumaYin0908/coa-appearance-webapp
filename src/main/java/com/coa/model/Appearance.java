package com.coa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "date_from")
    private LocalDate dateFrom;

    @Column(name="date_to")
    private LocalDate dateTo;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "purpose")
    private Purpose purpose;

    @Column(name = "date_issued")
    private LocalDate dateIssued;

    public Appearance(Visitor visitor, LocalDate dateFrom, LocalDate dateTo, Purpose purpose, LocalDate dateIssued) {
        this.visitor = visitor;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.purpose = purpose;
        this.dateIssued = dateIssued;
    }
}
