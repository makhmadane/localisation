package org.yescola.livraison.web.rest;
import org.yescola.livraison.domain.Qualite;
import org.yescola.livraison.repository.QualiteRepository;
import org.yescola.livraison.repository.search.QualiteSearchRepository;
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
 * REST controller for managing Qualite.
 */
@RestController
@RequestMapping("/api")
public class QualiteResource {

    private final Logger log = LoggerFactory.getLogger(QualiteResource.class);

    private static final String ENTITY_NAME = "qualite";

    private final QualiteRepository qualiteRepository;

    private final QualiteSearchRepository qualiteSearchRepository;

    public QualiteResource(QualiteRepository qualiteRepository, QualiteSearchRepository qualiteSearchRepository) {
        this.qualiteRepository = qualiteRepository;
        this.qualiteSearchRepository = qualiteSearchRepository;
    }

    /**
     * POST  /qualites : Create a new qualite.
     *
     * @param qualite the qualite to create
     * @return the ResponseEntity with status 201 (Created) and with body the new qualite, or with status 400 (Bad Request) if the qualite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/qualites")
    public ResponseEntity<Qualite> createQualite(@RequestBody Qualite qualite) throws URISyntaxException {
        log.debug("REST request to save Qualite : {}", qualite);
        if (qualite.getId() != null) {
            throw new BadRequestAlertException("A new qualite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Qualite result = qualiteRepository.save(qualite);
        qualiteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/qualites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /qualites : Updates an existing qualite.
     *
     * @param qualite the qualite to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated qualite,
     * or with status 400 (Bad Request) if the qualite is not valid,
     * or with status 500 (Internal Server Error) if the qualite couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/qualites")
    public ResponseEntity<Qualite> updateQualite(@RequestBody Qualite qualite) throws URISyntaxException {
        log.debug("REST request to update Qualite : {}", qualite);
        if (qualite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Qualite result = qualiteRepository.save(qualite);
        qualiteSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, qualite.getId().toString()))
            .body(result);
    }

    /**
     * GET  /qualites : get all the qualites.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of qualites in body
     */
    @GetMapping("/qualites")
    public List<Qualite> getAllQualites() {
        log.debug("REST request to get all Qualites");
        return qualiteRepository.findAll();
    }

    /**
     * GET  /qualites/:id : get the "id" qualite.
     *
     * @param id the id of the qualite to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the qualite, or with status 404 (Not Found)
     */
    @GetMapping("/qualites/{id}")
    public ResponseEntity<Qualite> getQualite(@PathVariable Long id) {
        log.debug("REST request to get Qualite : {}", id);
        Optional<Qualite> qualite = qualiteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(qualite);
    }

    /**
     * DELETE  /qualites/:id : delete the "id" qualite.
     *
     * @param id the id of the qualite to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/qualites/{id}")
    public ResponseEntity<Void> deleteQualite(@PathVariable Long id) {
        log.debug("REST request to delete Qualite : {}", id);
        qualiteRepository.deleteById(id);
        qualiteSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/qualites?query=:query : search for the qualite corresponding
     * to the query.
     *
     * @param query the query of the qualite search
     * @return the result of the search
     */
    @GetMapping("/_search/qualites")
    public List<Qualite> searchQualites(@RequestParam String query) {
        log.debug("REST request to search Qualites for query {}", query);
        return StreamSupport
            .stream(qualiteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
