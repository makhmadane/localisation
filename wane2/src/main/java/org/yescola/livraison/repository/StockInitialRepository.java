package org.yescola.livraison.repository;

import org.yescola.livraison.domain.StockInitial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StockInitial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockInitialRepository extends JpaRepository<StockInitial, Long> {

}
