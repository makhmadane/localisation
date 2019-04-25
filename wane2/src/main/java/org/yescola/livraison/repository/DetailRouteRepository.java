package org.yescola.livraison.repository;

import org.yescola.livraison.domain.DetailRoute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DetailRoute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailRouteRepository extends JpaRepository<DetailRoute, Long> {

}
