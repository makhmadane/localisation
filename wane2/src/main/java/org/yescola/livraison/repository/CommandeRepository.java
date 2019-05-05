package org.yescola.livraison.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.yescola.livraison.domain.Commande;
import org.yescola.livraison.domain.DetailCom;
import org.yescola.livraison.domain.Article;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Commande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    @Query("select article.id,article.nomarticle,detail.qteLiv,detail.qteCom from Article article,DetailCom detail where detail.article.id = article.id and detail.commande.id = :id ")
    Optional<Object[]> findarticle(@Param("id") Long id);

    @Query("select  distinct commande.etat from Commande commande  ")
    List<String> findAllEtat();

    @Query("select  distinct commande.boutique.nomBoutique  from Commande commande ")
    List<String> findAllBoutique();

    @Query("select  distinct commande.prevendeur.nomcomplet from Commande commande ")
    List<String> findAllPrevendeur();

    @Query("select  distinct commande.secteur.nomSecteur from Commande commande ")
    List<String> findAllSecteur();

    @Query("select  distinct commande.dateCom from Commande commande ")
    List<String> findAllDate();

    @Transactional
    @Modifying
    @Query("update Commande commande set commande.etat= :nom where commande.id= :id")
    void ChangeEtat(@Param("id") Long id,@Param("nom") String nom);
}
