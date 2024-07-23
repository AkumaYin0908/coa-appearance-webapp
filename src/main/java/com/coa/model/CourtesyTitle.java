package com.coa.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "courtesy_title")
public class CourtesyTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="title")
    private String title;

    @OneToMany(mappedBy = "courtesyTitle",cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.REFRESH})
    private List<Visitor> visitors;


    public CourtesyTitle(String title) {
        this.title = title;
    }

    public void addVisitor(Visitor visitor){
        if(visitors == null){
            visitors = new ArrayList<>();
        }
        visitor.setCourtesyTitle(this);
        visitors.add(visitor);
    }

    public void removeVisitor(Visitor visitor){
        visitor.setCourtesyTitle(null);
        visitors.remove(visitor);
    }
}
