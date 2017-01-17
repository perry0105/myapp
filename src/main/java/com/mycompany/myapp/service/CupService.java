package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Cup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Cup.
 */
public interface CupService {

    /**
     * Save a cup.
     *
     * @param cup the entity to save
     * @return the persisted entity
     */
    Cup save(Cup cup);

    /**
     *  Get all the cups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Cup> findAll(Pageable pageable);

    /**
     *  Get the "id" cup.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Cup findOne(Long id);

    /**
     *  Delete the "id" cup.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
