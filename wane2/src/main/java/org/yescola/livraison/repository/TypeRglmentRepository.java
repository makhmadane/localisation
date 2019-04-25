package org.yescola.livraison.repository;

import org.yescola.livraison.domain.TypeRglment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeRglment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeRglmentRepository extends JpaRepository<TypeRglment, Long> {

}
