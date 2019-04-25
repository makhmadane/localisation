package org.yescola.livraison.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Reglement.
 */
@Entity
@Table(name = "reglement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reglement")
public class Reglement implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_reg")
    private LocalDate dateReg;

    @Column(name = "montant_payer")
    private String montantPayer;

    @ManyToOne
    @JsonIgnoreProperties("reglements")
    private TypeRglment typeRglment;

    @ManyToOne
    @JsonIgnoreProperties("reglements")
    private Commande commande;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateReg() {
        return dateReg;
    }

    public Reglement dateReg(LocalDate dateReg) {
        this.dateReg = dateReg;
        return this;
    }

    public void setDateReg(LocalDate dateReg) {
        this.dateReg = dateReg;
    }

    public String getMontantPayer() {
        return montantPayer;
    }

    public Reglement montantPayer(String montantPayer) {
        this.montantPayer = montantPayer;
        return this;
    }

    public void setMontantPayer(String montantPayer) {
        this.montantPayer = montantPayer;
    }

    public TypeRglment getTypeRglment() {
        return typeRglment;
    }

    public Reglement typeRglment(TypeRglment typeRglment) {
        this.typeRglment = typeRglment;
        return this;
    }

    public void setTypeRglment(TypeRglment typeRglment) {
        this.typeRglment = typeRglment;
    }

    public Commande getCommande() {
        return commande;
    }

    public Reglement commande(Commande commande) {
        this.commande = commande;
        return this;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
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
        Reglement reglement = (Reglement) o;
        if (reglement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reglement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reglement{" +
            "id=" + getId() +
            ", dateReg='" + getDateReg() + "'" +
            ", montantPayer='" + getMontantPayer() + "'" +
            "}";
    }
}
