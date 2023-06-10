package com.onlinedukaan.config;

import com.onlinedukaan.model.Role;
import com.onlinedukaan.model.User;
import com.onlinedukaan.repo.RoleRepo;
import com.onlinedukaan.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class GoogleOauth2SuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String email = token.getPrincipal().getAttribute("email").toString();
        if (userRepo.findUserByEmail(email) == null) {

        } else {
            User user = new User();
            user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
            user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
            user.setEmail(email);
            Role role = roleRepo.findByName("USER");
            user.setRoles(Arrays.asList(role));
            userRepo.save(user);
        }
        redirectStrategy.sendRedirect(request, response, "/");
    }
}
