package org.yescola.livraison.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Appro.
 */
@Entity
@Table(name = "appro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "appro")
public class Appro implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qte_liv")
    private String qteLiv;

    @ManyToOne
    @JsonIgnoreProperties("appros")
    private Article article;

    @ManyToOne
    @JsonIgnoreProperties("appros")
    private BonLivraison bonLivraison;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQteLiv() {
        return qteLiv;
    }

    public Appro qteLiv(String qteLiv) {
        this.qteLiv = qteLiv;
        return this;
    }

    public void setQteLiv(String qteLiv) {
        this.qteLiv = qteLiv;
    }

    public Article getArticle() {
        return article;
    }

    public Appro article(Article article) {
        this.article = article;
        return this;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public BonLivraison getBonLivraison() {
        return bonLivraison;
    }

    public Appro bonLivraison(BonLivraison bonLivraison) {
        this.bonLivraison = bonLivraison;
        return this;
    }

    public void setBonLivraison(BonLivraison bonLivraison) {
        this.bonLivraison = bonLivraison;
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
        Appro appro = (Appro) o;
        if (appro.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appro.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Appro{" +
            "id=" + getId() +
            ", qteLiv='" + getQteLiv() + "'" +
            "}";
    }
}
