package com.onlinedukaan.config;

import com.onlinedukaan.model.CustomOauth2User;
import com.onlinedukaan.service.CustomOAuth2UserService;
import com.onlinedukaan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    UserService userService;

    RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/register", "/images/**", "/productImages/**", "/forgotpassword", "/addToCart/**", "/cart/**", "/shop/**").permitAll()
                .antMatchers("/dukaandaar/**").hasRole("ADMIN")
                .antMatchers("/getProducts/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/signin")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
                .defaultSuccessUrl("/signed")
                .failureUrl("/signin?error=true")
                .and()
                .oauth2Login()
                .loginPage("/signin")
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
                        String email = Objects.requireNonNull(token.getPrincipal().getAttribute("email")).toString();
                        String firstName = Objects.requireNonNull(token.getPrincipal().getAttribute("given_name")).toString();
                        String lastName = Objects.requireNonNull(token.getPrincipal().getAttribute("family_name")).toString();
                        userService.processOAuthPostLogin(email, firstName, lastName);
                        redirectStrategy.sendRedirect(request, response, "/");
                    }
                })
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/signin")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .and()
                .csrf()
                .disable();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public CustomOauth2User customOauth2User() {
        return new CustomOauth2User();
    }
}