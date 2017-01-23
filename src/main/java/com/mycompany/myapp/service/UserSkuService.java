package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.UserSku;
import com.mycompany.myapp.repository.UserSkuRepository;
import com.mycompany.myapp.service.dto.UserSkuDTO;
import com.mycompany.myapp.service.mapper.UserSkuMapper;
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
 * Service Implementation for managing UserSku.
 */
@Service
@Transactional
public class UserSkuService {

    private final Logger log = LoggerFactory.getLogger(UserSkuService.class);
    
    @Inject
    private UserSkuRepository userSkuRepository;

    @Inject
    private UserSkuMapper userSkuMapper;

    /**
     * Save a userSku.
     *
     * @param userSkuDTO the entity to save
     * @return the persisted entity
     */
    public UserSkuDTO save(UserSkuDTO userSkuDTO) {
        log.debug("Request to save UserSku : {}", userSkuDTO);
        UserSku userSku = userSkuMapper.userSkuDTOToUserSku(userSkuDTO);
        userSku = userSkuRepository.save(userSku);
        UserSkuDTO result = userSkuMapper.userSkuToUserSkuDTO(userSku);
        return result;
    }

    /**
     *  Get all the userSkus.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<UserSkuDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserSkus");
        Page<UserSku> result = userSkuRepository.findAll(pageable);
        return result.map(userSku -> userSkuMapper.userSkuToUserSkuDTO(userSku));
    }

    /**
     *  Get one userSku by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public UserSkuDTO findOne(Long id) {
        log.debug("Request to get UserSku : {}", id);
        UserSku userSku = userSkuRepository.findOne(id);
        UserSkuDTO userSkuDTO = userSkuMapper.userSkuToUserSkuDTO(userSku);
        return userSkuDTO;
    }

    /**
     *  Delete the  userSku by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserSku : {}", id);
        userSkuRepository.delete(id);
    }
}
