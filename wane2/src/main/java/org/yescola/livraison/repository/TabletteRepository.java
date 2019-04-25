package org.yescola.livraison.repository;

import org.yescola.livraison.domain.Tablette;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Tablette entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TabletteRepository extends JpaRepository<Tablette, Long> {

}
