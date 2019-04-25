package org.yescola.livraison.web.rest;
import org.yescola.livraison.domain.Commune;
import org.yescola.livraison.repository.CommuneRepository;
import org.yescola.livraison.repository.search.CommuneSearchRepository;
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
 * REST controller for managing Commune.
 */
@RestController
@RequestMapping("/api")
public class CommuneResource {

    private final Logger log = LoggerFactory.getLogger(CommuneResource.class);

    private static final String ENTITY_NAME = "commune";

    private final CommuneRepository communeRepository;

    private final CommuneSearchRepository communeSearchRepository;

    public CommuneResource(CommuneRepository communeRepository, CommuneSearchRepository communeSearchRepository) {
        this.communeRepository = communeRepository;
        this.communeSearchRepository = communeSearchRepository;
    }

    /**
     * POST  /communes : Create a new commune.
     *
     * @param commune the commune to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commune, or with status 400 (Bad Request) if the commune has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/communes")
    public ResponseEntity<Commune> createCommune(@RequestBody Commune commune) throws URISyntaxException {
        log.debug("REST request to save Commune : {}", commune);
        if (commune.getId() != null) {
            throw new BadRequestAlertException("A new commune cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Commune result = communeRepository.save(commune);
        communeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/communes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /communes : Updates an existing commune.
     *
     * @param commune the commune to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commune,
     * or with status 400 (Bad Request) if the commune is not valid,
     * or with status 500 (Internal Server Error) if the commune couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/communes")
    public ResponseEntity<Commune> updateCommune(@RequestBody Commune commune) throws URISyntaxException {
        log.debug("REST request to update Commune : {}", commune);
        if (commune.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Commune result = communeRepository.save(commune);
        communeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commune.getId().toString()))
            .body(result);
    }

    /**
     * GET  /communes : get all the communes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of communes in body
     */
    @GetMapping("/communes")
    public List<Commune> getAllCommunes() {
        log.debug("REST request to get all Communes");
        return communeRepository.findAll();
    }

    /**
     * GET  /communes/:id : get the "id" commune.
     *
     * @param id the id of the commune to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commune, or with status 404 (Not Found)
     */
    @GetMapping("/communes/{id}")
    public ResponseEntity<Commune> getCommune(@PathVariable Long id) {
        log.debug("REST request to get Commune : {}", id);
        Optional<Commune> commune = communeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(commune);
    }

    /**
     * DELETE  /communes/:id : delete the "id" commune.
     *
     * @param id the id of the commune to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/communes/{id}")
    public ResponseEntity<Void> deleteCommune(@PathVariable Long id) {
        log.debug("REST request to delete Commune : {}", id);
        communeRepository.deleteById(id);
        communeSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/communes?query=:query : search for the commune corresponding
     * to the query.
     *
     * @param query the query of the commune search
     * @return the result of the search
     */
    @GetMapping("/_search/communes")
    public List<Commune> searchCommunes(@RequestParam String query) {
        log.debug("REST request to search Communes for query {}", query);
        return StreamSupport
            .stream(communeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
