package com.onlinedukaan.service;

import com.onlinedukaan.model.Role;
import com.onlinedukaan.model.User;
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
    UserRepo user_repo;

    @Autowired
    RoleRepo role_repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return user_repo.findAll();
    }

    public void addUser( User user)  {

         Role role = role_repo.findByName("USER");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        user.setPassword(passwordEncoder.encode((user.getPassword())));
        user_repo.save(user);
    }

    private Role checkRoleExist() {
            Role role = new Role();
            role.setName("USER");
            return role_repo.save(role);
        }
        public User findUserByEmail(String email)
        {
            return user_repo.findUserByEmail(email);
        }

    }


