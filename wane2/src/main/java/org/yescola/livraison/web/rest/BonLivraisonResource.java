package org.yescola.livraison.web.rest;
import org.yescola.livraison.domain.BonLivraison;
import org.yescola.livraison.repository.BonLivraisonRepository;
import org.yescola.livraison.repository.search.BonLivraisonSearchRepository;
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
 * REST controller for managing BonLivraison.
 */
@RestController
@RequestMapping("/api")
public class BonLivraisonResource {

    private final Logger log = LoggerFactory.getLogger(BonLivraisonResource.class);

    private static final String ENTITY_NAME = "bonLivraison";

    private final BonLivraisonRepository bonLivraisonRepository;

    private final BonLivraisonSearchRepository bonLivraisonSearchRepository;

    public BonLivraisonResource(BonLivraisonRepository bonLivraisonRepository, BonLivraisonSearchRepository bonLivraisonSearchRepository) {
        this.bonLivraisonRepository = bonLivraisonRepository;
        this.bonLivraisonSearchRepository = bonLivraisonSearchRepository;
    }

    /**
     * POST  /bon-livraisons : Create a new bonLivraison.
     *
     * @param bonLivraison the bonLivraison to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bonLivraison, or with status 400 (Bad Request) if the bonLivraison has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bon-livraisons")
    public ResponseEntity<BonLivraison> createBonLivraison(@RequestBody BonLivraison bonLivraison) throws URISyntaxException {
        log.debug("REST request to save BonLivraison : {}", bonLivraison);
        if (bonLivraison.getId() != null) {
            throw new BadRequestAlertException("A new bonLivraison cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BonLivraison result = bonLivraisonRepository.save(bonLivraison);
        bonLivraisonSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/bon-livraisons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bon-livraisons : Updates an existing bonLivraison.
     *
     * @param bonLivraison the bonLivraison to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bonLivraison,
     * or with status 400 (Bad Request) if the bonLivraison is not valid,
     * or with status 500 (Internal Server Error) if the bonLivraison couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bon-livraisons")
    public ResponseEntity<BonLivraison> updateBonLivraison(@RequestBody BonLivraison bonLivraison) throws URISyntaxException {
        log.debug("REST request to update BonLivraison : {}", bonLivraison);
        if (bonLivraison.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BonLivraison result = bonLivraisonRepository.save(bonLivraison);
        bonLivraisonSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bonLivraison.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bon-livraisons : get all the bonLivraisons.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bonLivraisons in body
     */
    @GetMapping("/bon-livraisons")
    public List<BonLivraison> getAllBonLivraisons() {
        log.debug("REST request to get all BonLivraisons");
        return bonLivraisonRepository.findAll();
    }

    /**
     * GET  /bon-livraisons/:id : get the "id" bonLivraison.
     *
     * @param id the id of the bonLivraison to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bonLivraison, or with status 404 (Not Found)
     */
    @GetMapping("/bon-livraisons/{id}")
    public ResponseEntity<BonLivraison> getBonLivraison(@PathVariable Long id) {
        log.debug("REST request to get BonLivraison : {}", id);
        Optional<BonLivraison> bonLivraison = bonLivraisonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bonLivraison);
    }

    /**
     * DELETE  /bon-livraisons/:id : delete the "id" bonLivraison.
     *
     * @param id the id of the bonLivraison to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bon-livraisons/{id}")
    public ResponseEntity<Void> deleteBonLivraison(@PathVariable Long id) {
        log.debug("REST request to delete BonLivraison : {}", id);
        bonLivraisonRepository.deleteById(id);
        bonLivraisonSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/bon-livraisons?query=:query : search for the bonLivraison corresponding
     * to the query.
     *
     * @param query the query of the bonLivraison search
     * @return the result of the search
     */
    @GetMapping("/_search/bon-livraisons")
    public List<BonLivraison> searchBonLivraisons(@RequestParam String query) {
        log.debug("REST request to search BonLivraisons for query {}", query);
        return StreamSupport
            .stream(bonLivraisonSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
