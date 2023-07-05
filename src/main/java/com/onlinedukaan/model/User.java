package com.onlinedukaan.model;

import com.onlinedukaan.repository.Provider;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Enumerated(EnumType.STRING)
    Provider provider;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @Column(nullable = false)
    @NotEmpty(message = "First name should not be empty")
    private String firstName;

    @NotEmpty(message = "Last name should not be empty")
    @Column(nullable = false)
    private String lastName;

    private String password;

    @Email
    @Column(unique = true, nullable = false)
    @NotEmpty(message = "email should not be empty")
    private String email;

    @Column(nullable = false)
    private int wallet=0;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Role> roles = new ArrayList<>();

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cart_id",referencedColumnName = "cart_id")
    private Cart cart;

    public User(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

}
