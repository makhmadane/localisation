package org.yescola.livraison.web.rest;
import org.yescola.livraison.domain.Depot;
import org.yescola.livraison.repository.DepotRepository;
import org.yescola.livraison.repository.search.DepotSearchRepository;
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
 * REST controller for managing Depot.
 */
@RestController
@RequestMapping("/api")
public class DepotResource {

    private final Logger log = LoggerFactory.getLogger(DepotResource.class);

    private static final String ENTITY_NAME = "depot";

    private final DepotRepository depotRepository;

    private final DepotSearchRepository depotSearchRepository;

    public DepotResource(DepotRepository depotRepository, DepotSearchRepository depotSearchRepository) {
        this.depotRepository = depotRepository;
        this.depotSearchRepository = depotSearchRepository;
    }

    /**
     * POST  /depots : Create a new depot.
     *
     * @param depot the depot to create
     * @return the ResponseEntity with status 201 (Created) and with body the new depot, or with status 400 (Bad Request) if the depot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/depots")
    public ResponseEntity<Depot> createDepot(@RequestBody Depot depot) throws URISyntaxException {
        log.debug("REST request to save Depot : {}", depot);
        if (depot.getId() != null) {
            throw new BadRequestAlertException("A new depot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Depot result = depotRepository.save(depot);
        depotSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/depots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /depots : Updates an existing depot.
     *
     * @param depot the depot to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated depot,
     * or with status 400 (Bad Request) if the depot is not valid,
     * or with status 500 (Internal Server Error) if the depot couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/depots")
    public ResponseEntity<Depot> updateDepot(@RequestBody Depot depot) throws URISyntaxException {
        log.debug("REST request to update Depot : {}", depot);
        if (depot.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Depot result = depotRepository.save(depot);
        depotSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, depot.getId().toString()))
            .body(result);
    }

    /**
     * GET  /depots : get all the depots.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of depots in body
     */
    @GetMapping("/depots")
    public List<Depot> getAllDepots() {
        log.debug("REST request to get all Depots");
        return depotRepository.findAll();
    }

    /**
     * GET  /depots/:id : get the "id" depot.
     *
     * @param id the id of the depot to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the depot, or with status 404 (Not Found)
     */
    @GetMapping("/depots/{id}")
    public ResponseEntity<Depot> getDepot(@PathVariable Long id) {
        log.debug("REST request to get Depot : {}", id);
        Optional<Depot> depot = depotRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(depot);
    }

    /**
     * DELETE  /depots/:id : delete the "id" depot.
     *
     * @param id the id of the depot to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/depots/{id}")
    public ResponseEntity<Void> deleteDepot(@PathVariable Long id) {
        log.debug("REST request to delete Depot : {}", id);
        depotRepository.deleteById(id);
        depotSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/depots?query=:query : search for the depot corresponding
     * to the query.
     *
     * @param query the query of the depot search
     * @return the result of the search
     */
    @GetMapping("/_search/depots")
    public List<Depot> searchDepots(@RequestParam String query) {
        log.debug("REST request to search Depots for query {}", query);
        return StreamSupport
            .stream(depotSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
