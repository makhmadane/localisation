package org.yescola.livraison.repository;

import org.yescola.livraison.domain.MoyenTransport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MoyenTransport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoyenTransportRepository extends JpaRepository<MoyenTransport, Long> {

}
