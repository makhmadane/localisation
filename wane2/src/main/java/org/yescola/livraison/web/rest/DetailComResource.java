package org.yescola.livraison.web.rest;
import org.yescola.livraison.domain.DetailCom;
import org.yescola.livraison.repository.DetailComRepository;
import org.yescola.livraison.repository.search.DetailComSearchRepository;
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
 * REST controller for managing DetailCom.
 */
@RestController
@RequestMapping("/api")
public class DetailComResource {

    private final Logger log = LoggerFactory.getLogger(DetailComResource.class);

    private static final String ENTITY_NAME = "detailCom";

    private final DetailComRepository detailComRepository;

    private final DetailComSearchRepository detailComSearchRepository;

    public DetailComResource(DetailComRepository detailComRepository, DetailComSearchRepository detailComSearchRepository) {
        this.detailComRepository = detailComRepository;
        this.detailComSearchRepository = detailComSearchRepository;
    }

    /**
     * POST  /detail-coms : Create a new detailCom.
     *
     * @param detailCom the detailCom to create
     * @return the ResponseEntity with status 201 (Created) and with body the new detailCom, or with status 400 (Bad Request) if the detailCom has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/detail-coms")
    public ResponseEntity<DetailCom> createDetailCom(@RequestBody DetailCom detailCom) throws URISyntaxException {
        log.debug("REST request to save DetailCom : {}", detailCom);
        if (detailCom.getId() != null) {
            throw new BadRequestAlertException("A new detailCom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetailCom result = detailComRepository.save(detailCom);
        detailComSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/detail-coms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /detail-coms : Updates an existing detailCom.
     *
     * @param detailCom the detailCom to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated detailCom,
     * or with status 400 (Bad Request) if the detailCom is not valid,
     * or with status 500 (Internal Server Error) if the detailCom couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/detail-coms")
    public ResponseEntity<DetailCom> updateDetailCom(@RequestBody DetailCom detailCom) throws URISyntaxException {
        log.debug("REST request to update DetailCom : {}", detailCom);
        if (detailCom.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DetailCom result = detailComRepository.save(detailCom);
        detailComSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, detailCom.getId().toString()))
            .body(result);
    }

    /**
     * GET  /detail-coms : get all the detailComs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of detailComs in body
     */
    @GetMapping("/detail-coms")
    public List<DetailCom> getAllDetailComs() {
        log.debug("REST request to get all DetailComs");
        return detailComRepository.findAll();
    }

    /**
     * GET  /detail-coms/:id : get the "id" detailCom.
     *
     * @param id the id of the detailCom to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the detailCom, or with status 404 (Not Found)
     */
    @GetMapping("/detail-coms/{id}")
    public ResponseEntity<DetailCom> getDetailCom(@PathVariable Long id) {
        log.debug("REST request to get DetailCom : {}", id);
        Optional<DetailCom> detailCom = detailComRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(detailCom);
    }

    /**
     * DELETE  /detail-coms/:id : delete the "id" detailCom.
     *
     * @param id the id of the detailCom to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/detail-coms/{id}")
    public ResponseEntity<Void> deleteDetailCom(@PathVariable Long id) {
        log.debug("REST request to delete DetailCom : {}", id);
        detailComRepository.deleteById(id);
        detailComSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/detail-coms?query=:query : search for the detailCom corresponding
     * to the query.
     *
     * @param query the query of the detailCom search
     * @return the result of the search
     */
    @GetMapping("/_search/detail-coms")
    public List<DetailCom> searchDetailComs(@RequestParam String query) {
        log.debug("REST request to search DetailComs for query {}", query);
        return StreamSupport
            .stream(detailComSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
