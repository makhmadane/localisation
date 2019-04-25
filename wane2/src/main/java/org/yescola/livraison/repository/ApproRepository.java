package org.yescola.livraison.repository;

import org.yescola.livraison.domain.Appro;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Appro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApproRepository extends JpaRepository<Appro, Long> {

}
