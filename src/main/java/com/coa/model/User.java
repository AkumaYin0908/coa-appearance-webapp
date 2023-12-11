package com.coa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

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
    private String userName;

    @Column(name="password")
    private String password;

    @Column(name="active")
    private boolean active;


    @Column(name="name")
    private String name;


    @Column(name="position")
    private String position;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name="role_id"))
    private Collection<Role> roles;

    public User(String userName, String password, boolean active, String name, String position) {
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.name = name;
        this.position = position;
    }


}
