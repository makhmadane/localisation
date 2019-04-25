package org.yescola.livraison.repository;

import org.yescola.livraison.domain.Commune;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Commune entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommuneRepository extends JpaRepository<Commune, Long> {

}
