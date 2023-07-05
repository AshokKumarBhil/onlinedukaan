package com.onlinedukaan.service;

import com.onlinedukaan.model.Role;
import com.onlinedukaan.model.User;
import com.onlinedukaan.repository.Provider;
import com.onlinedukaan.repository.RoleRepository;
import com.onlinedukaan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {

        Role role = roleRepository.findByName("ROLE_USER");

        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode((user.getPassword())));
        }
        user.setProvider(Provider.LOCAL);
        userRepository.save(user);
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void processOAuthPostLogin(String email, String firstName, String lastName) {
        User existUser = userRepository.findByEmail(email);

        if (existUser == null) {
            User newUser = new User();
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setEmail(email);
            newUser.setPassword(null);
            newUser.setProvider(Provider.GOOGLE);
            Role role = roleRepository.findByName("ROLE_USER");
            if (role == null) {
                role = checkRoleExist();
            }
            newUser.setRoles(Arrays.asList(role));
            userRepository.save(newUser);
        }
    }
}


