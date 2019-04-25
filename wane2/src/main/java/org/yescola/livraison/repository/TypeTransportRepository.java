package org.yescola.livraison.repository;

import org.yescola.livraison.domain.TypeTransport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeTransport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeTransportRepository extends JpaRepository<TypeTransport, Long> {

}
