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
 * A Qualite.
 */
@Entity
@Table(name = "qualite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "qualite")
public class Qualite implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "etat")
    private String etat;

    @OneToMany(mappedBy = "qualite")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Boutique> boutiques = new HashSet<>();
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

    public Qualite libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getEtat() {
        return etat;
    }

    public Qualite etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Set<Boutique> getBoutiques() {
        return boutiques;
    }

    public Qualite boutiques(Set<Boutique> boutiques) {
        this.boutiques = boutiques;
        return this;
    }

    public Qualite addBoutique(Boutique boutique) {
        this.boutiques.add(boutique);
        boutique.setQualite(this);
        return this;
    }

    public Qualite removeBoutique(Boutique boutique) {
        this.boutiques.remove(boutique);
        boutique.setQualite(null);
        return this;
    }

    public void setBoutiques(Set<Boutique> boutiques) {
        this.boutiques = boutiques;
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
        Qualite qualite = (Qualite) o;
        if (qualite.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), qualite.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Qualite{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
