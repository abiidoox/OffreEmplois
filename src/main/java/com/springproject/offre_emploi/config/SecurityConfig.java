package com.springproject.offre_emploi.config;

import com.springproject.offre_emploi.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/assets/**").permitAll()
                        .requestMatchers("/assets2/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/index").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/chooseProfil").permitAll()
                        .requestMatchers("/RegistreRecruteur").permitAll()
                        .requestMatchers("/RegistreEmploye").permitAll()
                        .requestMatchers("/RegistreRecruteurX").permitAll()
                        .requestMatchers("/RegistreRecruteurY").permitAll()
                        .requestMatchers("/RegistreEmployeX").permitAll()
                        .requestMatchers("/RegistreEmployeY").permitAll()
                        .requestMatchers("offres/filter").permitAll()
                        .requestMatchers("/offres/**").hasAnyAuthority("recruteur","admin")
                        .requestMatchers("/Edit_Offre").hasAnyAuthority("recruteur")
                        .requestMatchers("/rec/**").hasAnyAuthority("recruteur","admin")
                        .requestMatchers("/Candidatures/**").hasAnyAuthority("Employer","admin")
                        .requestMatchers("/candidature/**").hasAnyAuthority("Employer")
                        .requestMatchers("/Postuler/**").hasAnyAuthority("Employer","admin")
                        .requestMatchers("/offre/**").hasAnyAuthority("Employer","admin","recruteur")
                        .requestMatchers("/api/**").hasAnyAuthority("admin")
                        .requestMatchers("/cv/**").hasAnyAuthority("recruteur","admin")
                        .requestMatchers("/toProfilRecruteur").hasAnyAuthority("recruteur")
                        .requestMatchers("/Recruteurs").hasAnyAuthority("Employer")
                        .requestMatchers("/emp/**").hasAnyAuthority("Employer")
                        .requestMatchers("/toProfilEmploye").hasAnyAuthority("Employer")
                        .requestMatchers("/Offre/**").hasAnyAuthority("Employer","admin","recruteur")
                        .requestMatchers("/Employeres").hasAnyAuthority("admin")
                        .requestMatchers("/Employer/**").hasAnyAuthority("admin")
                        .requestMatchers("/Recruteurs_Admin").hasAnyAuthority("admin")
                        .requestMatchers("/to_dashbord").authenticated()
                        .requestMatchers("/scrape").permitAll()
                        .requestMatchers("/contact").permitAll()
                        .requestMatchers("/service").permitAll()
                        .requestMatchers("/contact/mail").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin().defaultSuccessUrl("/to_dashbord",true)
                .loginPage("/login")
                .permitAll();


     //   http.userDetailsService(customUserDetailsService);
        return http.build();
    }
}
