package org.yescola.livraison.repository;

import org.yescola.livraison.domain.TypeRoute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeRoute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeRouteRepository extends JpaRepository<TypeRoute, Long> {

}
