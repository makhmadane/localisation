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
 * A Secteur.
 */
@Entity
@Table(name = "secteur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "secteur")
public class Secteur implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_secteur")
    private String nomSecteur;

    @Column(name = "longitutde")
    private String longitutde;

    @Column(name = "altitude")
    private String altitude;

    @Column(name = "etat")
    private String etat;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "secteur_route",
               joinColumns = @JoinColumn(name = "secteur_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "route_id", referencedColumnName = "id"))
    private Set<Route> routes = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "secteur_commune",
               joinColumns = @JoinColumn(name = "secteur_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "commune_id", referencedColumnName = "id"))
    private Set<Commune> communes = new HashSet<>();

    @OneToMany(mappedBy = "secteur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Boutique> boutiques = new HashSet<>();
    @OneToMany(mappedBy = "secteur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Commande> commandes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomSecteur() {
        return nomSecteur;
    }

    public Secteur nomSecteur(String nomSecteur) {
        this.nomSecteur = nomSecteur;
        return this;
    }

    public void setNomSecteur(String nomSecteur) {
        this.nomSecteur = nomSecteur;
    }

    public String getLongitutde() {
        return longitutde;
    }

    public Secteur longitutde(String longitutde) {
        this.longitutde = longitutde;
        return this;
    }

    public void setLongitutde(String longitutde) {
        this.longitutde = longitutde;
    }

    public String getAltitude() {
        return altitude;
    }

    public Secteur altitude(String altitude) {
        this.altitude = altitude;
        return this;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getEtat() {
        return etat;
    }

    public Secteur etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public Secteur routes(Set<Route> routes) {
        this.routes = routes;
        return this;
    }

    public Secteur addRoute(Route route) {
        this.routes.add(route);
        route.getSecteurs().add(this);
        return this;
    }

    public Secteur removeRoute(Route route) {
        this.routes.remove(route);
        route.getSecteurs().remove(this);
        return this;
    }

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
    }

    public Set<Commune> getCommunes() {
        return communes;
    }

    public Secteur communes(Set<Commune> communes) {
        this.communes = communes;
        return this;
    }

    public Secteur addCommune(Commune commune) {
        this.communes.add(commune);
        commune.getSecteurs().add(this);
        return this;
    }

    public Secteur removeCommune(Commune commune) {
        this.communes.remove(commune);
        commune.getSecteurs().remove(this);
        return this;
    }

    public void setCommunes(Set<Commune> communes) {
        this.communes = communes;
    }

    public Set<Boutique> getBoutiques() {
        return boutiques;
    }

    public Secteur boutiques(Set<Boutique> boutiques) {
        this.boutiques = boutiques;
        return this;
    }

    public Secteur addBoutique(Boutique boutique) {
        this.boutiques.add(boutique);
        boutique.setSecteur(this);
        return this;
    }

    public Secteur removeBoutique(Boutique boutique) {
        this.boutiques.remove(boutique);
        boutique.setSecteur(null);
        return this;
    }

    public void setBoutiques(Set<Boutique> boutiques) {
        this.boutiques = boutiques;
    }

    public Set<Commande> getCommandes() {
        return commandes;
    }

    public Secteur commandes(Set<Commande> commandes) {
        this.commandes = commandes;
        return this;
    }

    public Secteur addCommande(Commande commande) {
        this.commandes.add(commande);
        commande.setSecteur(this);
        return this;
    }

    public Secteur removeCommande(Commande commande) {
        this.commandes.remove(commande);
        commande.setSecteur(null);
        return this;
    }

    public void setCommandes(Set<Commande> commandes) {
        this.commandes = commandes;
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
        Secteur secteur = (Secteur) o;
        if (secteur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), secteur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Secteur{" +
            "id=" + getId() +
            ", nomSecteur='" + getNomSecteur() + "'" +
            ", longitutde='" + getLongitutde() + "'" +
            ", altitude='" + getAltitude() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
