package org.yescola.livraison.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.yescola.livraison.domain.Prospection;
import org.yescola.livraison.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Prospection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProspectionRepository extends JpaRepository<Prospection, Long> {
  //  where prospection.prevendeur.id=tablette.employee.id
   /* @Query("select propection,user from Prospection prospection, User user where prospection.prevendeur.id= user.id ")
    Page<Prospection> gettablette(Pageable pageable);*/

    @Query("select prospection from Prospection prospection where prospection.etat =:name")
    Optional<Prospection> getProspectionByEtat(@Param("name") String name);

    @Query("select prospection from Prospection prospection  where prospection.journee =:name")
    Optional<Prospection> getProspectionByJour(@Param("name") String name);

    @Query("select prospection from Prospection prospection  where prospection.datecreation =:name")
    Optional<Prospection> getProspectionByDate(@Param("name") String name);

   @Query("select prospection from Prospection prospection where prospection.prevendeur.id=:name ")
    Optional<Prospection> getProspectionByPrevendeur(@Param("name") Long name);

    /*@Query("select prospection from Prospection prospection where prospection.boutiques.prenom=:name ")
    Optional<Prospection> getProspectionBySecteur(@Param("name") String name);*/
}
