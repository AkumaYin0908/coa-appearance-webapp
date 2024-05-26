package com.coa.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "position")
public class Position {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;


    //to be renamed as title
    @Column(name="title")
    private String title;

    @ToString.Exclude
    @OneToMany(mappedBy = "position",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    List<Visitor> visitors;

    //to be removed
    public Position(String title) {
        this.title = title;
    }

    public void addVisitor(Visitor visitor){
        if(visitors == null){
            visitors = new ArrayList<>();
        }

        visitor.setPosition(this);
        visitors.add(visitor);
    }

    public void removeVisitor(Visitor visitor){
        visitor.setAgency(null);
        if(visitors !=null) visitors.remove(visitor);
    }
}
