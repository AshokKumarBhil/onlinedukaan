package com.onlinedukaan.service;

import com.onlinedukaan.model.Role;
import com.onlinedukaan.model.User;
import com.onlinedukaan.repo.Provider;
import com.onlinedukaan.repo.RoleRepo;
import com.onlinedukaan.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public void addUser(User user) {

        Role role = roleRepo.findByName("ROLE_USER");

        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        user.setPassword(passwordEncoder.encode((user.getPassword())));
        userRepo.save(user);
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepo.save(role);
    }

    public User findUserByEmail(String email) {
        return userRepo.findUserByEmail(email);

    }

    public void processOAuthPostLogin(String email, String firstName, String lastName) {
        User existUser = userRepo.findUserByEmail(email);

        if (existUser == null) {
            User newUser = new User();
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setEmail(email);
            newUser.setPassword(null);
            newUser.setProvider(Provider.GOOGLE);
            Role role = roleRepo.findByName("ROLE_USER");
            if (role == null) {
                role = checkRoleExist();
            }
            newUser.setRoles(Arrays.asList(role));
            userRepo.save(newUser);
        }
    }
}


