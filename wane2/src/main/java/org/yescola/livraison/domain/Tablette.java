package org.yescola.livraison.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Tablette.
 */
@Entity
@Table(name = "tablette")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tablette")
public class Tablette implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "date_serv")
    private LocalDate dateServ;

    @Column(name = "etat")
    private String etat;

    @Column(name = "numeropuce")
    private String numeropuce;

    @OneToOne(mappedBy = "tablette")
    @JsonIgnore
    private Employee employee;

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

    public Tablette numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDateServ() {
        return dateServ;
    }

    public Tablette dateServ(LocalDate dateServ) {
        this.dateServ = dateServ;
        return this;
    }

    public void setDateServ(LocalDate dateServ) {
        this.dateServ = dateServ;
    }

    public String getEtat() {
        return etat;
    }

    public Tablette etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getNumeropuce() {
        return numeropuce;
    }

    public Tablette numeropuce(String numeropuce) {
        this.numeropuce = numeropuce;
        return this;
    }

    public void setNumeropuce(String numeropuce) {
        this.numeropuce = numeropuce;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Tablette employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
        Tablette tablette = (Tablette) o;
        if (tablette.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tablette.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tablette{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", dateServ='" + getDateServ() + "'" +
            ", etat='" + getEtat() + "'" +
            ", numeropuce='" + getNumeropuce() + "'" +
            "}";
    }
}
