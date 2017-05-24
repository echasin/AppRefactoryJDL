package com.innvo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.innvo.domain.Personpersonmbr;

import com.innvo.repository.PersonpersonmbrRepository;
import com.innvo.repository.search.PersonpersonmbrSearchRepository;
import com.innvo.web.rest.util.HeaderUtil;
import com.innvo.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Personpersonmbr.
 */
@RestController
@RequestMapping("/api")
public class PersonpersonmbrResource {

    private final Logger log = LoggerFactory.getLogger(PersonpersonmbrResource.class);

    private static final String ENTITY_NAME = "personpersonmbr";
        
    private final PersonpersonmbrRepository personpersonmbrRepository;

    private final PersonpersonmbrSearchRepository personpersonmbrSearchRepository;

    public PersonpersonmbrResource(PersonpersonmbrRepository personpersonmbrRepository, PersonpersonmbrSearchRepository personpersonmbrSearchRepository) {
        this.personpersonmbrRepository = personpersonmbrRepository;
        this.personpersonmbrSearchRepository = personpersonmbrSearchRepository;
    }

    /**
     * POST  /personpersonmbrs : Create a new personpersonmbr.
     *
     * @param personpersonmbr the personpersonmbr to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personpersonmbr, or with status 400 (Bad Request) if the personpersonmbr has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personpersonmbrs")
    @Timed
    public ResponseEntity<Personpersonmbr> createPersonpersonmbr(@Valid @RequestBody Personpersonmbr personpersonmbr) throws URISyntaxException {
        log.debug("REST request to save Personpersonmbr : {}", personpersonmbr);
        if (personpersonmbr.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new personpersonmbr cannot already have an ID")).body(null);
        }
        Personpersonmbr result = personpersonmbrRepository.save(personpersonmbr);
        personpersonmbrSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/personpersonmbrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personpersonmbrs : Updates an existing personpersonmbr.
     *
     * @param personpersonmbr the personpersonmbr to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personpersonmbr,
     * or with status 400 (Bad Request) if the personpersonmbr is not valid,
     * or with status 500 (Internal Server Error) if the personpersonmbr couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personpersonmbrs")
    @Timed
    public ResponseEntity<Personpersonmbr> updatePersonpersonmbr(@Valid @RequestBody Personpersonmbr personpersonmbr) throws URISyntaxException {
        log.debug("REST request to update Personpersonmbr : {}", personpersonmbr);
        if (personpersonmbr.getId() == null) {
            return createPersonpersonmbr(personpersonmbr);
        }
        Personpersonmbr result = personpersonmbrRepository.save(personpersonmbr);
        personpersonmbrSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personpersonmbr.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personpersonmbrs : get all the personpersonmbrs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personpersonmbrs in body
     */
    @GetMapping("/personpersonmbrs")
    @Timed
    public ResponseEntity<List<Personpersonmbr>> getAllPersonpersonmbrs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Personpersonmbrs");
        Page<Personpersonmbr> page = personpersonmbrRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/personpersonmbrs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /personpersonmbrs/:id : get the "id" personpersonmbr.
     *
     * @param id the id of the personpersonmbr to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personpersonmbr, or with status 404 (Not Found)
     */
    @GetMapping("/personpersonmbrs/{id}")
    @Timed
    public ResponseEntity<Personpersonmbr> getPersonpersonmbr(@PathVariable Long id) {
        log.debug("REST request to get Personpersonmbr : {}", id);
        Personpersonmbr personpersonmbr = personpersonmbrRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personpersonmbr));
    }

    /**
     * DELETE  /personpersonmbrs/:id : delete the "id" personpersonmbr.
     *
     * @param id the id of the personpersonmbr to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personpersonmbrs/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonpersonmbr(@PathVariable Long id) {
        log.debug("REST request to delete Personpersonmbr : {}", id);
        personpersonmbrRepository.delete(id);
        personpersonmbrSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/personpersonmbrs?query=:query : search for the personpersonmbr corresponding
     * to the query.
     *
     * @param query the query of the personpersonmbr search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/personpersonmbrs")
    @Timed
    public ResponseEntity<List<Personpersonmbr>> searchPersonpersonmbrs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Personpersonmbrs for query {}", query);
        Page<Personpersonmbr> page = personpersonmbrSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/personpersonmbrs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
