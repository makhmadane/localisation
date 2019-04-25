package org.yescola.livraison.web.rest;
import org.yescola.livraison.domain.MoyenTransport;
import org.yescola.livraison.repository.MoyenTransportRepository;
import org.yescola.livraison.repository.search.MoyenTransportSearchRepository;
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
 * REST controller for managing MoyenTransport.
 */
@RestController
@RequestMapping("/api")
public class MoyenTransportResource {

    private final Logger log = LoggerFactory.getLogger(MoyenTransportResource.class);

    private static final String ENTITY_NAME = "moyenTransport";

    private final MoyenTransportRepository moyenTransportRepository;

    private final MoyenTransportSearchRepository moyenTransportSearchRepository;

    public MoyenTransportResource(MoyenTransportRepository moyenTransportRepository, MoyenTransportSearchRepository moyenTransportSearchRepository) {
        this.moyenTransportRepository = moyenTransportRepository;
        this.moyenTransportSearchRepository = moyenTransportSearchRepository;
    }

    /**
     * POST  /moyen-transports : Create a new moyenTransport.
     *
     * @param moyenTransport the moyenTransport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new moyenTransport, or with status 400 (Bad Request) if the moyenTransport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/moyen-transports")
    public ResponseEntity<MoyenTransport> createMoyenTransport(@RequestBody MoyenTransport moyenTransport) throws URISyntaxException {
        log.debug("REST request to save MoyenTransport : {}", moyenTransport);
        if (moyenTransport.getId() != null) {
            throw new BadRequestAlertException("A new moyenTransport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MoyenTransport result = moyenTransportRepository.save(moyenTransport);
        moyenTransportSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/moyen-transports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /moyen-transports : Updates an existing moyenTransport.
     *
     * @param moyenTransport the moyenTransport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated moyenTransport,
     * or with status 400 (Bad Request) if the moyenTransport is not valid,
     * or with status 500 (Internal Server Error) if the moyenTransport couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/moyen-transports")
    public ResponseEntity<MoyenTransport> updateMoyenTransport(@RequestBody MoyenTransport moyenTransport) throws URISyntaxException {
        log.debug("REST request to update MoyenTransport : {}", moyenTransport);
        if (moyenTransport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MoyenTransport result = moyenTransportRepository.save(moyenTransport);
        moyenTransportSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, moyenTransport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /moyen-transports : get all the moyenTransports.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of moyenTransports in body
     */
    @GetMapping("/moyen-transports")
    public List<MoyenTransport> getAllMoyenTransports() {
        log.debug("REST request to get all MoyenTransports");
        return moyenTransportRepository.findAll();
    }

    /**
     * GET  /moyen-transports/:id : get the "id" moyenTransport.
     *
     * @param id the id of the moyenTransport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the moyenTransport, or with status 404 (Not Found)
     */
    @GetMapping("/moyen-transports/{id}")
    public ResponseEntity<MoyenTransport> getMoyenTransport(@PathVariable Long id) {
        log.debug("REST request to get MoyenTransport : {}", id);
        Optional<MoyenTransport> moyenTransport = moyenTransportRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(moyenTransport);
    }

    /**
     * DELETE  /moyen-transports/:id : delete the "id" moyenTransport.
     *
     * @param id the id of the moyenTransport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/moyen-transports/{id}")
    public ResponseEntity<Void> deleteMoyenTransport(@PathVariable Long id) {
        log.debug("REST request to delete MoyenTransport : {}", id);
        moyenTransportRepository.deleteById(id);
        moyenTransportSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/moyen-transports?query=:query : search for the moyenTransport corresponding
     * to the query.
     *
     * @param query the query of the moyenTransport search
     * @return the result of the search
     */
    @GetMapping("/_search/moyen-transports")
    public List<MoyenTransport> searchMoyenTransports(@RequestParam String query) {
        log.debug("REST request to search MoyenTransports for query {}", query);
        return StreamSupport
            .stream(moyenTransportSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
