package org.yescola.livraison.web.rest;
import org.yescola.livraison.domain.DetailRoute;
import org.yescola.livraison.repository.DetailRouteRepository;
import org.yescola.livraison.repository.search.DetailRouteSearchRepository;
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
 * REST controller for managing DetailRoute.
 */
@RestController
@RequestMapping("/api")
public class DetailRouteResource {

    private final Logger log = LoggerFactory.getLogger(DetailRouteResource.class);

    private static final String ENTITY_NAME = "detailRoute";

    private final DetailRouteRepository detailRouteRepository;

    private final DetailRouteSearchRepository detailRouteSearchRepository;

    public DetailRouteResource(DetailRouteRepository detailRouteRepository, DetailRouteSearchRepository detailRouteSearchRepository) {
        this.detailRouteRepository = detailRouteRepository;
        this.detailRouteSearchRepository = detailRouteSearchRepository;
    }

    /**
     * POST  /detail-routes : Create a new detailRoute.
     *
     * @param detailRoute the detailRoute to create
     * @return the ResponseEntity with status 201 (Created) and with body the new detailRoute, or with status 400 (Bad Request) if the detailRoute has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/detail-routes")
    public ResponseEntity<DetailRoute> createDetailRoute(@RequestBody DetailRoute detailRoute) throws URISyntaxException {
        log.debug("REST request to save DetailRoute : {}", detailRoute);
        if (detailRoute.getId() != null) {
            throw new BadRequestAlertException("A new detailRoute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetailRoute result = detailRouteRepository.save(detailRoute);
        detailRouteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/detail-routes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /detail-routes : Updates an existing detailRoute.
     *
     * @param detailRoute the detailRoute to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated detailRoute,
     * or with status 400 (Bad Request) if the detailRoute is not valid,
     * or with status 500 (Internal Server Error) if the detailRoute couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/detail-routes")
    public ResponseEntity<DetailRoute> updateDetailRoute(@RequestBody DetailRoute detailRoute) throws URISyntaxException {
        log.debug("REST request to update DetailRoute : {}", detailRoute);
        if (detailRoute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DetailRoute result = detailRouteRepository.save(detailRoute);
        detailRouteSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, detailRoute.getId().toString()))
            .body(result);
    }

    /**
     * GET  /detail-routes : get all the detailRoutes.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of detailRoutes in body
     */
    @GetMapping("/detail-routes")
    public List<DetailRoute> getAllDetailRoutes(@RequestParam(required = false) String filter) {
        if ("commande-is-null".equals(filter)) {
            log.debug("REST request to get all DetailRoutes where commande is null");
            return StreamSupport
                .stream(detailRouteRepository.findAll().spliterator(), false)
                .filter(detailRoute -> detailRoute.getCommande() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all DetailRoutes");
        return detailRouteRepository.findAll();
    }

    /**
     * GET  /detail-routes/:id : get the "id" detailRoute.
     *
     * @param id the id of the detailRoute to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the detailRoute, or with status 404 (Not Found)
     */
    @GetMapping("/detail-routes/{id}")
    public ResponseEntity<DetailRoute> getDetailRoute(@PathVariable Long id) {
        log.debug("REST request to get DetailRoute : {}", id);
        Optional<DetailRoute> detailRoute = detailRouteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(detailRoute);
    }

    /**
     * DELETE  /detail-routes/:id : delete the "id" detailRoute.
     *
     * @param id the id of the detailRoute to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/detail-routes/{id}")
    public ResponseEntity<Void> deleteDetailRoute(@PathVariable Long id) {
        log.debug("REST request to delete DetailRoute : {}", id);
        detailRouteRepository.deleteById(id);
        detailRouteSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/detail-routes?query=:query : search for the detailRoute corresponding
     * to the query.
     *
     * @param query the query of the detailRoute search
     * @return the result of the search
     */
    @GetMapping("/_search/detail-routes")
    public List<DetailRoute> searchDetailRoutes(@RequestParam String query) {
        log.debug("REST request to search DetailRoutes for query {}", query);
        return StreamSupport
            .stream(detailRouteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
