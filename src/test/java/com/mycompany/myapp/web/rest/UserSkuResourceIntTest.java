package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MyappApp;

import com.mycompany.myapp.domain.UserSku;
import com.mycompany.myapp.repository.UserSkuRepository;
import com.mycompany.myapp.service.UserSkuService;
import com.mycompany.myapp.service.dto.UserSkuDTO;
import com.mycompany.myapp.service.mapper.UserSkuMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserSkuResource REST controller.
 *
 * @see UserSkuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class UserSkuResourceIntTest {

    @Inject
    private UserSkuRepository userSkuRepository;

    @Inject
    private UserSkuMapper userSkuMapper;

    @Inject
    private UserSkuService userSkuService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUserSkuMockMvc;

    private UserSku userSku;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserSkuResource userSkuResource = new UserSkuResource();
        ReflectionTestUtils.setField(userSkuResource, "userSkuService", userSkuService);
        this.restUserSkuMockMvc = MockMvcBuilders.standaloneSetup(userSkuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSku createEntity(EntityManager em) {
        UserSku userSku = new UserSku();
        return userSku;
    }

    @Before
    public void initTest() {
        userSku = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserSku() throws Exception {
        int databaseSizeBeforeCreate = userSkuRepository.findAll().size();

        // Create the UserSku
        UserSkuDTO userSkuDTO = userSkuMapper.userSkuToUserSkuDTO(userSku);

        restUserSkuMockMvc.perform(post("/api/user-skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userSkuDTO)))
            .andExpect(status().isCreated());

        // Validate the UserSku in the database
        List<UserSku> userSkuList = userSkuRepository.findAll();
        assertThat(userSkuList).hasSize(databaseSizeBeforeCreate + 1);
        UserSku testUserSku = userSkuList.get(userSkuList.size() - 1);
    }

    @Test
    @Transactional
    public void createUserSkuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userSkuRepository.findAll().size();

        // Create the UserSku with an existing ID
        UserSku existingUserSku = new UserSku();
        existingUserSku.setId(1L);
        UserSkuDTO existingUserSkuDTO = userSkuMapper.userSkuToUserSkuDTO(existingUserSku);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserSkuMockMvc.perform(post("/api/user-skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserSkuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserSku> userSkuList = userSkuRepository.findAll();
        assertThat(userSkuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserSkus() throws Exception {
        // Initialize the database
        userSkuRepository.saveAndFlush(userSku);

        // Get all the userSkuList
        restUserSkuMockMvc.perform(get("/api/user-skus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userSku.getId().intValue())));
    }

    @Test
    @Transactional
    public void getUserSku() throws Exception {
        // Initialize the database
        userSkuRepository.saveAndFlush(userSku);

        // Get the userSku
        restUserSkuMockMvc.perform(get("/api/user-skus/{id}", userSku.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userSku.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserSku() throws Exception {
        // Get the userSku
        restUserSkuMockMvc.perform(get("/api/user-skus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserSku() throws Exception {
        // Initialize the database
        userSkuRepository.saveAndFlush(userSku);
        int databaseSizeBeforeUpdate = userSkuRepository.findAll().size();

        // Update the userSku
        UserSku updatedUserSku = userSkuRepository.findOne(userSku.getId());
        UserSkuDTO userSkuDTO = userSkuMapper.userSkuToUserSkuDTO(updatedUserSku);

        restUserSkuMockMvc.perform(put("/api/user-skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userSkuDTO)))
            .andExpect(status().isOk());

        // Validate the UserSku in the database
        List<UserSku> userSkuList = userSkuRepository.findAll();
        assertThat(userSkuList).hasSize(databaseSizeBeforeUpdate);
        UserSku testUserSku = userSkuList.get(userSkuList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingUserSku() throws Exception {
        int databaseSizeBeforeUpdate = userSkuRepository.findAll().size();

        // Create the UserSku
        UserSkuDTO userSkuDTO = userSkuMapper.userSkuToUserSkuDTO(userSku);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserSkuMockMvc.perform(put("/api/user-skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userSkuDTO)))
            .andExpect(status().isCreated());

        // Validate the UserSku in the database
        List<UserSku> userSkuList = userSkuRepository.findAll();
        assertThat(userSkuList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserSku() throws Exception {
        // Initialize the database
        userSkuRepository.saveAndFlush(userSku);
        int databaseSizeBeforeDelete = userSkuRepository.findAll().size();

        // Get the userSku
        restUserSkuMockMvc.perform(delete("/api/user-skus/{id}", userSku.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserSku> userSkuList = userSkuRepository.findAll();
        assertThat(userSkuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
