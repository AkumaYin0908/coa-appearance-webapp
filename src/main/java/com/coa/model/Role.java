package com.coa.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Long roleId;

    @Column(name="name")
    private String name;

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "\"roleId\" :" + String.format("\"%s\"",roleId) +
                ", \"name\" :" + String.format("\"%s\"",name) +
                '}';
    }
}
