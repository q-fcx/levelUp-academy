package com.levelup.levelup_academy.Config;

import com.levelup.levelup_academy.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class ConfigurationSecurity {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/parent/register", "/api/v1/contract/add").permitAll()
                .requestMatchers("/api/v1/parent/edit","/api/v1/parent/delete", "/api/v1/parent/add-child",
                                    "/api/v1/parent/update-child", "/api/v1/parent/delete-child", "/api/v1/parent/child-statistic","/api/v1/parent/get-games").hasAuthority("PARENTS")
                .requestMatchers("/api/v1/booking/add", "api/v1/booking/cancel", "api/v1/booking/check", "/api/v1/booking/get-all").hasAnyAuthority("PLAYER", "PARENTS","PRO")
                .requestMatchers("/api/v1/game/**","/api/v1/contract/**", "/api/v1/moderator/edit", "/api/v1/moderator/delete", "/api/v1/moderator/get-all-pro",
                                    "/api/v1/moderator/get-all-pro", "/api/v1/moderator/review-contract","/api/v1/moderator/send-exam").hasAuthority("MODERATOR")

                .requestMatchers("/api/v1/player/register", "/api/v1/trainer/register").permitAll()
                .requestMatchers("/api/v1/player/edit","/api/v1/player/delete").hasAuthority("PLAYER")
                .requestMatchers("/api/v1/pro/register").permitAll()
                .requestMatchers("/api/v1/pro/edit","/api/v1/pro/delete").hasAuthority("PRO")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/user/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return httpSecurity.build();


    }
}
