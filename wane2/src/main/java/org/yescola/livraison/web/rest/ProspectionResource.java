package org.yescola.livraison.web.rest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.yescola.livraison.domain.Prospection;
import org.yescola.livraison.repository.ProspectionRepository;
import org.yescola.livraison.repository.search.ProspectionSearchRepository;
import org.yescola.livraison.web.rest.errors.BadRequestAlertException;
import org.yescola.livraison.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yescola.livraison.web.rest.util.PaginationUtil;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Prospection.
 */
@RestController
@RequestMapping("/api")
public class ProspectionResource {

    private final Logger log = LoggerFactory.getLogger(ProspectionResource.class);

    private static final String ENTITY_NAME = "prospection";

    private final ProspectionRepository prospectionRepository;

    private final ProspectionSearchRepository prospectionSearchRepository;

    public ProspectionResource(ProspectionRepository prospectionRepository, ProspectionSearchRepository prospectionSearchRepository) {
        this.prospectionRepository = prospectionRepository;
        this.prospectionSearchRepository = prospectionSearchRepository;
    }

    /**
     * POST  /prospections : Create a new prospection.
     *
     * @param prospection the prospection to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prospection, or with status 400 (Bad Request) if the prospection has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prospections")
    public ResponseEntity<Prospection> createProspection(@RequestBody Prospection prospection) throws URISyntaxException {
        log.debug("REST request to save Prospection : {}", prospection);
        if (prospection.getId() != null) {
            throw new BadRequestAlertException("A new prospection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Prospection result = prospectionRepository.save(prospection);
        prospectionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prospections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prospections : Updates an existing prospection.
     *
     * @param prospection the prospection to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prospection,
     * or with status 400 (Bad Request) if the prospection is not valid,
     * or with status 500 (Internal Server Error) if the prospection couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prospections")
    public ResponseEntity<Prospection> updateProspection(@RequestBody Prospection prospection) throws URISyntaxException {
        log.debug("REST request to update Prospection : {}", prospection);
        if (prospection.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Prospection result = prospectionRepository.save(prospection);
        prospectionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prospection.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prospections : get all the prospections.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of prospections in body
     */
    @GetMapping("/prospections")
    public List<Prospection> getAllProspections() {
        log.debug("REST request to get all Prospections");
        return prospectionRepository.findAll();
    }

    /**
     * GET  /prospections/:id : get the "id" prospection.
     *
     * @param id the id of the prospection to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prospection, or with status 404 (Not Found)
     */
    @GetMapping("/prospections/{id}")
    public ResponseEntity<Prospection> getProspection(@PathVariable Long id) {
        log.debug("REST request to get Prospection : {}", id);
        Optional<Prospection> prospection = prospectionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(prospection);
    }

    /**
     * DELETE  /prospections/:id : delete the "id" prospection.
     *
     * @param id the id of the prospection to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prospections/{id}")
    public ResponseEntity<Void> deleteProspection(@PathVariable Long id) {
        log.debug("REST request to delete Prospection : {}", id);
        prospectionRepository.deleteById(id);
        prospectionSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/prospections?query=:query : search for the prospection corresponding
     * to the query.
     *
     * @param query the query of the prospection search
     * @return the result of the search
     */
    @GetMapping("/_search/prospections")
    public List<Prospection> searchProspections(@RequestParam String query) {
        log.debug("REST request to search Prospections for query {}", query);
        return StreamSupport
            .stream(prospectionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
    @GetMapping("/prospectionsEtat/{name}")
    public ResponseEntity<Prospection> getProspectionByEtat(@PathVariable String name) {
        Optional<Prospection> prospection = prospectionRepository.getProspectionByEtat(name);
        return ResponseUtil.wrapOrNotFound(prospection);

    }
    @GetMapping("/prospectionsJour/{name}")
    public ResponseEntity<Prospection> getProspectionByJour(@PathVariable String name) {
        Optional<Prospection> prospection = prospectionRepository.getProspectionByJour(name);
        return ResponseUtil.wrapOrNotFound(prospection);

    }
    @GetMapping("/prospectionsDate/{name}")
    public ResponseEntity<Prospection> getProspectionByDate(@PathVariable String name) {
        Optional<Prospection> prospection = prospectionRepository.getProspectionByDate(name);
        return ResponseUtil.wrapOrNotFound(prospection);

    }
   @GetMapping("/prospectionsPrevendeur/{name}")
    public ResponseEntity<Prospection> getProspectionByPrevendeur(@PathVariable Long name) {
        Optional<Prospection> prospection = prospectionRepository.getProspectionByPrevendeur(name);
        return ResponseUtil.wrapOrNotFound(prospection);

    }
  /*@GetMapping("/prospectionsTablette/")
    public ResponseEntity <List<Prospection>> getProspectionByTablette(Pageable pageable) {
     Page <Prospection> page = prospectionRepository.gettablette(pageable);
     HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prospectionTablette");
     return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);*/

    }
   /* @GetMapping("/prospectionsSecteur/{name}")
    public ResponseEntity<Prospection> getProspectionBySecteur(@PathVariable String name) {
        Optional<Prospection> prospection = prospectionRepository.getProspectionBySecteur(name);
        return ResponseUtil.wrapOrNotFound(prospection);*/





