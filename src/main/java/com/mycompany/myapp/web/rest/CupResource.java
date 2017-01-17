package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Cup;
import com.mycompany.myapp.service.CupService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Cup.
 */
@RestController
@RequestMapping("/api")
public class CupResource {

    private final Logger log = LoggerFactory.getLogger(CupResource.class);

    @Inject
    private CupService cupService;

    /**
     * POST  /cups : Create a new cup.
     *
     * @param cup the cup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cup, or with status 400 (Bad Request) if the cup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cups")
    @Timed
    public ResponseEntity<Cup> createCup(@RequestBody Cup cup) throws URISyntaxException {
        log.debug("REST request to save Cup : {}", cup);
        if (cup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cup", "idexists", "A new cup cannot already have an ID")).body(null);
        }
        Cup result = cupService.save(cup);
        return ResponseEntity.created(new URI("/api/cups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cups : Updates an existing cup.
     *
     * @param cup the cup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cup,
     * or with status 400 (Bad Request) if the cup is not valid,
     * or with status 500 (Internal Server Error) if the cup couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cups")
    @Timed
    public ResponseEntity<Cup> updateCup(@RequestBody Cup cup) throws URISyntaxException {
        log.debug("REST request to update Cup : {}", cup);
        if (cup.getId() == null) {
            return createCup(cup);
        }
        Cup result = cupService.save(cup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cup", cup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cups : get all the cups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cups in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/cups")
    @Timed
    public ResponseEntity<List<Cup>> getAllCups(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Cups");
        Page<Cup> page = cupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cups/:id : get the "id" cup.
     *
     * @param id the id of the cup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cup, or with status 404 (Not Found)
     */
    @GetMapping("/cups/{id}")
    @Timed
    public ResponseEntity<Cup> getCup(@PathVariable Long id) {
        log.debug("REST request to get Cup : {}", id);
        Cup cup = cupService.findOne(id);
        return Optional.ofNullable(cup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cups/:id : delete the "id" cup.
     *
     * @param id the id of the cup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cups/{id}")
    @Timed
    public ResponseEntity<Void> deleteCup(@PathVariable Long id) {
        log.debug("REST request to delete Cup : {}", id);
        cupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cup", id.toString())).build();
    }

}
