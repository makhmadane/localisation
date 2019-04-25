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
 * A Metier.
 */
@Entity
@Table(name = "metier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "metier")
public class Metier implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "etatmetier")
    private String etatmetier;

    @OneToMany(mappedBy = "metier")
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

    public Metier libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getEtatmetier() {
        return etatmetier;
    }

    public Metier etatmetier(String etatmetier) {
        this.etatmetier = etatmetier;
        return this;
    }

    public void setEtatmetier(String etatmetier) {
        this.etatmetier = etatmetier;
    }

    public Set<Boutique> getBoutiques() {
        return boutiques;
    }

    public Metier boutiques(Set<Boutique> boutiques) {
        this.boutiques = boutiques;
        return this;
    }

    public Metier addBoutique(Boutique boutique) {
        this.boutiques.add(boutique);
        boutique.setMetier(this);
        return this;
    }

    public Metier removeBoutique(Boutique boutique) {
        this.boutiques.remove(boutique);
        boutique.setMetier(null);
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
        Metier metier = (Metier) o;
        if (metier.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), metier.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Metier{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", etatmetier='" + getEtatmetier() + "'" +
            "}";
    }
}
