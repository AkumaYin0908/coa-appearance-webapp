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

    @ToString.Exclude
    @OneToMany(mappedBy = "visitor",cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH})
    private List<Appearance> appearances;


    public Visitor(String name, Position position, Agency agency) {
        this.name = name;
        this.position = position;
        this.agency = agency;
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
