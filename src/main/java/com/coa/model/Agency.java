package com.coa.model;

import com.coa.model.address.Address;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="agency")
public class Agency {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "agency",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<Visitor> visitors;

    public Agency(String name) {
        this.name = name;
    }

    public void addVisitor(Visitor visitor){
        if(visitors == null){
            visitors = new ArrayList<>();
        }

        visitor.setAgency(this);
        visitors.add(visitor);
    }

    public void removeVisitor(Visitor visitor){
        visitor.setAgency(null);
        if(visitors !=null) visitors.remove(visitor);
    }
}
