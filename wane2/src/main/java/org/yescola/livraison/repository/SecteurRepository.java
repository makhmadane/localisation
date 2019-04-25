package org.yescola.livraison.repository;

import org.yescola.livraison.domain.Secteur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Secteur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecteurRepository extends JpaRepository<Secteur, Long> {

    @Query(value = "select distinct secteur from Secteur secteur left join fetch secteur.routes left join fetch secteur.communes",
        countQuery = "select count(distinct secteur) from Secteur secteur")
    Page<Secteur> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct secteur from Secteur secteur left join fetch secteur.routes left join fetch secteur.communes")
    List<Secteur> findAllWithEagerRelationships();

    @Query("select secteur from Secteur secteur left join fetch secteur.routes left join fetch secteur.communes where secteur.id =:id")
    Optional<Secteur> findOneWithEagerRelationships(@Param("id") Long id);

}
