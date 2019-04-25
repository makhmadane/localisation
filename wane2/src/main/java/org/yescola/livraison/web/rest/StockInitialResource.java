package org.yescola.livraison.web.rest;
import org.yescola.livraison.domain.StockInitial;
import org.yescola.livraison.repository.StockInitialRepository;
import org.yescola.livraison.repository.search.StockInitialSearchRepository;
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
 * REST controller for managing StockInitial.
 */
@RestController
@RequestMapping("/api")
public class StockInitialResource {

    private final Logger log = LoggerFactory.getLogger(StockInitialResource.class);

    private static final String ENTITY_NAME = "stockInitial";

    private final StockInitialRepository stockInitialRepository;

    private final StockInitialSearchRepository stockInitialSearchRepository;

    public StockInitialResource(StockInitialRepository stockInitialRepository, StockInitialSearchRepository stockInitialSearchRepository) {
        this.stockInitialRepository = stockInitialRepository;
        this.stockInitialSearchRepository = stockInitialSearchRepository;
    }

    /**
     * POST  /stock-initials : Create a new stockInitial.
     *
     * @param stockInitial the stockInitial to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stockInitial, or with status 400 (Bad Request) if the stockInitial has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stock-initials")
    public ResponseEntity<StockInitial> createStockInitial(@RequestBody StockInitial stockInitial) throws URISyntaxException {
        log.debug("REST request to save StockInitial : {}", stockInitial);
        if (stockInitial.getId() != null) {
            throw new BadRequestAlertException("A new stockInitial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockInitial result = stockInitialRepository.save(stockInitial);
        stockInitialSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/stock-initials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stock-initials : Updates an existing stockInitial.
     *
     * @param stockInitial the stockInitial to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stockInitial,
     * or with status 400 (Bad Request) if the stockInitial is not valid,
     * or with status 500 (Internal Server Error) if the stockInitial couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stock-initials")
    public ResponseEntity<StockInitial> updateStockInitial(@RequestBody StockInitial stockInitial) throws URISyntaxException {
        log.debug("REST request to update StockInitial : {}", stockInitial);
        if (stockInitial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockInitial result = stockInitialRepository.save(stockInitial);
        stockInitialSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stockInitial.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stock-initials : get all the stockInitials.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stockInitials in body
     */
    @GetMapping("/stock-initials")
    public List<StockInitial> getAllStockInitials() {
        log.debug("REST request to get all StockInitials");
        return stockInitialRepository.findAll();
    }

    /**
     * GET  /stock-initials/:id : get the "id" stockInitial.
     *
     * @param id the id of the stockInitial to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stockInitial, or with status 404 (Not Found)
     */
    @GetMapping("/stock-initials/{id}")
    public ResponseEntity<StockInitial> getStockInitial(@PathVariable Long id) {
        log.debug("REST request to get StockInitial : {}", id);
        Optional<StockInitial> stockInitial = stockInitialRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stockInitial);
    }

    /**
     * DELETE  /stock-initials/:id : delete the "id" stockInitial.
     *
     * @param id the id of the stockInitial to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stock-initials/{id}")
    public ResponseEntity<Void> deleteStockInitial(@PathVariable Long id) {
        log.debug("REST request to delete StockInitial : {}", id);
        stockInitialRepository.deleteById(id);
        stockInitialSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/stock-initials?query=:query : search for the stockInitial corresponding
     * to the query.
     *
     * @param query the query of the stockInitial search
     * @return the result of the search
     */
    @GetMapping("/_search/stock-initials")
    public List<StockInitial> searchStockInitials(@RequestParam String query) {
        log.debug("REST request to search StockInitials for query {}", query);
        return StreamSupport
            .stream(stockInitialSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
