package com.coa.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "leader")
public class Position {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name="name")
    @NotBlank(message = "must not be blank")
    @Size(min=10,message = "must have at least 10 characters")
    private String name;

    @OneToMany(mappedBy = "position",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    List<Visitor> visitors;

    public Position(String name) {
        this.name = name;
    }
}
