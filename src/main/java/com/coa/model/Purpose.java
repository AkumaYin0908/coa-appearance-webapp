package com.coa.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
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
    private List<Appearance> appearances;


    public Purpose(String description) {
        this.description = description;
    }

    public void addAppearance(Appearance appearance){
        if(appearances == null){
            appearances = new ArrayList<>();
        }
        appearance.setPurpose(this);
        appearances.add(appearance);
    }

    public void removeAppearance(Appearance appearance){
        appearance.setPurpose(null);
        if(appearances != null) appearances.remove(appearance);
    }
}
