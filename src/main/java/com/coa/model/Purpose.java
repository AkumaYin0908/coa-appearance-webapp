package com.coa.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "purpose")
public class Purpose {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name="name")
    @NotBlank(message = "must not be blank")
    @Size(min=10,message = "must have at least 10 characters")
    private String purpose;

    @OneToMany(mappedBy = "purpose", cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<Appearance> appearanceSet;



    public Purpose(String purpose) {
        this.purpose = purpose;
    }
}
