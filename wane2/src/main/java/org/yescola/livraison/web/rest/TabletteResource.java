package org.yescola.livraison.web.rest;
import org.yescola.livraison.domain.Tablette;
import org.yescola.livraison.repository.TabletteRepository;
import org.yescola.livraison.repository.search.TabletteSearchRepository;
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
 * REST controller for managing Tablette.
 */
@RestController
@RequestMapping("/api")
public class TabletteResource {

    private final Logger log = LoggerFactory.getLogger(TabletteResource.class);

    private static final String ENTITY_NAME = "tablette";

    private final TabletteRepository tabletteRepository;

    private final TabletteSearchRepository tabletteSearchRepository;

    public TabletteResource(TabletteRepository tabletteRepository, TabletteSearchRepository tabletteSearchRepository) {
        this.tabletteRepository = tabletteRepository;
        this.tabletteSearchRepository = tabletteSearchRepository;
    }

    /**
     * POST  /tablettes : Create a new tablette.
     *
     * @param tablette the tablette to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tablette, or with status 400 (Bad Request) if the tablette has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tablettes")
    public ResponseEntity<Tablette> createTablette(@RequestBody Tablette tablette) throws URISyntaxException {
        log.debug("REST request to save Tablette : {}", tablette);
        if (tablette.getId() != null) {
            throw new BadRequestAlertException("A new tablette cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tablette result = tabletteRepository.save(tablette);
        tabletteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tablettes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tablettes : Updates an existing tablette.
     *
     * @param tablette the tablette to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tablette,
     * or with status 400 (Bad Request) if the tablette is not valid,
     * or with status 500 (Internal Server Error) if the tablette couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tablettes")
    public ResponseEntity<Tablette> updateTablette(@RequestBody Tablette tablette) throws URISyntaxException {
        log.debug("REST request to update Tablette : {}", tablette);
        if (tablette.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tablette result = tabletteRepository.save(tablette);
        tabletteSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tablette.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tablettes : get all the tablettes.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of tablettes in body
     */
    @GetMapping("/tablettes")
    public List<Tablette> getAllTablettes(@RequestParam(required = false) String filter) {
        if ("employee-is-null".equals(filter)) {
            log.debug("REST request to get all Tablettes where employee is null");
            return StreamSupport
                .stream(tabletteRepository.findAll().spliterator(), false)
                .filter(tablette -> tablette.getEmployee() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Tablettes");
        return tabletteRepository.findAll();
    }

    /**
     * GET  /tablettes/:id : get the "id" tablette.
     *
     * @param id the id of the tablette to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tablette, or with status 404 (Not Found)
     */
    @GetMapping("/tablettes/{id}")
    public ResponseEntity<Tablette> getTablette(@PathVariable Long id) {
        log.debug("REST request to get Tablette : {}", id);
        Optional<Tablette> tablette = tabletteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tablette);
    }

    /**
     * DELETE  /tablettes/:id : delete the "id" tablette.
     *
     * @param id the id of the tablette to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tablettes/{id}")
    public ResponseEntity<Void> deleteTablette(@PathVariable Long id) {
        log.debug("REST request to delete Tablette : {}", id);
        tabletteRepository.deleteById(id);
        tabletteSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tablettes?query=:query : search for the tablette corresponding
     * to the query.
     *
     * @param query the query of the tablette search
     * @return the result of the search
     */
    @GetMapping("/_search/tablettes")
    public List<Tablette> searchTablettes(@RequestParam String query) {
        log.debug("REST request to search Tablettes for query {}", query);
        return StreamSupport
            .stream(tabletteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
