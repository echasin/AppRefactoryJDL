package com.innvo.web.rest;

import com.innvo.ApprefactoryApp;

import com.innvo.domain.Assetstatus;
import com.innvo.repository.AssetstatusRepository;
import com.innvo.repository.search.AssetstatusSearchRepository;
import com.innvo.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AssetstatusResource REST controller.
 *
 * @see AssetstatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApprefactoryApp.class)
public class AssetstatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAMESHORT = "AAAAAAAAAA";
    private static final String UPDATED_NAMESHORT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AssetstatusRepository assetstatusRepository;

    @Autowired
    private AssetstatusSearchRepository assetstatusSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssetstatusMockMvc;

    private Assetstatus assetstatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssetstatusResource assetstatusResource = new AssetstatusResource(assetstatusRepository, assetstatusSearchRepository);
        this.restAssetstatusMockMvc = MockMvcBuilders.standaloneSetup(assetstatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assetstatus createEntity(EntityManager em) {
        Assetstatus assetstatus = new Assetstatus()
            .name(DEFAULT_NAME)
            .nameshort(DEFAULT_NAMESHORT)
            .description(DEFAULT_DESCRIPTION);
        return assetstatus;
    }

    @Before
    public void initTest() {
        assetstatusSearchRepository.deleteAll();
        assetstatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssetstatus() throws Exception {
        int databaseSizeBeforeCreate = assetstatusRepository.findAll().size();

        // Create the Assetstatus
        restAssetstatusMockMvc.perform(post("/api/assetstatuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetstatus)))
            .andExpect(status().isCreated());

        // Validate the Assetstatus in the database
        List<Assetstatus> assetstatusList = assetstatusRepository.findAll();
        assertThat(assetstatusList).hasSize(databaseSizeBeforeCreate + 1);
        Assetstatus testAssetstatus = assetstatusList.get(assetstatusList.size() - 1);
        assertThat(testAssetstatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAssetstatus.getNameshort()).isEqualTo(DEFAULT_NAMESHORT);
        assertThat(testAssetstatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Assetstatus in Elasticsearch
        Assetstatus assetstatusEs = assetstatusSearchRepository.findOne(testAssetstatus.getId());
        assertThat(assetstatusEs).isEqualToComparingFieldByField(testAssetstatus);
    }

    @Test
    @Transactional
    public void createAssetstatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assetstatusRepository.findAll().size();

        // Create the Assetstatus with an existing ID
        assetstatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetstatusMockMvc.perform(post("/api/assetstatuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetstatus)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Assetstatus> assetstatusList = assetstatusRepository.findAll();
        assertThat(assetstatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAssetstatuses() throws Exception {
        // Initialize the database
        assetstatusRepository.saveAndFlush(assetstatus);

        // Get all the assetstatusList
        restAssetstatusMockMvc.perform(get("/api/assetstatuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetstatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].nameshort").value(hasItem(DEFAULT_NAMESHORT.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getAssetstatus() throws Exception {
        // Initialize the database
        assetstatusRepository.saveAndFlush(assetstatus);

        // Get the assetstatus
        restAssetstatusMockMvc.perform(get("/api/assetstatuses/{id}", assetstatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assetstatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.nameshort").value(DEFAULT_NAMESHORT.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAssetstatus() throws Exception {
        // Get the assetstatus
        restAssetstatusMockMvc.perform(get("/api/assetstatuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetstatus() throws Exception {
        // Initialize the database
        assetstatusRepository.saveAndFlush(assetstatus);
        assetstatusSearchRepository.save(assetstatus);
        int databaseSizeBeforeUpdate = assetstatusRepository.findAll().size();

        // Update the assetstatus
        Assetstatus updatedAssetstatus = assetstatusRepository.findOne(assetstatus.getId());
        updatedAssetstatus
            .name(UPDATED_NAME)
            .nameshort(UPDATED_NAMESHORT)
            .description(UPDATED_DESCRIPTION);

        restAssetstatusMockMvc.perform(put("/api/assetstatuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAssetstatus)))
            .andExpect(status().isOk());

        // Validate the Assetstatus in the database
        List<Assetstatus> assetstatusList = assetstatusRepository.findAll();
        assertThat(assetstatusList).hasSize(databaseSizeBeforeUpdate);
        Assetstatus testAssetstatus = assetstatusList.get(assetstatusList.size() - 1);
        assertThat(testAssetstatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAssetstatus.getNameshort()).isEqualTo(UPDATED_NAMESHORT);
        assertThat(testAssetstatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Assetstatus in Elasticsearch
        Assetstatus assetstatusEs = assetstatusSearchRepository.findOne(testAssetstatus.getId());
        assertThat(assetstatusEs).isEqualToComparingFieldByField(testAssetstatus);
    }

    @Test
    @Transactional
    public void updateNonExistingAssetstatus() throws Exception {
        int databaseSizeBeforeUpdate = assetstatusRepository.findAll().size();

        // Create the Assetstatus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssetstatusMockMvc.perform(put("/api/assetstatuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetstatus)))
            .andExpect(status().isCreated());

        // Validate the Assetstatus in the database
        List<Assetstatus> assetstatusList = assetstatusRepository.findAll();
        assertThat(assetstatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAssetstatus() throws Exception {
        // Initialize the database
        assetstatusRepository.saveAndFlush(assetstatus);
        assetstatusSearchRepository.save(assetstatus);
        int databaseSizeBeforeDelete = assetstatusRepository.findAll().size();

        // Get the assetstatus
        restAssetstatusMockMvc.perform(delete("/api/assetstatuses/{id}", assetstatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean assetstatusExistsInEs = assetstatusSearchRepository.exists(assetstatus.getId());
        assertThat(assetstatusExistsInEs).isFalse();

        // Validate the database is empty
        List<Assetstatus> assetstatusList = assetstatusRepository.findAll();
        assertThat(assetstatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAssetstatus() throws Exception {
        // Initialize the database
        assetstatusRepository.saveAndFlush(assetstatus);
        assetstatusSearchRepository.save(assetstatus);

        // Search the assetstatus
        restAssetstatusMockMvc.perform(get("/api/_search/assetstatuses?query=id:" + assetstatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetstatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].nameshort").value(hasItem(DEFAULT_NAMESHORT.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Assetstatus.class);
        Assetstatus assetstatus1 = new Assetstatus();
        assetstatus1.setId(1L);
        Assetstatus assetstatus2 = new Assetstatus();
        assetstatus2.setId(assetstatus1.getId());
        assertThat(assetstatus1).isEqualTo(assetstatus2);
        assetstatus2.setId(2L);
        assertThat(assetstatus1).isNotEqualTo(assetstatus2);
        assetstatus1.setId(null);
        assertThat(assetstatus1).isNotEqualTo(assetstatus2);
    }
}
