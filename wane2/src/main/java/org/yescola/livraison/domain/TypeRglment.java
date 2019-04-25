package org.yescola.livraison.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TypeRglment.
 */
@Entity
@Table(name = "type_rglment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "typerglment")
public class TypeRglment implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "etat")
    private String etat;

    @OneToMany(mappedBy = "typeRglment")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reglement> reglements = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public TypeRglment libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getEtat() {
        return etat;
    }

    public TypeRglment etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Set<Reglement> getReglements() {
        return reglements;
    }

    public TypeRglment reglements(Set<Reglement> reglements) {
        this.reglements = reglements;
        return this;
    }

    public TypeRglment addReglement(Reglement reglement) {
        this.reglements.add(reglement);
        reglement.setTypeRglment(this);
        return this;
    }

    public TypeRglment removeReglement(Reglement reglement) {
        this.reglements.remove(reglement);
        reglement.setTypeRglment(null);
        return this;
    }

    public void setReglements(Set<Reglement> reglements) {
        this.reglements = reglements;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TypeRglment typeRglment = (TypeRglment) o;
        if (typeRglment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeRglment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeRglment{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
