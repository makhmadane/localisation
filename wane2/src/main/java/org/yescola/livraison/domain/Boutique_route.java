
package org.yescola.livraison.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "boutique_route")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "boutique_route")
public class Boutique_route implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "route_id",nullable = false)
    private Long routeId;

    @Id
    @Column(name = "boutique_id",nullable = false)
    private Long boutiqueId;


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getRouteId() {

        return  this.routeId;
    }
    public Long getBoutiqueId() {

        return  this.boutiqueId;
    }
    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public void setBoutiqueId(Long boutiqueId) {
        this.boutiqueId = boutiqueId;
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
        Boutique_route boutique_route = (Boutique_route) o;
        if (boutique_route.getBoutiqueId()== null || getBoutiqueId() == null) {
            return false;
        }
        return Objects.equals(getBoutiqueId(), boutique_route.getBoutiqueId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getBoutiqueId());
    }

    @Override
    public String toString() {
        return "Article{" +
            "boutiqueId=" + getBoutiqueId() +
            ", routeId='" + getRouteId() + "'" +
            "}";
    }
}
