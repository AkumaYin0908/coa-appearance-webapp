package com.coa.model;


import com.coa.model.address.Address;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private CourtesyTitle courtesyTitle;

    @Column(name="first_name")
    private String firstName;

    @Column(name = "middle_init")
    private String middleInitial;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="position")
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="agency")
    private Agency agency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="address")
    private Address address;

    @ToString.Exclude
    @OneToMany(mappedBy = "visitor",cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH})
    private List<Appearance> appearances;

    public Visitor(CourtesyTitle courtesyTitle, String firstName, String middleInitial, String lastName, Position position, Agency agency, Address address) {
        this.courtesyTitle = courtesyTitle;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.position = position;
        this.agency = agency;
        this.address = address;
    }

    public void addAppearance(Appearance appearance){
        if(appearances == null){
            appearances = new ArrayList<>();
        }

        appearance.setVisitor(this);
        appearances.add(appearance);
    }

    public void removeAppearance(Appearance appearance){
        appearance.setVisitor(null);
        appearances.remove(appearance);
    }
}
