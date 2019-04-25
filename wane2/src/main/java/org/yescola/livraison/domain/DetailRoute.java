package org.yescola.livraison.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DetailRoute.
 */
@Entity
@Table(name = "detail_route")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "detailroute")
public class DetailRoute implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "heure_a_com")
    private String heureACom;

    @Column(name = "heure_f_com")
    private String heureFCom;

    @Column(name = "heure_a_liv")
    private String heureALiv;

    @Column(name = "heure_f_liv")
    private String heureFLiv;

    @Column(name = "distance_depot")
    private String distanceDepot;

    @OneToOne(mappedBy = "detailRoute")
    @JsonIgnore
    private Commande commande;

    @ManyToOne
    @JsonIgnoreProperties("detailRoutes")
    private Route route;

    @ManyToOne
    @JsonIgnoreProperties("detailRoutes")
    private Boutique boutique;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeureACom() {
        return heureACom;
    }

    public DetailRoute heureACom(String heureACom) {
        this.heureACom = heureACom;
        return this;
    }

    public void setHeureACom(String heureACom) {
        this.heureACom = heureACom;
    }

    public String getHeureFCom() {
        return heureFCom;
    }

    public DetailRoute heureFCom(String heureFCom) {
        this.heureFCom = heureFCom;
        return this;
    }

    public void setHeureFCom(String heureFCom) {
        this.heureFCom = heureFCom;
    }

    public String getHeureALiv() {
        return heureALiv;
    }

    public DetailRoute heureALiv(String heureALiv) {
        this.heureALiv = heureALiv;
        return this;
    }

    public void setHeureALiv(String heureALiv) {
        this.heureALiv = heureALiv;
    }

    public String getHeureFLiv() {
        return heureFLiv;
    }

    public DetailRoute heureFLiv(String heureFLiv) {
        this.heureFLiv = heureFLiv;
        return this;
    }

    public void setHeureFLiv(String heureFLiv) {
        this.heureFLiv = heureFLiv;
    }

    public String getDistanceDepot() {
        return distanceDepot;
    }

    public DetailRoute distanceDepot(String distanceDepot) {
        this.distanceDepot = distanceDepot;
        return this;
    }

    public void setDistanceDepot(String distanceDepot) {
        this.distanceDepot = distanceDepot;
    }

    public Commande getCommande() {
        return commande;
    }

    public DetailRoute commande(Commande commande) {
        this.commande = commande;
        return this;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Route getRoute() {
        return route;
    }

    public DetailRoute route(Route route) {
        this.route = route;
        return this;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public DetailRoute boutique(Boutique boutique) {
        this.boutique = boutique;
        return this;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
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
        DetailRoute detailRoute = (DetailRoute) o;
        if (detailRoute.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detailRoute.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetailRoute{" +
            "id=" + getId() +
            ", heureACom='" + getHeureACom() + "'" +
            ", heureFCom='" + getHeureFCom() + "'" +
            ", heureALiv='" + getHeureALiv() + "'" +
            ", heureFLiv='" + getHeureFLiv() + "'" +
            ", distanceDepot='" + getDistanceDepot() + "'" +
            "}";
    }
}
