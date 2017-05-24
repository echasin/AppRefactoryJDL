package com.innvo.web.rest;

import com.innvo.ApprefactoryApp;

import com.innvo.domain.Personpersonmbr;
import com.innvo.domain.Person;
import com.innvo.repository.PersonpersonmbrRepository;
import com.innvo.repository.search.PersonpersonmbrSearchRepository;
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
 * Test class for the PersonpersonmbrResource REST controller.
 *
 * @see PersonpersonmbrResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApprefactoryApp.class)
public class PersonpersonmbrResourceIntTest {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private PersonpersonmbrRepository personpersonmbrRepository;

    @Autowired
    private PersonpersonmbrSearchRepository personpersonmbrSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonpersonmbrMockMvc;

    private Personpersonmbr personpersonmbr;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonpersonmbrResource personpersonmbrResource = new PersonpersonmbrResource(personpersonmbrRepository, personpersonmbrSearchRepository);
        this.restPersonpersonmbrMockMvc = MockMvcBuilders.standaloneSetup(personpersonmbrResource)
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
    public static Personpersonmbr createEntity(EntityManager em) {
        Personpersonmbr personpersonmbr = new Personpersonmbr()
            .comment(DEFAULT_COMMENT);
        // Add required entity
        Person parentmbr = PersonResourceIntTest.createEntity(em);
        em.persist(parentmbr);
        em.flush();
        personpersonmbr.setParentmbr(parentmbr);
        return personpersonmbr;
    }

    @Before
    public void initTest() {
        personpersonmbrSearchRepository.deleteAll();
        personpersonmbr = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonpersonmbr() throws Exception {
        int databaseSizeBeforeCreate = personpersonmbrRepository.findAll().size();

        // Create the Personpersonmbr
        restPersonpersonmbrMockMvc.perform(post("/api/personpersonmbrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personpersonmbr)))
            .andExpect(status().isCreated());

        // Validate the Personpersonmbr in the database
        List<Personpersonmbr> personpersonmbrList = personpersonmbrRepository.findAll();
        assertThat(personpersonmbrList).hasSize(databaseSizeBeforeCreate + 1);
        Personpersonmbr testPersonpersonmbr = personpersonmbrList.get(personpersonmbrList.size() - 1);
        assertThat(testPersonpersonmbr.getComment()).isEqualTo(DEFAULT_COMMENT);

        // Validate the Personpersonmbr in Elasticsearch
        Personpersonmbr personpersonmbrEs = personpersonmbrSearchRepository.findOne(testPersonpersonmbr.getId());
        assertThat(personpersonmbrEs).isEqualToComparingFieldByField(testPersonpersonmbr);
    }

    @Test
    @Transactional
    public void createPersonpersonmbrWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personpersonmbrRepository.findAll().size();

        // Create the Personpersonmbr with an existing ID
        personpersonmbr.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonpersonmbrMockMvc.perform(post("/api/personpersonmbrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personpersonmbr)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Personpersonmbr> personpersonmbrList = personpersonmbrRepository.findAll();
        assertThat(personpersonmbrList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonpersonmbrs() throws Exception {
        // Initialize the database
        personpersonmbrRepository.saveAndFlush(personpersonmbr);

        // Get all the personpersonmbrList
        restPersonpersonmbrMockMvc.perform(get("/api/personpersonmbrs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personpersonmbr.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getPersonpersonmbr() throws Exception {
        // Initialize the database
        personpersonmbrRepository.saveAndFlush(personpersonmbr);

        // Get the personpersonmbr
        restPersonpersonmbrMockMvc.perform(get("/api/personpersonmbrs/{id}", personpersonmbr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personpersonmbr.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonpersonmbr() throws Exception {
        // Get the personpersonmbr
        restPersonpersonmbrMockMvc.perform(get("/api/personpersonmbrs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonpersonmbr() throws Exception {
        // Initialize the database
        personpersonmbrRepository.saveAndFlush(personpersonmbr);
        personpersonmbrSearchRepository.save(personpersonmbr);
        int databaseSizeBeforeUpdate = personpersonmbrRepository.findAll().size();

        // Update the personpersonmbr
        Personpersonmbr updatedPersonpersonmbr = personpersonmbrRepository.findOne(personpersonmbr.getId());
        updatedPersonpersonmbr
            .comment(UPDATED_COMMENT);

        restPersonpersonmbrMockMvc.perform(put("/api/personpersonmbrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonpersonmbr)))
            .andExpect(status().isOk());

        // Validate the Personpersonmbr in the database
        List<Personpersonmbr> personpersonmbrList = personpersonmbrRepository.findAll();
        assertThat(personpersonmbrList).hasSize(databaseSizeBeforeUpdate);
        Personpersonmbr testPersonpersonmbr = personpersonmbrList.get(personpersonmbrList.size() - 1);
        assertThat(testPersonpersonmbr.getComment()).isEqualTo(UPDATED_COMMENT);

        // Validate the Personpersonmbr in Elasticsearch
        Personpersonmbr personpersonmbrEs = personpersonmbrSearchRepository.findOne(testPersonpersonmbr.getId());
        assertThat(personpersonmbrEs).isEqualToComparingFieldByField(testPersonpersonmbr);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonpersonmbr() throws Exception {
        int databaseSizeBeforeUpdate = personpersonmbrRepository.findAll().size();

        // Create the Personpersonmbr

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonpersonmbrMockMvc.perform(put("/api/personpersonmbrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personpersonmbr)))
            .andExpect(status().isCreated());

        // Validate the Personpersonmbr in the database
        List<Personpersonmbr> personpersonmbrList = personpersonmbrRepository.findAll();
        assertThat(personpersonmbrList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonpersonmbr() throws Exception {
        // Initialize the database
        personpersonmbrRepository.saveAndFlush(personpersonmbr);
        personpersonmbrSearchRepository.save(personpersonmbr);
        int databaseSizeBeforeDelete = personpersonmbrRepository.findAll().size();

        // Get the personpersonmbr
        restPersonpersonmbrMockMvc.perform(delete("/api/personpersonmbrs/{id}", personpersonmbr.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean personpersonmbrExistsInEs = personpersonmbrSearchRepository.exists(personpersonmbr.getId());
        assertThat(personpersonmbrExistsInEs).isFalse();

        // Validate the database is empty
        List<Personpersonmbr> personpersonmbrList = personpersonmbrRepository.findAll();
        assertThat(personpersonmbrList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPersonpersonmbr() throws Exception {
        // Initialize the database
        personpersonmbrRepository.saveAndFlush(personpersonmbr);
        personpersonmbrSearchRepository.save(personpersonmbr);

        // Search the personpersonmbr
        restPersonpersonmbrMockMvc.perform(get("/api/_search/personpersonmbrs?query=id:" + personpersonmbr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personpersonmbr.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Personpersonmbr.class);
        Personpersonmbr personpersonmbr1 = new Personpersonmbr();
        personpersonmbr1.setId(1L);
        Personpersonmbr personpersonmbr2 = new Personpersonmbr();
        personpersonmbr2.setId(personpersonmbr1.getId());
        assertThat(personpersonmbr1).isEqualTo(personpersonmbr2);
        personpersonmbr2.setId(2L);
        assertThat(personpersonmbr1).isNotEqualTo(personpersonmbr2);
        personpersonmbr1.setId(null);
        assertThat(personpersonmbr1).isNotEqualTo(personpersonmbr2);
    }
}
