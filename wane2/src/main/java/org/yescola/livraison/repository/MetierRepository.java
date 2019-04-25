package org.yescola.livraison.repository;

import org.yescola.livraison.domain.Metier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Metier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetierRepository extends JpaRepository<Metier, Long> {

}
