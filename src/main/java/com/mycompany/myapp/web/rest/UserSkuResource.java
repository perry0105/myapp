package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.UserSkuService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.UserSkuDTO;

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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing UserSku.
 */
@RestController
@RequestMapping("/api")
public class UserSkuResource {

    private final Logger log = LoggerFactory.getLogger(UserSkuResource.class);
        
    @Inject
    private UserSkuService userSkuService;

    /**
     * POST  /user-skus : Create a new userSku.
     *
     * @param userSkuDTO the userSkuDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userSkuDTO, or with status 400 (Bad Request) if the userSku has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-skus")
    @Timed
    public ResponseEntity<UserSkuDTO> createUserSku(@RequestBody UserSkuDTO userSkuDTO) throws URISyntaxException {
        log.debug("REST request to save UserSku : {}", userSkuDTO);
        if (userSkuDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userSku", "idexists", "A new userSku cannot already have an ID")).body(null);
        }
        UserSkuDTO result = userSkuService.save(userSkuDTO);
        return ResponseEntity.created(new URI("/api/user-skus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userSku", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-skus : Updates an existing userSku.
     *
     * @param userSkuDTO the userSkuDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userSkuDTO,
     * or with status 400 (Bad Request) if the userSkuDTO is not valid,
     * or with status 500 (Internal Server Error) if the userSkuDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-skus")
    @Timed
    public ResponseEntity<UserSkuDTO> updateUserSku(@RequestBody UserSkuDTO userSkuDTO) throws URISyntaxException {
        log.debug("REST request to update UserSku : {}", userSkuDTO);
        if (userSkuDTO.getId() == null) {
            return createUserSku(userSkuDTO);
        }
        UserSkuDTO result = userSkuService.save(userSkuDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userSku", userSkuDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-skus : get all the userSkus.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userSkus in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-skus")
    @Timed
    public ResponseEntity<List<UserSkuDTO>> getAllUserSkus(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserSkus");
        Page<UserSkuDTO> page = userSkuService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-skus");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-skus/:id : get the "id" userSku.
     *
     * @param id the id of the userSkuDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userSkuDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-skus/{id}")
    @Timed
    public ResponseEntity<UserSkuDTO> getUserSku(@PathVariable Long id) {
        log.debug("REST request to get UserSku : {}", id);
        UserSkuDTO userSkuDTO = userSkuService.findOne(id);
        return Optional.ofNullable(userSkuDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /user-skus/:id : delete the "id" userSku.
     *
     * @param id the id of the userSkuDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-skus/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserSku(@PathVariable Long id) {
        log.debug("REST request to delete UserSku : {}", id);
        userSkuService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userSku", id.toString())).build();
    }

}
