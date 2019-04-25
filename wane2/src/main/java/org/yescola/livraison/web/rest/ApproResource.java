package org.yescola.livraison.web.rest;
import org.yescola.livraison.domain.Appro;
import org.yescola.livraison.repository.ApproRepository;
import org.yescola.livraison.repository.search.ApproSearchRepository;
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
 * REST controller for managing Appro.
 */
@RestController
@RequestMapping("/api")
public class ApproResource {

    private final Logger log = LoggerFactory.getLogger(ApproResource.class);

    private static final String ENTITY_NAME = "appro";

    private final ApproRepository approRepository;

    private final ApproSearchRepository approSearchRepository;

    public ApproResource(ApproRepository approRepository, ApproSearchRepository approSearchRepository) {
        this.approRepository = approRepository;
        this.approSearchRepository = approSearchRepository;
    }

    /**
     * POST  /appros : Create a new appro.
     *
     * @param appro the appro to create
     * @return the ResponseEntity with status 201 (Created) and with body the new appro, or with status 400 (Bad Request) if the appro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/appros")
    public ResponseEntity<Appro> createAppro(@RequestBody Appro appro) throws URISyntaxException {
        log.debug("REST request to save Appro : {}", appro);
        if (appro.getId() != null) {
            throw new BadRequestAlertException("A new appro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Appro result = approRepository.save(appro);
        approSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/appros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /appros : Updates an existing appro.
     *
     * @param appro the appro to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated appro,
     * or with status 400 (Bad Request) if the appro is not valid,
     * or with status 500 (Internal Server Error) if the appro couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/appros")
    public ResponseEntity<Appro> updateAppro(@RequestBody Appro appro) throws URISyntaxException {
        log.debug("REST request to update Appro : {}", appro);
        if (appro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Appro result = approRepository.save(appro);
        approSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, appro.getId().toString()))
            .body(result);
    }

    /**
     * GET  /appros : get all the appros.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of appros in body
     */
    @GetMapping("/appros")
    public List<Appro> getAllAppros() {
        log.debug("REST request to get all Appros");
        return approRepository.findAll();
    }

    /**
     * GET  /appros/:id : get the "id" appro.
     *
     * @param id the id of the appro to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appro, or with status 404 (Not Found)
     */
    @GetMapping("/appros/{id}")
    public ResponseEntity<Appro> getAppro(@PathVariable Long id) {
        log.debug("REST request to get Appro : {}", id);
        Optional<Appro> appro = approRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(appro);
    }

    /**
     * DELETE  /appros/:id : delete the "id" appro.
     *
     * @param id the id of the appro to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/appros/{id}")
    public ResponseEntity<Void> deleteAppro(@PathVariable Long id) {
        log.debug("REST request to delete Appro : {}", id);
        approRepository.deleteById(id);
        approSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/appros?query=:query : search for the appro corresponding
     * to the query.
     *
     * @param query the query of the appro search
     * @return the result of the search
     */
    @GetMapping("/_search/appros")
    public List<Appro> searchAppros(@RequestParam String query) {
        log.debug("REST request to search Appros for query {}", query);
        return StreamSupport
            .stream(approSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
