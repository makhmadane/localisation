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
 * A Depot.
 */
@Entity
@Table(name = "depot")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "depot")
public class Depot implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "altitude")
    private String altitude;

    @Column(name = "etat")
    private String etat;

    @OneToMany(mappedBy = "depot")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Article> articles = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public Depot adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public Depot telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public Depot email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLongitude() {
        return longitude;
    }

    public Depot longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public Depot altitude(String altitude) {
        this.altitude = altitude;
        return this;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getEtat() {
        return etat;
    }

    public Depot etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public Depot articles(Set<Article> articles) {
        this.articles = articles;
        return this;
    }

    public Depot addArticle(Article article) {
        this.articles.add(article);
        article.setDepot(this);
        return this;
    }

    public Depot removeArticle(Article article) {
        this.articles.remove(article);
        article.setDepot(null);
        return this;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
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
        Depot depot = (Depot) o;
        if (depot.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), depot.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Depot{" +
            "id=" + getId() +
            ", adresse='" + getAdresse() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", altitude='" + getAltitude() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
