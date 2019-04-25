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
 * A TypeTransport.
 */
@Entity
@Table(name = "type_transport")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "typetransport")
public class TypeTransport implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @OneToMany(mappedBy = "typeTransport")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MoyenTransport> moyenTransports = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public TypeTransport libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<MoyenTransport> getMoyenTransports() {
        return moyenTransports;
    }

    public TypeTransport moyenTransports(Set<MoyenTransport> moyenTransports) {
        this.moyenTransports = moyenTransports;
        return this;
    }

    public TypeTransport addMoyenTransport(MoyenTransport moyenTransport) {
        this.moyenTransports.add(moyenTransport);
        moyenTransport.setTypeTransport(this);
        return this;
    }

    public TypeTransport removeMoyenTransport(MoyenTransport moyenTransport) {
        this.moyenTransports.remove(moyenTransport);
        moyenTransport.setTypeTransport(null);
        return this;
    }

    public void setMoyenTransports(Set<MoyenTransport> moyenTransports) {
        this.moyenTransports = moyenTransports;
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
        TypeTransport typeTransport = (TypeTransport) o;
        if (typeTransport.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeTransport.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeTransport{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
