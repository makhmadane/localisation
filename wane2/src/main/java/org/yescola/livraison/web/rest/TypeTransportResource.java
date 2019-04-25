package org.yescola.livraison.web.rest;
import org.yescola.livraison.domain.TypeTransport;
import org.yescola.livraison.repository.TypeTransportRepository;
import org.yescola.livraison.repository.search.TypeTransportSearchRepository;
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
 * REST controller for managing TypeTransport.
 */
@RestController
@RequestMapping("/api")
public class TypeTransportResource {

    private final Logger log = LoggerFactory.getLogger(TypeTransportResource.class);

    private static final String ENTITY_NAME = "typeTransport";

    private final TypeTransportRepository typeTransportRepository;

    private final TypeTransportSearchRepository typeTransportSearchRepository;

    public TypeTransportResource(TypeTransportRepository typeTransportRepository, TypeTransportSearchRepository typeTransportSearchRepository) {
        this.typeTransportRepository = typeTransportRepository;
        this.typeTransportSearchRepository = typeTransportSearchRepository;
    }

    /**
     * POST  /type-transports : Create a new typeTransport.
     *
     * @param typeTransport the typeTransport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeTransport, or with status 400 (Bad Request) if the typeTransport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-transports")
    public ResponseEntity<TypeTransport> createTypeTransport(@RequestBody TypeTransport typeTransport) throws URISyntaxException {
        log.debug("REST request to save TypeTransport : {}", typeTransport);
        if (typeTransport.getId() != null) {
            throw new BadRequestAlertException("A new typeTransport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeTransport result = typeTransportRepository.save(typeTransport);
        typeTransportSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/type-transports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-transports : Updates an existing typeTransport.
     *
     * @param typeTransport the typeTransport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeTransport,
     * or with status 400 (Bad Request) if the typeTransport is not valid,
     * or with status 500 (Internal Server Error) if the typeTransport couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-transports")
    public ResponseEntity<TypeTransport> updateTypeTransport(@RequestBody TypeTransport typeTransport) throws URISyntaxException {
        log.debug("REST request to update TypeTransport : {}", typeTransport);
        if (typeTransport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeTransport result = typeTransportRepository.save(typeTransport);
        typeTransportSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeTransport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-transports : get all the typeTransports.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeTransports in body
     */
    @GetMapping("/type-transports")
    public List<TypeTransport> getAllTypeTransports() {
        log.debug("REST request to get all TypeTransports");
        return typeTransportRepository.findAll();
    }

    /**
     * GET  /type-transports/:id : get the "id" typeTransport.
     *
     * @param id the id of the typeTransport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeTransport, or with status 404 (Not Found)
     */
    @GetMapping("/type-transports/{id}")
    public ResponseEntity<TypeTransport> getTypeTransport(@PathVariable Long id) {
        log.debug("REST request to get TypeTransport : {}", id);
        Optional<TypeTransport> typeTransport = typeTransportRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typeTransport);
    }

    /**
     * DELETE  /type-transports/:id : delete the "id" typeTransport.
     *
     * @param id the id of the typeTransport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-transports/{id}")
    public ResponseEntity<Void> deleteTypeTransport(@PathVariable Long id) {
        log.debug("REST request to delete TypeTransport : {}", id);
        typeTransportRepository.deleteById(id);
        typeTransportSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-transports?query=:query : search for the typeTransport corresponding
     * to the query.
     *
     * @param query the query of the typeTransport search
     * @return the result of the search
     */
    @GetMapping("/_search/type-transports")
    public List<TypeTransport> searchTypeTransports(@RequestParam String query) {
        log.debug("REST request to search TypeTransports for query {}", query);
        return StreamSupport
            .stream(typeTransportSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
