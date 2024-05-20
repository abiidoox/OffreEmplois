package com.springproject.offre_emploi.Beans;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CandidatureId implements Serializable {
    private static final long serialVersionUID = 4371694791313774071L;
    @Column(name = "Che_id", nullable = false)
    private Integer cheId;

    @Column(name = "Off_id", nullable = false)
    private Integer offId;

    public Integer getCheId() {
        return cheId;
    }

    public void setCheId(Integer cheId) {
        this.cheId = cheId;
    }

    public Integer getOffId() {
        return offId;
    }

    public void setOffId(Integer offId) {
        this.offId = offId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CandidatureId entity = (CandidatureId) o;
        return Objects.equals(this.offId, entity.offId) &&
                Objects.equals(this.cheId, entity.cheId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offId, cheId);
    }

}