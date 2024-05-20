package com.springproject.offre_emploi.Beans;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "administrateur")
public class Administrateur {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id", nullable = false)
    private Utilisateur utilisateurs;

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

}