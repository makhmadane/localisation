package org.yescola.livraison.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import org.yescola.livraison.domain.enumeration.Civilite;

/**
 * A Boutique.
 */
@Entity
@Table(name = "boutique")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "boutique")
public class Boutique implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_boutique")
    private String nomBoutique;

    @Column(name = "prenom")
    private String prenom;

    @Enumerated(EnumType.STRING)
    @Column(name = "civilite")
    private Civilite civilite;

    @Column(name = "nom")
    private String nom;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "telproprietaire")
    private String telproprietaire;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "altitude")
    private String altitude;

    @Column(name = "etat")
    private String etat;

    @Column(name = "periodicite")
    private String periodicite;

    @Column(name = "date_dernier_com")
    private String dateDernierCom;

    @Column(name = "date_dernier_liv")
    private String dateDernierLiv;

    @Column(name = "solde")
    private String solde;

    @Column(name = "commande_en_cours")
    private String commandeEnCours;

    @OneToMany(mappedBy = "boutique")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StockInitial> stockInitials = new HashSet<>();
    @OneToMany(mappedBy = "boutique")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DetailRoute> detailRoutes = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("boutiques")
    private Metier metier;

    @ManyToOne
    @JsonIgnoreProperties("boutiques")
    private Qualite qualite;

    @ManyToOne
    @JsonIgnoreProperties("boutiques")
    private Secteur secteur;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "boutique_route",
               joinColumns = @JoinColumn(name = "boutique_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "route_id", referencedColumnName = "id"))
    private Set<Route> routes = new HashSet<>();

    @OneToOne(mappedBy = "boutique")
    @JsonIgnore
    private Commande commande;

    @ManyToOne
    @JsonIgnoreProperties("boutiques")
    private Prospection prospection;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomBoutique() {
        return nomBoutique;
    }

    public Boutique nomBoutique(String nomBoutique) {
        this.nomBoutique = nomBoutique;
        return this;
    }

    public void setNomBoutique(String nomBoutique) {
        this.nomBoutique = nomBoutique;
    }

    public String getPrenom() {
        return prenom;
    }

    public Boutique prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Civilite getCivilite() {
        return civilite;
    }

    public Boutique civilite(Civilite civilite) {
        this.civilite = civilite;
        return this;
    }

    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }

    public String getNom() {
        return nom;
    }

    public Boutique nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public Boutique telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelproprietaire() {
        return telproprietaire;
    }

    public Boutique telproprietaire(String telproprietaire) {
        this.telproprietaire = telproprietaire;
        return this;
    }

    public void setTelproprietaire(String telproprietaire) {
        this.telproprietaire = telproprietaire;
    }

    public String getLongitude() {
        return longitude;
    }

    public Boutique longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public Boutique altitude(String altitude) {
        this.altitude = altitude;
        return this;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getEtat() {
        return etat;
    }

    public Boutique etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getPeriodicite() {
        return periodicite;
    }

    public Boutique periodicite(String periodicite) {
        this.periodicite = periodicite;
        return this;
    }

    public void setPeriodicite(String periodicite) {
        this.periodicite = periodicite;
    }

    public String getDateDernierCom() {
        return dateDernierCom;
    }

    public Boutique dateDernierCom(String dateDernierCom) {
        this.dateDernierCom = dateDernierCom;
        return this;
    }

    public void setDateDernierCom(String dateDernierCom) {
        this.dateDernierCom = dateDernierCom;
    }

    public String getDateDernierLiv() {
        return dateDernierLiv;
    }

    public Boutique dateDernierLiv(String dateDernierLiv) {
        this.dateDernierLiv = dateDernierLiv;
        return this;
    }

    public void setDateDernierLiv(String dateDernierLiv) {
        this.dateDernierLiv = dateDernierLiv;
    }

    public String getSolde() {
        return solde;
    }

    public Boutique solde(String solde) {
        this.solde = solde;
        return this;
    }

    public void setSolde(String solde) {
        this.solde = solde;
    }

    public String getCommandeEnCours() {
        return commandeEnCours;
    }

    public Boutique commandeEnCours(String commandeEnCours) {
        this.commandeEnCours = commandeEnCours;
        return this;
    }

    public void setCommandeEnCours(String commandeEnCours) {
        this.commandeEnCours = commandeEnCours;
    }

    public Set<StockInitial> getStockInitials() {
        return stockInitials;
    }

    public Boutique stockInitials(Set<StockInitial> stockInitials) {
        this.stockInitials = stockInitials;
        return this;
    }

    public Boutique addStockInitial(StockInitial stockInitial) {
        this.stockInitials.add(stockInitial);
        stockInitial.setBoutique(this);
        return this;
    }

    public Boutique removeStockInitial(StockInitial stockInitial) {
        this.stockInitials.remove(stockInitial);
        stockInitial.setBoutique(null);
        return this;
    }

    public void setStockInitials(Set<StockInitial> stockInitials) {
        this.stockInitials = stockInitials;
    }

    public Set<DetailRoute> getDetailRoutes() {
        return detailRoutes;
    }

    public Boutique detailRoutes(Set<DetailRoute> detailRoutes) {
        this.detailRoutes = detailRoutes;
        return this;
    }

    public Boutique addDetailRoute(DetailRoute detailRoute) {
        this.detailRoutes.add(detailRoute);
        detailRoute.setBoutique(this);
        return this;
    }

    public Boutique removeDetailRoute(DetailRoute detailRoute) {
        this.detailRoutes.remove(detailRoute);
        detailRoute.setBoutique(null);
        return this;
    }

    public void setDetailRoutes(Set<DetailRoute> detailRoutes) {
        this.detailRoutes = detailRoutes;
    }

    public Metier getMetier() {
        return metier;
    }

    public Boutique metier(Metier metier) {
        this.metier = metier;
        return this;
    }

    public void setMetier(Metier metier) {
        this.metier = metier;
    }

    public Qualite getQualite() {
        return qualite;
    }

    public Boutique qualite(Qualite qualite) {
        this.qualite = qualite;
        return this;
    }

    public void setQualite(Qualite qualite) {
        this.qualite = qualite;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public Boutique secteur(Secteur secteur) {
        this.secteur = secteur;
        return this;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public Boutique routes(Set<Route> routes) {
        this.routes = routes;
        return this;
    }

    public Boutique addRoute(Route route) {
        this.routes.add(route);
        route.getBoutiques().add(this);
        return this;
    }

    public Boutique removeRoute(Route route) {
        this.routes.remove(route);
        route.getBoutiques().remove(this);
        return this;
    }

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
    }

    public Commande getCommande() {
        return commande;
    }

    public Boutique commande(Commande commande) {
        this.commande = commande;
        return this;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Prospection getProspection() {
        return prospection;
    }

    public Boutique prospection(Prospection prospection) {
        this.prospection = prospection;
        return this;
    }

    public void setProspection(Prospection prospection) {
        this.prospection = prospection;
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
        Boutique boutique = (Boutique) o;
        if (boutique.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), boutique.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Boutique{" +
            "id=" + getId() +
            ", nomBoutique='" + getNomBoutique() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", civilite='" + getCivilite() + "'" +
            ", nom='" + getNom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", telproprietaire='" + getTelproprietaire() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", altitude='" + getAltitude() + "'" +
            ", etat='" + getEtat() + "'" +
            ", periodicite='" + getPeriodicite() + "'" +
            ", dateDernierCom='" + getDateDernierCom() + "'" +
            ", dateDernierLiv='" + getDateDernierLiv() + "'" +
            ", solde='" + getSolde() + "'" +
            ", commandeEnCours='" + getCommandeEnCours() + "'" +
            "}";
    }
}
