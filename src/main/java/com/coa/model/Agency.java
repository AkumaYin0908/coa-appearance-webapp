package com.coa.model;

import com.coa.model.address.Address;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="agency")
@ToString(exclude = "visitors")
public class Agency {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name = "name")
    private String name;

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
        visitors.remove(visitor);
    }
}
