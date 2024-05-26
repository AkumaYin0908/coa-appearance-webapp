package com.coa.model;


import com.coa.model.address.Address;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="visitor")
public class Visitor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="name",unique = true)
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="position")
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="agency")
    private Agency agency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="address")
    private Address address;


    public Visitor(String name, Position position, Agency agency) {
        this.name = name;
        this.position = position;
        this.agency = agency;
    }
}
