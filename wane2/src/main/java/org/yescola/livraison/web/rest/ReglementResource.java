package org.yescola.livraison.web.rest;
import org.yescola.livraison.domain.Reglement;
import org.yescola.livraison.repository.ReglementRepository;
import org.yescola.livraison.repository.search.ReglementSearchRepository;
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
 * REST controller for managing Reglement.
 */
@RestController
@RequestMapping("/api")
public class ReglementResource {

    private final Logger log = LoggerFactory.getLogger(ReglementResource.class);

    private static final String ENTITY_NAME = "reglement";

    private final ReglementRepository reglementRepository;

    private final ReglementSearchRepository reglementSearchRepository;

    public ReglementResource(ReglementRepository reglementRepository, ReglementSearchRepository reglementSearchRepository) {
        this.reglementRepository = reglementRepository;
        this.reglementSearchRepository = reglementSearchRepository;
    }

    /**
     * POST  /reglements : Create a new reglement.
     *
     * @param reglement the reglement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reglement, or with status 400 (Bad Request) if the reglement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reglements")
    public ResponseEntity<Reglement> createReglement(@RequestBody Reglement reglement) throws URISyntaxException {
        log.debug("REST request to save Reglement : {}", reglement);
        if (reglement.getId() != null) {
            throw new BadRequestAlertException("A new reglement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reglement result = reglementRepository.save(reglement);
        reglementSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/reglements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reglements : Updates an existing reglement.
     *
     * @param reglement the reglement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reglement,
     * or with status 400 (Bad Request) if the reglement is not valid,
     * or with status 500 (Internal Server Error) if the reglement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reglements")
    public ResponseEntity<Reglement> updateReglement(@RequestBody Reglement reglement) throws URISyntaxException {
        log.debug("REST request to update Reglement : {}", reglement);
        if (reglement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Reglement result = reglementRepository.save(reglement);
        reglementSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reglement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reglements : get all the reglements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reglements in body
     */
    @GetMapping("/reglements")
    public List<Reglement> getAllReglements() {
        log.debug("REST request to get all Reglements");
        return reglementRepository.findAll();
    }

    /**
     * GET  /reglements/:id : get the "id" reglement.
     *
     * @param id the id of the reglement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reglement, or with status 404 (Not Found)
     */
    @GetMapping("/reglements/{id}")
    public ResponseEntity<Reglement> getReglement(@PathVariable Long id) {
        log.debug("REST request to get Reglement : {}", id);
        Optional<Reglement> reglement = reglementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reglement);
    }

    /**
     * DELETE  /reglements/:id : delete the "id" reglement.
     *
     * @param id the id of the reglement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reglements/{id}")
    public ResponseEntity<Void> deleteReglement(@PathVariable Long id) {
        log.debug("REST request to delete Reglement : {}", id);
        reglementRepository.deleteById(id);
        reglementSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/reglements?query=:query : search for the reglement corresponding
     * to the query.
     *
     * @param query the query of the reglement search
     * @return the result of the search
     */
    @GetMapping("/_search/reglements")
    public List<Reglement> searchReglements(@RequestParam String query) {
        log.debug("REST request to search Reglements for query {}", query);
        return StreamSupport
            .stream(reglementSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
