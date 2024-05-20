package com.springproject.offre_emploi.services;

import com.springproject.offre_emploi.Beans.Utilisateur;
import com.springproject.offre_emploi.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        Utilisateur user = userRepository.findByMail(usernameOrEmail);
        if (user != null) {
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return user.getType().toString();
                }
            });
            return new org.springframework.security.core.userdetails.User(user.getMail(), user.getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("Email ou mot de passe incorrect");
        }
    }
}

