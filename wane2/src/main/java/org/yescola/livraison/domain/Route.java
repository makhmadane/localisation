package org.yescola.livraison.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Route.
 */
@Entity
@Table(name = "route")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "route")
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "journee")
    private String journee;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "datedep_com")
    private LocalDate datedepCom;

    @Column(name = "date_r_com")
    private LocalDate dateRCom;

    @Column(name = "heure_dep_com")
    private String heureDepCom;

    @Column(name = "heure_fin_com")
    private String heureFinCom;

    @Column(name = "heure_dep_liv")
    private String heureDepLiv;

    @Column(name = "heure_fin_liv")
    private String heureFinLiv;

    @Column(name = "journee_liv")
    private String journeeLiv;

    @Column(name = "datedep_liv")
    private LocalDate datedepLiv;

    @Column(name = "date_r_liv")
    private LocalDate dateRLiv;

    @Column(name = "etat")
    private String etat;

    @OneToMany(mappedBy = "route")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DetailRoute> detailRoutes = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("routes")
    private MoyenTransport moyenTransport;

    @ManyToOne
    @JsonIgnoreProperties("routes")
    private TypeRoute commande;

    @ManyToOne
    @JsonIgnoreProperties("routes")
    private TypeRoute livraison;

    @ManyToOne
    @JsonIgnoreProperties("routes")
    private User gerantCommande;

    @ManyToOne
    @JsonIgnoreProperties("routes")
    private Employee prevendeur;

    @ManyToOne
    @JsonIgnoreProperties("routes")
    private Employee gerantlivraison;

    @ManyToOne
    @JsonIgnoreProperties("routes")
    private Employee livreur;

    @ManyToMany(mappedBy = "routes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Secteur> secteurs = new HashSet<>();

    @ManyToMany(mappedBy = "routes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Boutique> boutiques = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Route numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getJournee() {
        return journee;
    }

    public Route journee(String journee) {
        this.journee = journee;
        return this;
    }

    public void setJournee(String journee) {
        this.journee = journee;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public Route dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDatedepCom() {
        return datedepCom;
    }

    public Route datedepCom(LocalDate datedepCom) {
        this.datedepCom = datedepCom;
        return this;
    }

    public void setDatedepCom(LocalDate datedepCom) {
        this.datedepCom = datedepCom;
    }

    public LocalDate getDateRCom() {
        return dateRCom;
    }

    public Route dateRCom(LocalDate dateRCom) {
        this.dateRCom = dateRCom;
        return this;
    }

    public void setDateRCom(LocalDate dateRCom) {
        this.dateRCom = dateRCom;
    }

    public String getHeureDepCom() {
        return heureDepCom;
    }

    public Route heureDepCom(String heureDepCom) {
        this.heureDepCom = heureDepCom;
        return this;
    }

    public void setHeureDepCom(String heureDepCom) {
        this.heureDepCom = heureDepCom;
    }

    public String getHeureFinCom() {
        return heureFinCom;
    }

    public Route heureFinCom(String heureFinCom) {
        this.heureFinCom = heureFinCom;
        return this;
    }

    public void setHeureFinCom(String heureFinCom) {
        this.heureFinCom = heureFinCom;
    }

    public String getHeureDepLiv() {
        return heureDepLiv;
    }

    public Route heureDepLiv(String heureDepLiv) {
        this.heureDepLiv = heureDepLiv;
        return this;
    }

    public void setHeureDepLiv(String heureDepLiv) {
        this.heureDepLiv = heureDepLiv;
    }

    public String getHeureFinLiv() {
        return heureFinLiv;
    }

    public Route heureFinLiv(String heureFinLiv) {
        this.heureFinLiv = heureFinLiv;
        return this;
    }

    public void setHeureFinLiv(String heureFinLiv) {
        this.heureFinLiv = heureFinLiv;
    }

    public String getJourneeLiv() {
        return journeeLiv;
    }

    public Route journeeLiv(String journeeLiv) {
        this.journeeLiv = journeeLiv;
        return this;
    }

    public void setJourneeLiv(String journeeLiv) {
        this.journeeLiv = journeeLiv;
    }

    public LocalDate getDatedepLiv() {
        return datedepLiv;
    }

    public Route datedepLiv(LocalDate datedepLiv) {
        this.datedepLiv = datedepLiv;
        return this;
    }

    public void setDatedepLiv(LocalDate datedepLiv) {
        this.datedepLiv = datedepLiv;
    }

    public LocalDate getDateRLiv() {
        return dateRLiv;
    }

    public Route dateRLiv(LocalDate dateRLiv) {
        this.dateRLiv = dateRLiv;
        return this;
    }

    public void setDateRLiv(LocalDate dateRLiv) {
        this.dateRLiv = dateRLiv;
    }

    public String getEtat() {
        return etat;
    }

    public Route etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Set<DetailRoute> getDetailRoutes() {
        return detailRoutes;
    }

    public Route detailRoutes(Set<DetailRoute> detailRoutes) {
        this.detailRoutes = detailRoutes;
        return this;
    }

    public Route addDetailRoute(DetailRoute detailRoute) {
        this.detailRoutes.add(detailRoute);
        detailRoute.setRoute(this);
        return this;
    }

    public Route removeDetailRoute(DetailRoute detailRoute) {
        this.detailRoutes.remove(detailRoute);
        detailRoute.setRoute(null);
        return this;
    }

    public void setDetailRoutes(Set<DetailRoute> detailRoutes) {
        this.detailRoutes = detailRoutes;
    }

    public MoyenTransport getMoyenTransport() {
        return moyenTransport;
    }

    public Route moyenTransport(MoyenTransport moyenTransport) {
        this.moyenTransport = moyenTransport;
        return this;
    }

    public void setMoyenTransport(MoyenTransport moyenTransport) {
        this.moyenTransport = moyenTransport;
    }

    public TypeRoute getCommande() {
        return commande;
    }

    public Route commande(TypeRoute typeRoute) {
        this.commande = typeRoute;
        return this;
    }

    public void setCommande(TypeRoute typeRoute) {
        this.commande = typeRoute;
    }

    public TypeRoute getLivraison() {
        return livraison;
    }

    public Route livraison(TypeRoute typeRoute) {
        this.livraison = typeRoute;
        return this;
    }

    public void setLivraison(TypeRoute typeRoute) {
        this.livraison = typeRoute;
    }

    public User getGerantCommande() {
        return gerantCommande;
    }

    public Route gerantCommande(User user) {
        this.gerantCommande = user;
        return this;
    }

    public void setGerantCommande(User user) {
        this.gerantCommande = user;
    }

    public Employee getPrevendeur() {
        return prevendeur;
    }

    public Route prevendeur(Employee employee) {
        this.prevendeur = employee;
        return this;
    }

    public void setPrevendeur(Employee employee) {
        this.prevendeur = employee;
    }

    public Employee getGerantlivraison() {
        return gerantlivraison;
    }

    public Route gerantlivraison(Employee employee) {
        this.gerantlivraison = employee;
        return this;
    }

    public void setGerantlivraison(Employee employee) {
        this.gerantlivraison = employee;
    }

    public Employee getLivreur() {
        return livreur;
    }

    public Route livreur(Employee employee) {
        this.livreur = employee;
        return this;
    }

    public void setLivreur(Employee employee) {
        this.livreur = employee;
    }

    public Set<Secteur> getSecteurs() {
        return secteurs;
    }

    public Route secteurs(Set<Secteur> secteurs) {
        this.secteurs = secteurs;
        return this;
    }

    public Route addSecteur(Secteur secteur) {
        this.secteurs.add(secteur);
        secteur.getRoutes().add(this);
        return this;
    }

    public Route removeSecteur(Secteur secteur) {
        this.secteurs.remove(secteur);
        secteur.getRoutes().remove(this);
        return this;
    }

    public void setSecteurs(Set<Secteur> secteurs) {
        this.secteurs = secteurs;
    }

    public Set<Boutique> getBoutiques() {
        return boutiques;
    }

    public Route boutiques(Set<Boutique> boutiques) {
        this.boutiques = boutiques;
        return this;
    }

    public Route addBoutique(Boutique boutique) {
        this.boutiques.add(boutique);
        boutique.getRoutes().add(this);
        return this;
    }

    public Route removeBoutique(Boutique boutique) {
        this.boutiques.remove(boutique);
        boutique.getRoutes().remove(this);
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
        Route route = (Route) o;
        if (route.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), route.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Route{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", journee='" + getJournee() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", datedepCom='" + getDatedepCom() + "'" +
            ", dateRCom='" + getDateRCom() + "'" +
            ", heureDepCom='" + getHeureDepCom() + "'" +
            ", heureFinCom='" + getHeureFinCom() + "'" +
            ", heureDepLiv='" + getHeureDepLiv() + "'" +
            ", heureFinLiv='" + getHeureFinLiv() + "'" +
            ", journeeLiv='" + getJourneeLiv() + "'" +
            ", datedepLiv='" + getDatedepLiv() + "'" +
            ", dateRLiv='" + getDateRLiv() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
