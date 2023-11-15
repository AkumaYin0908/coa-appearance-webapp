package com.coa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name="username")
    @NotNull(message = "required")
    @Size(min = 5, message = "must have at least 5 characters")
    private String userName;

    @NotNull(message = "required")
    @Size(min = 8, message = "must have at least 8 characters")
    @Column(name="password")
    private String password;

    @Column(name="active")
    private boolean active;

    @NotNull(message = "required")
    @Size(min = 10,max = 50, message = "must have at least 10 characters but not more than 50 characters")
    @Column(name="name")
    private String name;

    @NotNull(message = "required")
    @Size(min = 10,max = 50, message = "must have at least 20 characters but not more than 50 characters")
    @Column(name="position")
    private String position;

    public User(String userName, String password, boolean active, String name, String position) {
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.name = name;
        this.position = position;
    }


}
