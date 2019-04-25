package org.yescola.livraison.web.rest;
import org.yescola.livraison.domain.Secteur;
import org.yescola.livraison.repository.SecteurRepository;
import org.yescola.livraison.repository.search.SecteurSearchRepository;
import org.yescola.livraison.web.rest.errors.BadRequestAlertException;
import org.yescola.livraison.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Secteur.
 */
@RestController
@RequestMapping("/api")
public class SecteurResource {

    private final Logger log = LoggerFactory.getLogger(SecteurResource.class);

    private static final String ENTITY_NAME = "secteur";

    private final SecteurRepository secteurRepository;

    private final SecteurSearchRepository secteurSearchRepository;

    public SecteurResource(SecteurRepository secteurRepository, SecteurSearchRepository secteurSearchRepository) {
        this.secteurRepository = secteurRepository;
        this.secteurSearchRepository = secteurSearchRepository;
    }

    /**
     * POST  /secteurs : Create a new secteur.
     *
     * @param secteur the secteur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new secteur, or with status 400 (Bad Request) if the secteur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/secteurs")
    public ResponseEntity<Secteur> createSecteur(@RequestBody Secteur secteur) throws URISyntaxException {
        log.debug("REST request to save Secteur : {}", secteur);
        if (secteur.getId() != null) {
            throw new BadRequestAlertException("A new secteur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Secteur result = secteurRepository.save(secteur);
        secteurSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/secteurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /secteurs : Updates an existing secteur.
     *
     * @param secteur the secteur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated secteur,
     * or with status 400 (Bad Request) if the secteur is not valid,
     * or with status 500 (Internal Server Error) if the secteur couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/secteurs")
    public ResponseEntity<Secteur> updateSecteur(@RequestBody Secteur secteur) throws URISyntaxException {
        log.debug("REST request to update Secteur : {}", secteur);
        if (secteur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Secteur result = secteurRepository.save(secteur);
        secteurSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, secteur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /secteurs : get all the secteurs.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of secteurs in body
     */
    @GetMapping("/secteurs")
    public List<Secteur> getAllSecteurs(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Secteurs");
        return secteurRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /secteurs/:id : get the "id" secteur.
     *
     * @param id the id of the secteur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the secteur, or with status 404 (Not Found)
     */
    @GetMapping("/secteurs/{id}")
    public ResponseEntity<Secteur> getSecteur(@PathVariable Long id) {
        log.debug("REST request to get Secteur : {}", id);
        Optional<Secteur> secteur = secteurRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(secteur);
    }

    /**
     * DELETE  /secteurs/:id : delete the "id" secteur.
     *
     * @param id the id of the secteur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/secteurs/{id}")
    public ResponseEntity<Void> deleteSecteur(@PathVariable Long id) {
        log.debug("REST request to delete Secteur : {}", id);
        secteurRepository.deleteById(id);
        secteurSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/secteurs?query=:query : search for the secteur corresponding
     * to the query.
     *
     * @param query the query of the secteur search
     * @return the result of the search
     */
    @GetMapping("/_search/secteurs")
    public List<Secteur> searchSecteurs(@RequestParam String query) {
        log.debug("REST request to search Secteurs for query {}", query);
        return StreamSupport
            .stream(secteurSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
