package com.onlinedukaan.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @ManyToMany(mappedBy = "roles")
    List<User> users;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleId;
    @Column(nullable = false, unique = true)
    private String name;
}
