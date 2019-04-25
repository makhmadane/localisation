package org.yescola.livraison.repository;

import org.yescola.livraison.domain.DetailCom;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DetailCom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailComRepository extends JpaRepository<DetailCom, Long> {

}
