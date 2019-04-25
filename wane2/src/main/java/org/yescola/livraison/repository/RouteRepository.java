package org.yescola.livraison.repository;

import org.yescola.livraison.domain.Route;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Route entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    @Query("select route from Route route where route.gerantCommande.login = ?#{principal.username}")
    List<Route> findByGerantCommandeIsCurrentUser();

}
