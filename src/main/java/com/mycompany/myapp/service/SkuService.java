package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Sku;
import com.mycompany.myapp.repository.SkuRepository;
import com.mycompany.myapp.service.dto.SkuDTO;
import com.mycompany.myapp.service.mapper.SkuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Sku.
 */
@Service
@Transactional
public class SkuService {

    private final Logger log = LoggerFactory.getLogger(SkuService.class);
    
    @Inject
    private SkuRepository skuRepository;

    @Inject
    private SkuMapper skuMapper;

    /**
     * Save a sku.
     *
     * @param skuDTO the entity to save
     * @return the persisted entity
     */
    public SkuDTO save(SkuDTO skuDTO) {
        log.debug("Request to save Sku : {}", skuDTO);
        Sku sku = skuMapper.skuDTOToSku(skuDTO);
        sku = skuRepository.save(sku);
        SkuDTO result = skuMapper.skuToSkuDTO(sku);
        return result;
    }

    /**
     *  Get all the skus.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SkuDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Skus");
        Page<Sku> result = skuRepository.findAll(pageable);
        return result.map(sku -> skuMapper.skuToSkuDTO(sku));
    }

    /**
     *  Get one sku by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SkuDTO findOne(Long id) {
        log.debug("Request to get Sku : {}", id);
        Sku sku = skuRepository.findOne(id);
        SkuDTO skuDTO = skuMapper.skuToSkuDTO(sku);
        return skuDTO;
    }

    /**
     *  Delete the  sku by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Sku : {}", id);
        skuRepository.delete(id);
    }
}
