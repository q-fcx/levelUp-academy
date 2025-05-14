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
                .requestMatchers("/api/v1/parent/register", "/api/v1/player/register", "/api/v1/trainer/register", "/api/v1/pro/register","/api/v1/contract/**", "/api/v1/payments/callback").permitAll()

                .requestMatchers("/api/v1/parent/edit","/api/v1/parent/delete", "/api/v1/parent/add-child",
                                    "/api/v1/parent/update-child", "/api/v1/parent/delete-child", "/api/v1/parent/child-statistic","/api/v1/parent/get-games","/api/v1/parent/get-child-stati-by-parent").hasAuthority("PARENTS")
                .requestMatchers("/api/v1/subscription/**").hasAnyAuthority("PARENTS","PLAYER")


                .requestMatchers("/api/v1/booking/add", "api/v1/booking/cancel", "api/v1/booking/check", "/api/v1/booking/get-all",
                                     "/api/v1/review/add","/api/v1/review/delete").hasAnyAuthority("PLAYER", "PARENTS","PRO")


                .requestMatchers("/api/v1/game/**","/api/v1/contract/**", "/api/v1/moderator/edit", "/api/v1/moderator/delete","/api/v1/parent/get",
                              "/api/v1/moderator/get-all-pro", "/api/v1/moderator/review-contract","/api/v1/moderator/send-exam","/api/v1/player/get","/api/v1/player/get-player",
                              "/api/v1/pro/get","/api/v1/pro/cv","/api/v1/review/get-all", "/api/v1/session/get", "/api/v1/session/add","/api/v1/session/update","/api/v1/session/del",
                                "/api/v1/session/change-session","/api/v1/trainer/get").hasAuthority("MODERATOR")

                .requestMatchers("/api/v1/player/edit","/api/v1/player/delete","/api/v1/player/player").hasAuthority("PLAYER")

                .requestMatchers("/api/v1/pro/edit","/api/v1/pro/delete","/api/v1/pro/accept","/api/v1/pro/reject","/api/v1/pro/expireAccount","/api/v1/pro/professional").hasAuthority("PRO")

                .requestMatchers("/api/v1/pro/approve","/api/v1/user/get-all","/api/v1/trainer/cv","/api/v1/trainer/approve-trainer","/api/v1/trainer/reject-trainer","/api/v1/moderator/register").hasAuthority("ADMIN")

                .requestMatchers("/api/v1/review/get-my-reviews","/api/v1/session/notify-start","/api/v1/trainer/get-players","/api/v1/child-statistic/**","/api/v1/player-statistic/**",
                                "/api/v1/pro-statistic/**", "/api/v1/trainer/edit", "/api/v1/trainer/delete","/api/v1/trainer/give-player","/api/v1/trainer/give-pro","/api/v1/trainer/give-child",
                                 "/api/v1/trainer/addStatisticToChild", "/api/v1/trainer/addStatisticToPlayer", "/api/v1/trainer/addStatisticToPro").hasAuthority("TRAINER")
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
