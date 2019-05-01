package org.yescola.livraison.repository;
import org.yescola.livraison.domain.Route;
import org.yescola.livraison.domain.Boutique;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yescola.livraison.domain.Boutique_route;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Boutique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoutiqueRepository extends JpaRepository<Boutique, Long> {

    @Query(value = "select distinct boutique from Boutique boutique left join fetch boutique.routes",
        countQuery = "select count(distinct boutique) from Boutique boutique")
    Page<Boutique> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct boutique from Boutique boutique left join fetch boutique.routes")
    List<Boutique> findAllWithEagerRelationships();

    @Query(value = "select route from Boutique_route resource,Route route where resource.routeId=route.id")
    List<Route> findroute();

    @Query("select boutique from Boutique boutique left join fetch boutique.routes where boutique.id =:id")
    Optional<Boutique> findOneWithEagerRelationships(@Param("id") Long id);

}
