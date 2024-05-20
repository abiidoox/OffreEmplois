package com.springproject.offre_emploi.Beans;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "chercheurd_emploi")
public class ChercheurdEmploi {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id", nullable = false)
    private Utilisateur utilisateurs;

    @Column(name = "nom", length = 254)
    private String nom;

    @Column(name = "prenom", length = 254)
    private String prenom;

    @Column(name = "cv", length = 254)
    private String cv;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Utilisateur getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(Utilisateur utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

}