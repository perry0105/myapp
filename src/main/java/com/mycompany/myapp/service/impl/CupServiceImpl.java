package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Book;
import com.mycompany.myapp.domain.Cup;
import com.mycompany.myapp.repository.BookRepository;
import com.mycompany.myapp.repository.CupRepository;
import com.mycompany.myapp.service.BookService;
import com.mycompany.myapp.service.CupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service Implementation for managing Cup.
 */
@Service
@Transactional
public class CupServiceImpl implements CupService {

    private final Logger log = LoggerFactory.getLogger(CupServiceImpl.class);

    @Inject
    private CupRepository cupRepository;

    /**
     * Save a cup.
     *
     * @param cup the entity to save
     * @return the persisted entity
     */
    public Cup save(Cup cup) {
        log.debug("Request to save Cup : {}", cup);
        Cup result = cupRepository.save(cup);
        return result;
    }

    /**
     *  Get all the cups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Cup> findAll(Pageable pageable) {
        log.debug("Request to get all Cups");
        Page<Cup> result = cupRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one cup by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Cup findOne(Long id) {
        log.debug("Request to get Cup : {}", id);
        Cup cup = cupRepository.findOne(id);
        return cup;
    }

    /**
     *  Delete the  cup by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Cup : {}", id);
        cupRepository.delete(id);
    }
}
