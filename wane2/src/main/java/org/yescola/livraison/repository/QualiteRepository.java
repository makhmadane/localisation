package org.yescola.livraison.repository;

import org.yescola.livraison.domain.Qualite;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Qualite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QualiteRepository extends JpaRepository<Qualite, Long> {

}
