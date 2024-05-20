package com.springproject.offre_emploi.Beans;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "candidature")
public class Candidature {
    @EmbeddedId
    private CandidatureId id;

    @MapsId("cheId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Che_id", nullable = false)
    private ChercheurdEmploi che;

    @MapsId("offId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Off_id", nullable = false)
    private Offre off;

    @Column(name = "dateCandidature")
    private Instant dateCandidature;

    @Column(name = "lettreMotivation", length = 254)
    private String lettreMotivation;

    public CandidatureId getId() {
        return id;
    }

    public void setId(CandidatureId id) {
        this.id = id;
    }

    public ChercheurdEmploi getChe() {
        return che;
    }

    public void setChe(ChercheurdEmploi che) {
        this.che = che;
    }

    public Offre getOff() {
        return off;
    }

    public void setOff(Offre off) {
        this.off = off;
    }

    public Instant getDateCandidature() {
        return dateCandidature;
    }

    public void setDateCandidature(Instant dateCandidature) {
        this.dateCandidature = dateCandidature;
    }

    public String getLettreMotivation() {
        return lettreMotivation;
    }

    public void setLettreMotivation(String lettreMotivation) {
        this.lettreMotivation = lettreMotivation;
    }

}