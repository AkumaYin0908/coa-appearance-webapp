package com.coa.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "leader")
public class Leader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name="name")
    private String name;


    @Column(name="position")
    private String position;


    @Column(name="in_charge")
    private boolean inCharge;

    public Leader(String name, String position, boolean inCharge) {
        this.name = name;
        this.position = position;
        this.inCharge = inCharge;
    }

    public Leader(boolean inCharge){
        this.inCharge=inCharge;
    }
}
