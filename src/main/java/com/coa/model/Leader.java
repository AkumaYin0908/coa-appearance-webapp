package com.coa.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
