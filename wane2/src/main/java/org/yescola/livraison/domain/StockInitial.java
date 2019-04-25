package org.yescola.livraison.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A StockInitial.
 */
@Entity
@Table(name = "stock_initial")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "stockinitial")
public class StockInitial implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qte_stock_init")
    private String qteStockInit;

    @Column(name = "qte_stock_encours")
    private String qteStockEncours;

    @ManyToOne
    @JsonIgnoreProperties("stockInitials")
    private Boutique boutique;

    @ManyToOne
    @JsonIgnoreProperties("stockInitials")
    private Article article;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQteStockInit() {
        return qteStockInit;
    }

    public StockInitial qteStockInit(String qteStockInit) {
        this.qteStockInit = qteStockInit;
        return this;
    }

    public void setQteStockInit(String qteStockInit) {
        this.qteStockInit = qteStockInit;
    }

    public String getQteStockEncours() {
        return qteStockEncours;
    }

    public StockInitial qteStockEncours(String qteStockEncours) {
        this.qteStockEncours = qteStockEncours;
        return this;
    }

    public void setQteStockEncours(String qteStockEncours) {
        this.qteStockEncours = qteStockEncours;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public StockInitial boutique(Boutique boutique) {
        this.boutique = boutique;
        return this;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public Article getArticle() {
        return article;
    }

    public StockInitial article(Article article) {
        this.article = article;
        return this;
    }

    public void setArticle(Article article) {
        this.article = article;
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
        StockInitial stockInitial = (StockInitial) o;
        if (stockInitial.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockInitial.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockInitial{" +
            "id=" + getId() +
            ", qteStockInit='" + getQteStockInit() + "'" +
            ", qteStockEncours='" + getQteStockEncours() + "'" +
            "}";
    }
}
