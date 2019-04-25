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
 * A Commande.
 */
@Entity
@Table(name = "commande")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "commande")
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "num_com")
    private String numCom;

    @Column(name = "date_com")
    private LocalDate dateCom;

    @Column(name = "etat")
    private String etat;

    @Column(name = "montant_liv")
    private String montantLiv;

    @Column(name = "montant_rest")
    private String montantRest;

    @OneToOne
    @JoinColumn(unique = true)
    private Boutique boutique;

    @OneToOne
    @JoinColumn(unique = true)
    private DetailRoute detailRoute;

    @OneToMany(mappedBy = "commande")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DetailCom> detailComs = new HashSet<>();
    @OneToMany(mappedBy = "commande")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Prospection> prospections = new HashSet<>();
    @OneToMany(mappedBy = "commande")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reglement> reglements = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("commandes")
    private Secteur secteur;

    @ManyToOne
    @JsonIgnoreProperties("commandes")
    private Employee livreur;

    @ManyToOne
    @JsonIgnoreProperties("commandes")
    private Employee prevendeur;

    @ManyToOne
    @JsonIgnoreProperties("commandes")
    private Prospection prospection;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumCom() {
        return numCom;
    }

    public Commande numCom(String numCom) {
        this.numCom = numCom;
        return this;
    }

    public void setNumCom(String numCom) {
        this.numCom = numCom;
    }

    public LocalDate getDateCom() {
        return dateCom;
    }

    public Commande dateCom(LocalDate dateCom) {
        this.dateCom = dateCom;
        return this;
    }

    public void setDateCom(LocalDate dateCom) {
        this.dateCom = dateCom;
    }

    public String getEtat() {
        return etat;
    }

    public Commande etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getMontantLiv() {
        return montantLiv;
    }

    public Commande montantLiv(String montantLiv) {
        this.montantLiv = montantLiv;
        return this;
    }

    public void setMontantLiv(String montantLiv) {
        this.montantLiv = montantLiv;
    }

    public String getMontantRest() {
        return montantRest;
    }

    public Commande montantRest(String montantRest) {
        this.montantRest = montantRest;
        return this;
    }

    public void setMontantRest(String montantRest) {
        this.montantRest = montantRest;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public Commande boutique(Boutique boutique) {
        this.boutique = boutique;
        return this;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public DetailRoute getDetailRoute() {
        return detailRoute;
    }

    public Commande detailRoute(DetailRoute detailRoute) {
        this.detailRoute = detailRoute;
        return this;
    }

    public void setDetailRoute(DetailRoute detailRoute) {
        this.detailRoute = detailRoute;
    }

    public Set<DetailCom> getDetailComs() {
        return detailComs;
    }

    public Commande detailComs(Set<DetailCom> detailComs) {
        this.detailComs = detailComs;
        return this;
    }

    public Commande addDetailCom(DetailCom detailCom) {
        this.detailComs.add(detailCom);
        detailCom.setCommande(this);
        return this;
    }

    public Commande removeDetailCom(DetailCom detailCom) {
        this.detailComs.remove(detailCom);
        detailCom.setCommande(null);
        return this;
    }

    public void setDetailComs(Set<DetailCom> detailComs) {
        this.detailComs = detailComs;
    }

    public Set<Prospection> getProspections() {
        return prospections;
    }

    public Commande prospections(Set<Prospection> prospections) {
        this.prospections = prospections;
        return this;
    }

    public Commande addProspection(Prospection prospection) {
        this.prospections.add(prospection);
        prospection.setCommande(this);
        return this;
    }

    public Commande removeProspection(Prospection prospection) {
        this.prospections.remove(prospection);
        prospection.setCommande(null);
        return this;
    }

    public void setProspections(Set<Prospection> prospections) {
        this.prospections = prospections;
    }

    public Set<Reglement> getReglements() {
        return reglements;
    }

    public Commande reglements(Set<Reglement> reglements) {
        this.reglements = reglements;
        return this;
    }

    public Commande addReglement(Reglement reglement) {
        this.reglements.add(reglement);
        reglement.setCommande(this);
        return this;
    }

    public Commande removeReglement(Reglement reglement) {
        this.reglements.remove(reglement);
        reglement.setCommande(null);
        return this;
    }

    public void setReglements(Set<Reglement> reglements) {
        this.reglements = reglements;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public Commande secteur(Secteur secteur) {
        this.secteur = secteur;
        return this;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public Employee getLivreur() {
        return livreur;
    }

    public Commande livreur(Employee employee) {
        this.livreur = employee;
        return this;
    }

    public void setLivreur(Employee employee) {
        this.livreur = employee;
    }

    public Employee getPrevendeur() {
        return prevendeur;
    }

    public Commande prevendeur(Employee employee) {
        this.prevendeur = employee;
        return this;
    }

    public void setPrevendeur(Employee employee) {
        this.prevendeur = employee;
    }

    public Prospection getProspection() {
        return prospection;
    }

    public Commande prospection(Prospection prospection) {
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
        Commande commande = (Commande) o;
        if (commande.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commande.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Commande{" +
            "id=" + getId() +
            ", numCom='" + getNumCom() + "'" +
            ", dateCom='" + getDateCom() + "'" +
            ", etat='" + getEtat() + "'" +
            ", montantLiv='" + getMontantLiv() + "'" +
            ", montantRest='" + getMontantRest() + "'" +
            "}";
    }
}
