package com.coa.model.address;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public abstract class Base {

    @Id
    @Column(name="code")
    private String code;


    @Column(name="name")
    private String name;

    public Base(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
