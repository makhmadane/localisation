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
 * A MoyenTransport.
 */
@Entity
@Table(name = "moyen_transport")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "moyentransport")
public class MoyenTransport implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matricule")
    private String matricule;

    @Column(name = "date_list")
    private LocalDate dateList;

    @Column(name = "etat")
    private String etat;

    @Column(name = "vitesse")
    private String vitesse;

    @Column(name = "capacite")
    private String capacite;

    @ManyToOne
    @JsonIgnoreProperties("moyenTransports")
    private TypeTransport typeTransport;

    @OneToMany(mappedBy = "moyenTransport")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Route> routes = new HashSet<>();
    @OneToMany(mappedBy = "moyenTransport")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Prospection> prospections = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public MoyenTransport matricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public LocalDate getDateList() {
        return dateList;
    }

    public MoyenTransport dateList(LocalDate dateList) {
        this.dateList = dateList;
        return this;
    }

    public void setDateList(LocalDate dateList) {
        this.dateList = dateList;
    }

    public String getEtat() {
        return etat;
    }

    public MoyenTransport etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getVitesse() {
        return vitesse;
    }

    public MoyenTransport vitesse(String vitesse) {
        this.vitesse = vitesse;
        return this;
    }

    public void setVitesse(String vitesse) {
        this.vitesse = vitesse;
    }

    public String getCapacite() {
        return capacite;
    }

    public MoyenTransport capacite(String capacite) {
        this.capacite = capacite;
        return this;
    }

    public void setCapacite(String capacite) {
        this.capacite = capacite;
    }

    public TypeTransport getTypeTransport() {
        return typeTransport;
    }

    public MoyenTransport typeTransport(TypeTransport typeTransport) {
        this.typeTransport = typeTransport;
        return this;
    }

    public void setTypeTransport(TypeTransport typeTransport) {
        this.typeTransport = typeTransport;
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public MoyenTransport routes(Set<Route> routes) {
        this.routes = routes;
        return this;
    }

    public MoyenTransport addRoute(Route route) {
        this.routes.add(route);
        route.setMoyenTransport(this);
        return this;
    }

    public MoyenTransport removeRoute(Route route) {
        this.routes.remove(route);
        route.setMoyenTransport(null);
        return this;
    }

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
    }

    public Set<Prospection> getProspections() {
        return prospections;
    }

    public MoyenTransport prospections(Set<Prospection> prospections) {
        this.prospections = prospections;
        return this;
    }

    public MoyenTransport addProspection(Prospection prospection) {
        this.prospections.add(prospection);
        prospection.setMoyenTransport(this);
        return this;
    }

    public MoyenTransport removeProspection(Prospection prospection) {
        this.prospections.remove(prospection);
        prospection.setMoyenTransport(null);
        return this;
    }

    public void setProspections(Set<Prospection> prospections) {
        this.prospections = prospections;
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
        MoyenTransport moyenTransport = (MoyenTransport) o;
        if (moyenTransport.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), moyenTransport.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MoyenTransport{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", dateList='" + getDateList() + "'" +
            ", etat='" + getEtat() + "'" +
            ", vitesse='" + getVitesse() + "'" +
            ", capacite='" + getCapacite() + "'" +
            "}";
    }
}
