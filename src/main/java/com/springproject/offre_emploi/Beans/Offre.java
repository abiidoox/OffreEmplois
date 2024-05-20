package com.springproject.offre_emploi.Beans;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;
import org.springframework.jdbc.core.SqlTypeValue;

import java.sql.SQLData;
import java.sql.SQLType;
import java.time.Instant;

@Entity
@Table(name = "offre")
public class Offre {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Rec_id", nullable = false)
    private Recruteur rec;

    @Column(name = "titre", length = 254)
    private String titre;

    @Column(name = "description")
    private String description;

    @Column(name = "salaire")
    private Long salaire;

    @Column(name = "datePublication")
    private Instant datePublication;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Recruteur getRec() {
        return rec;
    }

    public void setRec(Recruteur rec) {
        this.rec = rec;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSalaire() {
        return salaire;
    }

    public void setSalaire(Long salaire) {
        this.salaire = salaire;
    }

    public Instant getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Instant datePublication) {
        this.datePublication = datePublication;
    }

}