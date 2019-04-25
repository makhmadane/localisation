package org.yescola.livraison.web.rest;
import org.yescola.livraison.domain.TypeRglment;
import org.yescola.livraison.repository.TypeRglmentRepository;
import org.yescola.livraison.repository.search.TypeRglmentSearchRepository;
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
 * REST controller for managing TypeRglment.
 */
@RestController
@RequestMapping("/api")
public class TypeRglmentResource {

    private final Logger log = LoggerFactory.getLogger(TypeRglmentResource.class);

    private static final String ENTITY_NAME = "typeRglment";

    private final TypeRglmentRepository typeRglmentRepository;

    private final TypeRglmentSearchRepository typeRglmentSearchRepository;

    public TypeRglmentResource(TypeRglmentRepository typeRglmentRepository, TypeRglmentSearchRepository typeRglmentSearchRepository) {
        this.typeRglmentRepository = typeRglmentRepository;
        this.typeRglmentSearchRepository = typeRglmentSearchRepository;
    }

    /**
     * POST  /type-rglments : Create a new typeRglment.
     *
     * @param typeRglment the typeRglment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeRglment, or with status 400 (Bad Request) if the typeRglment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-rglments")
    public ResponseEntity<TypeRglment> createTypeRglment(@RequestBody TypeRglment typeRglment) throws URISyntaxException {
        log.debug("REST request to save TypeRglment : {}", typeRglment);
        if (typeRglment.getId() != null) {
            throw new BadRequestAlertException("A new typeRglment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeRglment result = typeRglmentRepository.save(typeRglment);
        typeRglmentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/type-rglments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-rglments : Updates an existing typeRglment.
     *
     * @param typeRglment the typeRglment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeRglment,
     * or with status 400 (Bad Request) if the typeRglment is not valid,
     * or with status 500 (Internal Server Error) if the typeRglment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-rglments")
    public ResponseEntity<TypeRglment> updateTypeRglment(@RequestBody TypeRglment typeRglment) throws URISyntaxException {
        log.debug("REST request to update TypeRglment : {}", typeRglment);
        if (typeRglment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeRglment result = typeRglmentRepository.save(typeRglment);
        typeRglmentSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeRglment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-rglments : get all the typeRglments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeRglments in body
     */
    @GetMapping("/type-rglments")
    public List<TypeRglment> getAllTypeRglments() {
        log.debug("REST request to get all TypeRglments");
        return typeRglmentRepository.findAll();
    }

    /**
     * GET  /type-rglments/:id : get the "id" typeRglment.
     *
     * @param id the id of the typeRglment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeRglment, or with status 404 (Not Found)
     */
    @GetMapping("/type-rglments/{id}")
    public ResponseEntity<TypeRglment> getTypeRglment(@PathVariable Long id) {
        log.debug("REST request to get TypeRglment : {}", id);
        Optional<TypeRglment> typeRglment = typeRglmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typeRglment);
    }

    /**
     * DELETE  /type-rglments/:id : delete the "id" typeRglment.
     *
     * @param id the id of the typeRglment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-rglments/{id}")
    public ResponseEntity<Void> deleteTypeRglment(@PathVariable Long id) {
        log.debug("REST request to delete TypeRglment : {}", id);
        typeRglmentRepository.deleteById(id);
        typeRglmentSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-rglments?query=:query : search for the typeRglment corresponding
     * to the query.
     *
     * @param query the query of the typeRglment search
     * @return the result of the search
     */
    @GetMapping("/_search/type-rglments")
    public List<TypeRglment> searchTypeRglments(@RequestParam String query) {
        log.debug("REST request to search TypeRglments for query {}", query);
        return StreamSupport
            .stream(typeRglmentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
