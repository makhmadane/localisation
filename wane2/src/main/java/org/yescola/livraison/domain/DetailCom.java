package org.yescola.livraison.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DetailCom.
 */
@Entity
@Table(name = "detail_com")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "detailcom")
public class DetailCom implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qte_com")
    private String qteCom;

    @Column(name = "qte_liv")
    private String qteLiv;

    @ManyToOne
    @JsonIgnoreProperties("detailComs")
    private Article article;

    @ManyToOne
    @JsonIgnoreProperties("detailComs")
    private Commande commande;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQteCom() {
        return qteCom;
    }

    public DetailCom qteCom(String qteCom) {
        this.qteCom = qteCom;
        return this;
    }

    public void setQteCom(String qteCom) {
        this.qteCom = qteCom;
    }

    public String getQteLiv() {
        return qteLiv;
    }

    public DetailCom qteLiv(String qteLiv) {
        this.qteLiv = qteLiv;
        return this;
    }

    public void setQteLiv(String qteLiv) {
        this.qteLiv = qteLiv;
    }

    public Article getArticle() {
        return article;
    }

    public DetailCom article(Article article) {
        this.article = article;
        return this;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Commande getCommande() {
        return commande;
    }

    public DetailCom commande(Commande commande) {
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
        DetailCom detailCom = (DetailCom) o;
        if (detailCom.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detailCom.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetailCom{" +
            "id=" + getId() +
            ", qteCom='" + getQteCom() + "'" +
            ", qteLiv='" + getQteLiv() + "'" +
            "}";
    }
}
