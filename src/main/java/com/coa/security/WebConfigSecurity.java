package com.coa.security;


import com.coa.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.net.http.HttpClient;

@Configuration
public class WebConfigSecurity {

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {return new BCryptPasswordEncoder();}

    @Bean
    public DaoAuthenticationProvider getAuthenticationProvider(UserService userService){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(getPasswordEncoder());

        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain getFilterChain(HttpSecurity httpSecurity, UserService userService)
            throws Exception {

            httpSecurity.authorizeHttpRequests(configurer ->
                    configurer
                            .requestMatchers("/css/**").permitAll()
                            .requestMatchers("/js/**").permitAll()
                            .requestMatchers("/register/**").permitAll()
                            .requestMatchers("/images/**").permitAll()
                            .requestMatchers("/fragments/**").permitAll()
                            .requestMatchers("/layout/**").permitAll()
                            .requestMatchers("/").permitAll()

                            .anyRequest().authenticated()
            )
                    .formLogin(login -> login.loginPage("/login")
                            .loginProcessingUrl("/authenticate")
                            .successHandler(new CustomAuthenticationSuccessHandler(userService))
                            .permitAll()
                    )
                    .logout(LogoutConfigurer::permitAll)

                    .exceptionHandling(configurer-> configurer.accessDeniedPage("/access-denied"));

            return httpSecurity.build();

    }




}
