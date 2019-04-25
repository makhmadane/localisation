package org.yescola.livraison.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A BonLivraison.
 */
@Entity
@Table(name = "bon_livraison")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bonlivraison")
public class BonLivraison implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_bl")
    private LocalDate dateBl;

    @OneToMany(mappedBy = "bonLivraison")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Appro> appros = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateBl() {
        return dateBl;
    }

    public BonLivraison dateBl(LocalDate dateBl) {
        this.dateBl = dateBl;
        return this;
    }

    public void setDateBl(LocalDate dateBl) {
        this.dateBl = dateBl;
    }

    public Set<Appro> getAppros() {
        return appros;
    }

    public BonLivraison appros(Set<Appro> appros) {
        this.appros = appros;
        return this;
    }

    public BonLivraison addAppro(Appro appro) {
        this.appros.add(appro);
        appro.setBonLivraison(this);
        return this;
    }

    public BonLivraison removeAppro(Appro appro) {
        this.appros.remove(appro);
        appro.setBonLivraison(null);
        return this;
    }

    public void setAppros(Set<Appro> appros) {
        this.appros = appros;
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
        BonLivraison bonLivraison = (BonLivraison) o;
        if (bonLivraison.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bonLivraison.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BonLivraison{" +
            "id=" + getId() +
            ", dateBl='" + getDateBl() + "'" +
            "}";
    }
}
