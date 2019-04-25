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
 * A Parfum.
 */
@Entity
@Table(name = "parfum")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "parfum")
public class Parfum implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numeroparf")
    private String numeroparf;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "etat")
    private String etat;

    @OneToMany(mappedBy = "parfum")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Article> articles = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroparf() {
        return numeroparf;
    }

    public Parfum numeroparf(String numeroparf) {
        this.numeroparf = numeroparf;
        return this;
    }

    public void setNumeroparf(String numeroparf) {
        this.numeroparf = numeroparf;
    }

    public String getLibelle() {
        return libelle;
    }

    public Parfum libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getEtat() {
        return etat;
    }

    public Parfum etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public Parfum articles(Set<Article> articles) {
        this.articles = articles;
        return this;
    }

    public Parfum addArticle(Article article) {
        this.articles.add(article);
        article.setParfum(this);
        return this;
    }

    public Parfum removeArticle(Article article) {
        this.articles.remove(article);
        article.setParfum(null);
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
        Parfum parfum = (Parfum) o;
        if (parfum.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), parfum.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Parfum{" +
            "id=" + getId() +
            ", numeroparf='" + getNumeroparf() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
