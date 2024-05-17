package com.coa.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data

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
}
