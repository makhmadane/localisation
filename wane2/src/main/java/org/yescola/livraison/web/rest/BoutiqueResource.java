package org.yescola.livraison.web.rest;
import org.yescola.livraison.domain.Boutique;
import org.yescola.livraison.domain.Boutique_route;
import org.yescola.livraison.domain.Route;
import org.yescola.livraison.repository.BoutiqueRepository;
import org.yescola.livraison.repository.search.BoutiqueSearchRepository;
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
 * REST controller for managing Boutique.
 */
@RestController
@RequestMapping("/api")
public class BoutiqueResource {

    private final Logger log = LoggerFactory.getLogger(BoutiqueResource.class);

    private static final String ENTITY_NAME = "boutique";

    private final BoutiqueRepository boutiqueRepository;

    private final BoutiqueSearchRepository boutiqueSearchRepository;

    public BoutiqueResource(BoutiqueRepository boutiqueRepository, BoutiqueSearchRepository boutiqueSearchRepository) {
        this.boutiqueRepository = boutiqueRepository;
        this.boutiqueSearchRepository = boutiqueSearchRepository;
    }

    /**
     * POST  /boutiques : Create a new boutique.
     *
     * @param boutique the boutique to create
     * @return the ResponseEntity with status 201 (Created) and with body the new boutique, or with status 400 (Bad Request) if the boutique has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/boutiques")
    public ResponseEntity<Boutique> createBoutique(@RequestBody Boutique boutique) throws URISyntaxException {
        log.debug("REST request to save Boutique : {}", boutique);
        if (boutique.getId() != null) {
            throw new BadRequestAlertException("A new boutique cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Boutique result = boutiqueRepository.save(boutique);
        boutiqueSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/boutiques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /boutiques : Updates an existing boutique.
     *
     * @param boutique the boutique to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated boutique,
     * or with status 400 (Bad Request) if the boutique is not valid,
     * or with status 500 (Internal Server Error) if the boutique couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/boutiques")
    public ResponseEntity<Boutique> updateBoutique(@RequestBody Boutique boutique) throws URISyntaxException {
        log.debug("REST request to update Boutique : {}", boutique);
        if (boutique.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Boutique result = boutiqueRepository.save(boutique);
        boutiqueSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, boutique.getId().toString()))
            .body(result);
    }

    /**
     * GET  /boutiques : get all the boutiques.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of boutiques in body
     */
    @GetMapping("/boutiques")
    public List<Boutique> getAllBoutiques(@RequestParam(required = false) String filter,@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        if ("commande-is-null".equals(filter)) {
            log.debug("REST request to get all Boutiques where commande is null");
            return StreamSupport
                .stream(boutiqueRepository.findAll().spliterator(), false)
                .filter(boutique -> boutique.getCommande() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Boutiques");
        return boutiqueRepository.findAllWithEagerRelationships();
    }


    /**
     * GET  /boutiques/:id : get the "id" boutique.
     *
     * @param id the id of the boutique to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the boutique, or with status 404 (Not Found)
     */
    @GetMapping("/boutiques/{id}")
    public ResponseEntity<Boutique> getBoutique(@PathVariable Long id) {
        log.debug("REST request to get Boutique : {}", id);
        Optional<Boutique> boutique = boutiqueRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(boutique);
    }

    /**
     * DELETE  /boutiques/:id : delete the "id" boutique.
     *
     * @param id the id of the boutique to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/boutiques/{id}")
    public ResponseEntity<Void> deleteBoutique(@PathVariable Long id) {
        log.debug("REST request to delete Boutique : {}", id);
        boutiqueRepository.deleteById(id);
        boutiqueSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/boutiques?query=:query : search for the boutique corresponding
     * to the query.
     *
     * @param query the query of the boutique search
     * @return the result of the search
     */
    @GetMapping("/_search/boutiques")
    public List<Boutique> searchBoutiques(@RequestParam String query) {
        log.debug("REST request to search Boutiques for query {}", query);
        return StreamSupport
            .stream(boutiqueSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @GetMapping("/boutiqueRoute")
    public List<Route> getRoute() {

        List<Route> boutique = boutiqueRepository.findroute();
        return  boutique;
    }


}
