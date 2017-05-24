package com.innvo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.innvo.domain.Assetstatus;

import com.innvo.repository.AssetstatusRepository;
import com.innvo.repository.search.AssetstatusSearchRepository;
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
 * REST controller for managing Assetstatus.
 */
@RestController
@RequestMapping("/api")
public class AssetstatusResource {

    private final Logger log = LoggerFactory.getLogger(AssetstatusResource.class);

    private static final String ENTITY_NAME = "assetstatus";
        
    private final AssetstatusRepository assetstatusRepository;

    private final AssetstatusSearchRepository assetstatusSearchRepository;

    public AssetstatusResource(AssetstatusRepository assetstatusRepository, AssetstatusSearchRepository assetstatusSearchRepository) {
        this.assetstatusRepository = assetstatusRepository;
        this.assetstatusSearchRepository = assetstatusSearchRepository;
    }

    /**
     * POST  /assetstatuses : Create a new assetstatus.
     *
     * @param assetstatus the assetstatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assetstatus, or with status 400 (Bad Request) if the assetstatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assetstatuses")
    @Timed
    public ResponseEntity<Assetstatus> createAssetstatus(@Valid @RequestBody Assetstatus assetstatus) throws URISyntaxException {
        log.debug("REST request to save Assetstatus : {}", assetstatus);
        if (assetstatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new assetstatus cannot already have an ID")).body(null);
        }
        Assetstatus result = assetstatusRepository.save(assetstatus);
        assetstatusSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/assetstatuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assetstatuses : Updates an existing assetstatus.
     *
     * @param assetstatus the assetstatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assetstatus,
     * or with status 400 (Bad Request) if the assetstatus is not valid,
     * or with status 500 (Internal Server Error) if the assetstatus couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/assetstatuses")
    @Timed
    public ResponseEntity<Assetstatus> updateAssetstatus(@Valid @RequestBody Assetstatus assetstatus) throws URISyntaxException {
        log.debug("REST request to update Assetstatus : {}", assetstatus);
        if (assetstatus.getId() == null) {
            return createAssetstatus(assetstatus);
        }
        Assetstatus result = assetstatusRepository.save(assetstatus);
        assetstatusSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, assetstatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assetstatuses : get all the assetstatuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of assetstatuses in body
     */
    @GetMapping("/assetstatuses")
    @Timed
    public ResponseEntity<List<Assetstatus>> getAllAssetstatuses(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Assetstatuses");
        Page<Assetstatus> page = assetstatusRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assetstatuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assetstatuses/:id : get the "id" assetstatus.
     *
     * @param id the id of the assetstatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assetstatus, or with status 404 (Not Found)
     */
    @GetMapping("/assetstatuses/{id}")
    @Timed
    public ResponseEntity<Assetstatus> getAssetstatus(@PathVariable Long id) {
        log.debug("REST request to get Assetstatus : {}", id);
        Assetstatus assetstatus = assetstatusRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(assetstatus));
    }

    /**
     * DELETE  /assetstatuses/:id : delete the "id" assetstatus.
     *
     * @param id the id of the assetstatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/assetstatuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssetstatus(@PathVariable Long id) {
        log.debug("REST request to delete Assetstatus : {}", id);
        assetstatusRepository.delete(id);
        assetstatusSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/assetstatuses?query=:query : search for the assetstatus corresponding
     * to the query.
     *
     * @param query the query of the assetstatus search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/assetstatuses")
    @Timed
    public ResponseEntity<List<Assetstatus>> searchAssetstatuses(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Assetstatuses for query {}", query);
        Page<Assetstatus> page = assetstatusSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/assetstatuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
