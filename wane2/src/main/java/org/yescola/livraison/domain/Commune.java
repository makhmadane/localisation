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
 * A Commune.
 */
@Entity
@Table(name = "commune")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "commune")
public class Commune implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_com")
    private String nomCom;

    @Column(name = "longitutde")
    private String longitutde;

    @Column(name = "altitude")
    private String altitude;

    @Column(name = "etat")
    private String etat;

    @ManyToMany(mappedBy = "communes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Secteur> secteurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomCom() {
        return nomCom;
    }

    public Commune nomCom(String nomCom) {
        this.nomCom = nomCom;
        return this;
    }

    public void setNomCom(String nomCom) {
        this.nomCom = nomCom;
    }

    public String getLongitutde() {
        return longitutde;
    }

    public Commune longitutde(String longitutde) {
        this.longitutde = longitutde;
        return this;
    }

    public void setLongitutde(String longitutde) {
        this.longitutde = longitutde;
    }

    public String getAltitude() {
        return altitude;
    }

    public Commune altitude(String altitude) {
        this.altitude = altitude;
        return this;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getEtat() {
        return etat;
    }

    public Commune etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Set<Secteur> getSecteurs() {
        return secteurs;
    }

    public Commune secteurs(Set<Secteur> secteurs) {
        this.secteurs = secteurs;
        return this;
    }

    public Commune addSecteur(Secteur secteur) {
        this.secteurs.add(secteur);
        secteur.getCommunes().add(this);
        return this;
    }

    public Commune removeSecteur(Secteur secteur) {
        this.secteurs.remove(secteur);
        secteur.getCommunes().remove(this);
        return this;
    }

    public void setSecteurs(Set<Secteur> secteurs) {
        this.secteurs = secteurs;
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
        Commune commune = (Commune) o;
        if (commune.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commune.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Commune{" +
            "id=" + getId() +
            ", nomCom='" + getNomCom() + "'" +
            ", longitutde='" + getLongitutde() + "'" +
            ", altitude='" + getAltitude() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
