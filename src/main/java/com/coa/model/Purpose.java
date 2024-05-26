package com.coa.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "purpose")
public class Purpose {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    //to be renamed as description
    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "purpose", cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<Appearance> appearanceSet;

    //to be removed
    public Purpose(String description) {
        this.description = description;
    }
}
