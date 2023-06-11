package com.onlinedukaan.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

public class CustomOauth2User implements OAuth2User {

    private OAuth2User oauth2User;

    public CustomOauth2User(OAuth2User user) {
        this.oauth2User=user;
    }

    public CustomOauth2User() {

    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oauth2User.getAttribute("name");
    }
    public String getEmail() {
        return oauth2User.getAttribute("email");
    }
}
