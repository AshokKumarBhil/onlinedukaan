package com.onlinedukaan.model;

import com.onlinedukaan.repository.Provider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
public class User {

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

    @Enumerated(EnumType.STRING)
    Provider provider;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    public User(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getFirstName() {
        return firstName;
    }
}
