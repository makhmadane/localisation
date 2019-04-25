package org.yescola.livraison.repository;

import org.yescola.livraison.domain.BonLivraison;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BonLivraison entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BonLivraisonRepository extends JpaRepository<BonLivraison, Long> {

}
