package org.yescola.livraison.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomarticle")
    private String nomarticle;

    @Column(name = "numeroarticle")
    private String numeroarticle;

    @Column(name = "qte_stock")
    private String qteStock;

    @Column(name = "qte_seuil")
    private String qteSeuil;

    @Column(name = "pack")
    private String pack;

    @Column(name = "etat")
    private String etat;

    @Column(name = "prix")
    private String prix;

    @OneToMany(mappedBy = "article")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StockInitial> stockInitials = new HashSet<>();
    @OneToMany(mappedBy = "article")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DetailCom> detailComs = new HashSet<>();
    @OneToMany(mappedBy = "article")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Appro> appros = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("articles")
    private Parfum parfum;

    @ManyToOne
    @JsonIgnoreProperties("articles")
    private Depot depot;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomarticle() {
        return nomarticle;
    }

    public Article nomarticle(String nomarticle) {
        this.nomarticle = nomarticle;
        return this;
    }

    public void setNomarticle(String nomarticle) {
        this.nomarticle = nomarticle;
    }

    public String getNumeroarticle() {
        return numeroarticle;
    }

    public Article numeroarticle(String numeroarticle) {
        this.numeroarticle = numeroarticle;
        return this;
    }

    public void setNumeroarticle(String numeroarticle) {
        this.numeroarticle = numeroarticle;
    }

    public String getQteStock() {
        return qteStock;
    }

    public Article qteStock(String qteStock) {
        this.qteStock = qteStock;
        return this;
    }

    public void setQteStock(String qteStock) {
        this.qteStock = qteStock;
    }

    public String getQteSeuil() {
        return qteSeuil;
    }

    public Article qteSeuil(String qteSeuil) {
        this.qteSeuil = qteSeuil;
        return this;
    }

    public void setQteSeuil(String qteSeuil) {
        this.qteSeuil = qteSeuil;
    }

    public String getPack() {
        return pack;
    }

    public Article pack(String pack) {
        this.pack = pack;
        return this;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getEtat() {
        return etat;
    }

    public Article etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getPrix() {
        return prix;
    }

    public Article prix(String prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public Set<StockInitial> getStockInitials() {
        return stockInitials;
    }

    public Article stockInitials(Set<StockInitial> stockInitials) {
        this.stockInitials = stockInitials;
        return this;
    }

    public Article addStockInitial(StockInitial stockInitial) {
        this.stockInitials.add(stockInitial);
        stockInitial.setArticle(this);
        return this;
    }

    public Article removeStockInitial(StockInitial stockInitial) {
        this.stockInitials.remove(stockInitial);
        stockInitial.setArticle(null);
        return this;
    }

    public void setStockInitials(Set<StockInitial> stockInitials) {
        this.stockInitials = stockInitials;
    }

    public Set<DetailCom> getDetailComs() {
        return detailComs;
    }

    public Article detailComs(Set<DetailCom> detailComs) {
        this.detailComs = detailComs;
        return this;
    }

    public Article addDetailCom(DetailCom detailCom) {
        this.detailComs.add(detailCom);
        detailCom.setArticle(this);
        return this;
    }

    public Article removeDetailCom(DetailCom detailCom) {
        this.detailComs.remove(detailCom);
        detailCom.setArticle(null);
        return this;
    }

    public void setDetailComs(Set<DetailCom> detailComs) {
        this.detailComs = detailComs;
    }

    public Set<Appro> getAppros() {
        return appros;
    }

    public Article appros(Set<Appro> appros) {
        this.appros = appros;
        return this;
    }

    public Article addAppro(Appro appro) {
        this.appros.add(appro);
        appro.setArticle(this);
        return this;
    }

    public Article removeAppro(Appro appro) {
        this.appros.remove(appro);
        appro.setArticle(null);
        return this;
    }

    public void setAppros(Set<Appro> appros) {
        this.appros = appros;
    }

    public Parfum getParfum() {
        return parfum;
    }

    public Article parfum(Parfum parfum) {
        this.parfum = parfum;
        return this;
    }

    public void setParfum(Parfum parfum) {
        this.parfum = parfum;
    }

    public Depot getDepot() {
        return depot;
    }

    public Article depot(Depot depot) {
        this.depot = depot;
        return this;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
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
        Article article = (Article) o;
        if (article.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), article.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", nomarticle='" + getNomarticle() + "'" +
            ", numeroarticle='" + getNumeroarticle() + "'" +
            ", qteStock='" + getQteStock() + "'" +
            ", qteSeuil='" + getQteSeuil() + "'" +
            ", pack='" + getPack() + "'" +
            ", etat='" + getEtat() + "'" +
            ", prix='" + getPrix() + "'" +
            "}";
    }
}
