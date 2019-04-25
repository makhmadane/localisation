package org.yescola.livraison.web.rest;
import org.yescola.livraison.domain.TypeRoute;
import org.yescola.livraison.repository.TypeRouteRepository;
import org.yescola.livraison.repository.search.TypeRouteSearchRepository;
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
 * REST controller for managing TypeRoute.
 */
@RestController
@RequestMapping("/api")
public class TypeRouteResource {

    private final Logger log = LoggerFactory.getLogger(TypeRouteResource.class);

    private static final String ENTITY_NAME = "typeRoute";

    private final TypeRouteRepository typeRouteRepository;

    private final TypeRouteSearchRepository typeRouteSearchRepository;

    public TypeRouteResource(TypeRouteRepository typeRouteRepository, TypeRouteSearchRepository typeRouteSearchRepository) {
        this.typeRouteRepository = typeRouteRepository;
        this.typeRouteSearchRepository = typeRouteSearchRepository;
    }

    /**
     * POST  /type-routes : Create a new typeRoute.
     *
     * @param typeRoute the typeRoute to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeRoute, or with status 400 (Bad Request) if the typeRoute has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-routes")
    public ResponseEntity<TypeRoute> createTypeRoute(@RequestBody TypeRoute typeRoute) throws URISyntaxException {
        log.debug("REST request to save TypeRoute : {}", typeRoute);
        if (typeRoute.getId() != null) {
            throw new BadRequestAlertException("A new typeRoute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeRoute result = typeRouteRepository.save(typeRoute);
        typeRouteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/type-routes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-routes : Updates an existing typeRoute.
     *
     * @param typeRoute the typeRoute to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeRoute,
     * or with status 400 (Bad Request) if the typeRoute is not valid,
     * or with status 500 (Internal Server Error) if the typeRoute couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-routes")
    public ResponseEntity<TypeRoute> updateTypeRoute(@RequestBody TypeRoute typeRoute) throws URISyntaxException {
        log.debug("REST request to update TypeRoute : {}", typeRoute);
        if (typeRoute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeRoute result = typeRouteRepository.save(typeRoute);
        typeRouteSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeRoute.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-routes : get all the typeRoutes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeRoutes in body
     */
    @GetMapping("/type-routes")
    public List<TypeRoute> getAllTypeRoutes() {
        log.debug("REST request to get all TypeRoutes");
        return typeRouteRepository.findAll();
    }

    /**
     * GET  /type-routes/:id : get the "id" typeRoute.
     *
     * @param id the id of the typeRoute to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeRoute, or with status 404 (Not Found)
     */
    @GetMapping("/type-routes/{id}")
    public ResponseEntity<TypeRoute> getTypeRoute(@PathVariable Long id) {
        log.debug("REST request to get TypeRoute : {}", id);
        Optional<TypeRoute> typeRoute = typeRouteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typeRoute);
    }

    /**
     * DELETE  /type-routes/:id : delete the "id" typeRoute.
     *
     * @param id the id of the typeRoute to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-routes/{id}")
    public ResponseEntity<Void> deleteTypeRoute(@PathVariable Long id) {
        log.debug("REST request to delete TypeRoute : {}", id);
        typeRouteRepository.deleteById(id);
        typeRouteSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-routes?query=:query : search for the typeRoute corresponding
     * to the query.
     *
     * @param query the query of the typeRoute search
     * @return the result of the search
     */
    @GetMapping("/_search/type-routes")
    public List<TypeRoute> searchTypeRoutes(@RequestParam String query) {
        log.debug("REST request to search TypeRoutes for query {}", query);
        return StreamSupport
            .stream(typeRouteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
