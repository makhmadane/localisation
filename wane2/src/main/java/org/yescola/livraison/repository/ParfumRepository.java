package org.yescola.livraison.repository;

import org.yescola.livraison.domain.Parfum;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Parfum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParfumRepository extends JpaRepository<Parfum, Long> {

}
