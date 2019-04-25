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
 * A Prospection.
 */
@Entity
@Table(name = "prospection")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prospection")
public class Prospection implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "datedepart")
    private LocalDate datedepart;

    @Column(name = "datecreation")
    private LocalDate datecreation;

    @Column(name = "heuredepart")
    private String heuredepart;

    @Column(name = "heurearrive")
    private String heurearrive;

    @Column(name = "journee")
    private String journee;

    @Column(name = "nbreatteint")
    private String nbreatteint;

    @Column(name = "nbrprevue")
    private String nbrprevue;

    @Column(name = "etat")
    private String etat;

    @OneToMany(mappedBy = "prospection")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Boutique> boutiques = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("prospections")
    private Employee prevendeur;

    @ManyToOne
    @JsonIgnoreProperties("prospections")
    private Employee gerant;

    @ManyToOne
    @JsonIgnoreProperties("prospections")
    private MoyenTransport moyenTransport;

    @ManyToOne
    @JsonIgnoreProperties("prospections")
    private Commande commande;

    @OneToMany(mappedBy = "prospection")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Commande> commandes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatedepart() {
        return datedepart;
    }

    public Prospection datedepart(LocalDate datedepart) {
        this.datedepart = datedepart;
        return this;
    }

    public void setDatedepart(LocalDate datedepart) {
        this.datedepart = datedepart;
    }

    public LocalDate getDatecreation() {
        return datecreation;
    }

    public Prospection datecreation(LocalDate datecreation) {
        this.datecreation = datecreation;
        return this;
    }

    public void setDatecreation(LocalDate datecreation) {
        this.datecreation = datecreation;
    }

    public String getHeuredepart() {
        return heuredepart;
    }

    public Prospection heuredepart(String heuredepart) {
        this.heuredepart = heuredepart;
        return this;
    }

    public void setHeuredepart(String heuredepart) {
        this.heuredepart = heuredepart;
    }

    public String getHeurearrive() {
        return heurearrive;
    }

    public Prospection heurearrive(String heurearrive) {
        this.heurearrive = heurearrive;
        return this;
    }

    public void setHeurearrive(String heurearrive) {
        this.heurearrive = heurearrive;
    }

    public String getJournee() {
        return journee;
    }

    public Prospection journee(String journee) {
        this.journee = journee;
        return this;
    }

    public void setJournee(String journee) {
        this.journee = journee;
    }

    public String getNbreatteint() {
        return nbreatteint;
    }

    public Prospection nbreatteint(String nbreatteint) {
        this.nbreatteint = nbreatteint;
        return this;
    }

    public void setNbreatteint(String nbreatteint) {
        this.nbreatteint = nbreatteint;
    }

    public String getNbrprevue() {
        return nbrprevue;
    }

    public Prospection nbrprevue(String nbrprevue) {
        this.nbrprevue = nbrprevue;
        return this;
    }

    public void setNbrprevue(String nbrprevue) {
        this.nbrprevue = nbrprevue;
    }

    public String getEtat() {
        return etat;
    }

    public Prospection etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Set<Boutique> getBoutiques() {
        return boutiques;
    }

    public Prospection boutiques(Set<Boutique> boutiques) {
        this.boutiques = boutiques;
        return this;
    }

    public Prospection addBoutique(Boutique boutique) {
        this.boutiques.add(boutique);
        boutique.setProspection(this);
        return this;
    }

    public Prospection removeBoutique(Boutique boutique) {
        this.boutiques.remove(boutique);
        boutique.setProspection(null);
        return this;
    }

    public void setBoutiques(Set<Boutique> boutiques) {
        this.boutiques = boutiques;
    }

    public Employee getPrevendeur() {
        return prevendeur;
    }

    public Prospection prevendeur(Employee employee) {
        this.prevendeur = employee;
        return this;
    }

    public void setPrevendeur(Employee employee) {
        this.prevendeur = employee;
    }

    public Employee getGerant() {
        return gerant;
    }

    public Prospection gerant(Employee employee) {
        this.gerant = employee;
        return this;
    }

    public void setGerant(Employee employee) {
        this.gerant = employee;
    }

    public MoyenTransport getMoyenTransport() {
        return moyenTransport;
    }

    public Prospection moyenTransport(MoyenTransport moyenTransport) {
        this.moyenTransport = moyenTransport;
        return this;
    }

    public void setMoyenTransport(MoyenTransport moyenTransport) {
        this.moyenTransport = moyenTransport;
    }

    public Commande getCommande() {
        return commande;
    }

    public Prospection commande(Commande commande) {
        this.commande = commande;
        return this;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Set<Commande> getCommandes() {
        return commandes;
    }

    public Prospection commandes(Set<Commande> commandes) {
        this.commandes = commandes;
        return this;
    }

    public Prospection addCommande(Commande commande) {
        this.commandes.add(commande);
        commande.setProspection(this);
        return this;
    }

    public Prospection removeCommande(Commande commande) {
        this.commandes.remove(commande);
        commande.setProspection(null);
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
        Prospection prospection = (Prospection) o;
        if (prospection.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prospection.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Prospection{" +
            "id=" + getId() +
            ", datedepart='" + getDatedepart() + "'" +
            ", datecreation='" + getDatecreation() + "'" +
            ", heuredepart='" + getHeuredepart() + "'" +
            ", heurearrive='" + getHeurearrive() + "'" +
            ", journee='" + getJournee() + "'" +
            ", nbreatteint='" + getNbreatteint() + "'" +
            ", nbrprevue='" + getNbrprevue() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
