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
    @NotBlank(message = "must not be blank")
    @Size(min=10,message = "must have at least 10 characters")
    private String name;

    @NotBlank(message = "must not be blank")
    @Size(min=10,message = "must have at least 10 characters")
    @Column(name="position")
    private String position;


    @Column(name="in_charge")
    private boolean inCharge;

    public Leader(String name, String position, boolean inCharge) {
        this.name = name;
        this.position = position;
        this.inCharge = inCharge;
    }
}
