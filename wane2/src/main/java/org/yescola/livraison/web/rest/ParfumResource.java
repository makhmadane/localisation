package org.yescola.livraison.web.rest;
import org.yescola.livraison.domain.Parfum;
import org.yescola.livraison.repository.ParfumRepository;
import org.yescola.livraison.repository.search.ParfumSearchRepository;
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
 * REST controller for managing Parfum.
 */
@RestController
@RequestMapping("/api")
public class ParfumResource {

    private final Logger log = LoggerFactory.getLogger(ParfumResource.class);

    private static final String ENTITY_NAME = "parfum";

    private final ParfumRepository parfumRepository;

    private final ParfumSearchRepository parfumSearchRepository;

    public ParfumResource(ParfumRepository parfumRepository, ParfumSearchRepository parfumSearchRepository) {
        this.parfumRepository = parfumRepository;
        this.parfumSearchRepository = parfumSearchRepository;
    }

    /**
     * POST  /parfums : Create a new parfum.
     *
     * @param parfum the parfum to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parfum, or with status 400 (Bad Request) if the parfum has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parfums")
    public ResponseEntity<Parfum> createParfum(@RequestBody Parfum parfum) throws URISyntaxException {
        log.debug("REST request to save Parfum : {}", parfum);
        if (parfum.getId() != null) {
            throw new BadRequestAlertException("A new parfum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Parfum result = parfumRepository.save(parfum);
        parfumSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/parfums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parfums : Updates an existing parfum.
     *
     * @param parfum the parfum to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parfum,
     * or with status 400 (Bad Request) if the parfum is not valid,
     * or with status 500 (Internal Server Error) if the parfum couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parfums")
    public ResponseEntity<Parfum> updateParfum(@RequestBody Parfum parfum) throws URISyntaxException {
        log.debug("REST request to update Parfum : {}", parfum);
        if (parfum.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Parfum result = parfumRepository.save(parfum);
        parfumSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parfum.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parfums : get all the parfums.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of parfums in body
     */
    @GetMapping("/parfums")
    public List<Parfum> getAllParfums() {
        log.debug("REST request to get all Parfums");
        return parfumRepository.findAll();
    }

    /**
     * GET  /parfums/:id : get the "id" parfum.
     *
     * @param id the id of the parfum to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parfum, or with status 404 (Not Found)
     */
    @GetMapping("/parfums/{id}")
    public ResponseEntity<Parfum> getParfum(@PathVariable Long id) {
        log.debug("REST request to get Parfum : {}", id);
        Optional<Parfum> parfum = parfumRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(parfum);
    }

    /**
     * DELETE  /parfums/:id : delete the "id" parfum.
     *
     * @param id the id of the parfum to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parfums/{id}")
    public ResponseEntity<Void> deleteParfum(@PathVariable Long id) {
        log.debug("REST request to delete Parfum : {}", id);
        parfumRepository.deleteById(id);
        parfumSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/parfums?query=:query : search for the parfum corresponding
     * to the query.
     *
     * @param query the query of the parfum search
     * @return the result of the search
     */
    @GetMapping("/_search/parfums")
    public List<Parfum> searchParfums(@RequestParam String query) {
        log.debug("REST request to search Parfums for query {}", query);
        return StreamSupport
            .stream(parfumSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
