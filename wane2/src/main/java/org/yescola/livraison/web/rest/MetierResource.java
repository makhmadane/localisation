package org.yescola.livraison.web.rest;
import org.yescola.livraison.domain.Metier;
import org.yescola.livraison.repository.MetierRepository;
import org.yescola.livraison.repository.search.MetierSearchRepository;
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
 * REST controller for managing Metier.
 */
@RestController
@RequestMapping("/api")
public class MetierResource {

    private final Logger log = LoggerFactory.getLogger(MetierResource.class);

    private static final String ENTITY_NAME = "metier";

    private final MetierRepository metierRepository;

    private final MetierSearchRepository metierSearchRepository;

    public MetierResource(MetierRepository metierRepository, MetierSearchRepository metierSearchRepository) {
        this.metierRepository = metierRepository;
        this.metierSearchRepository = metierSearchRepository;
    }

    /**
     * POST  /metiers : Create a new metier.
     *
     * @param metier the metier to create
     * @return the ResponseEntity with status 201 (Created) and with body the new metier, or with status 400 (Bad Request) if the metier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/metiers")
    public ResponseEntity<Metier> createMetier(@RequestBody Metier metier) throws URISyntaxException {
        log.debug("REST request to save Metier : {}", metier);
        if (metier.getId() != null) {
            throw new BadRequestAlertException("A new metier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Metier result = metierRepository.save(metier);
        metierSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/metiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /metiers : Updates an existing metier.
     *
     * @param metier the metier to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated metier,
     * or with status 400 (Bad Request) if the metier is not valid,
     * or with status 500 (Internal Server Error) if the metier couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/metiers")
    public ResponseEntity<Metier> updateMetier(@RequestBody Metier metier) throws URISyntaxException {
        log.debug("REST request to update Metier : {}", metier);
        if (metier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Metier result = metierRepository.save(metier);
        metierSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, metier.getId().toString()))
            .body(result);
    }

    /**
     * GET  /metiers : get all the metiers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of metiers in body
     */
    @GetMapping("/metiers")
    public List<Metier> getAllMetiers() {
        log.debug("REST request to get all Metiers");
        return metierRepository.findAll();
    }

    /**
     * GET  /metiers/:id : get the "id" metier.
     *
     * @param id the id of the metier to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the metier, or with status 404 (Not Found)
     */
    @GetMapping("/metiers/{id}")
    public ResponseEntity<Metier> getMetier(@PathVariable Long id) {
        log.debug("REST request to get Metier : {}", id);
        Optional<Metier> metier = metierRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(metier);
    }

    /**
     * DELETE  /metiers/:id : delete the "id" metier.
     *
     * @param id the id of the metier to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/metiers/{id}")
    public ResponseEntity<Void> deleteMetier(@PathVariable Long id) {
        log.debug("REST request to delete Metier : {}", id);
        metierRepository.deleteById(id);
        metierSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/metiers?query=:query : search for the metier corresponding
     * to the query.
     *
     * @param query the query of the metier search
     * @return the result of the search
     */
    @GetMapping("/_search/metiers")
    public List<Metier> searchMetiers(@RequestParam String query) {
        log.debug("REST request to search Metiers for query {}", query);
        return StreamSupport
            .stream(metierSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
