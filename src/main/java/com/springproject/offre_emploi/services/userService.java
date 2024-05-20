package com.springproject.offre_emploi.services;

import com.springproject.offre_emploi.Beans.Utilisateur;
import org.springframework.stereotype.Service;

@Service
public interface userService {
    Utilisateur findUserByEmail(String email);
}
