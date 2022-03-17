package com.mytest.mytestapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mytest.mytestapp.IntegrationTest;
import com.mytest.mytestapp.domain.AppRole;
import com.mytest.mytestapp.repository.AppRoleRepository;
import com.mytest.mytestapp.service.dto.AppRoleDTO;
import com.mytest.mytestapp.service.mapper.AppRoleMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AppRoleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppRoleResourceIT {

    private static final String DEFAULT_ROLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-roles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppRoleRepository appRoleRepository;

    @Autowired
    private AppRoleMapper appRoleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppRoleMockMvc;

    private AppRole appRole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppRole createEntity(EntityManager em) {
        AppRole appRole = new AppRole().roleName(DEFAULT_ROLE_NAME);
        return appRole;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppRole createUpdatedEntity(EntityManager em) {
        AppRole appRole = new AppRole().roleName(UPDATED_ROLE_NAME);
        return appRole;
    }

    @BeforeEach
    public void initTest() {
        appRole = createEntity(em);
    }

    @Test
    @Transactional
    void createAppRole() throws Exception {
        int databaseSizeBeforeCreate = appRoleRepository.findAll().size();
        // Create the AppRole
        AppRoleDTO appRoleDTO = appRoleMapper.toDto(appRole);
        restAppRoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appRoleDTO)))
            .andExpect(status().isCreated());

        // Validate the AppRole in the database
        List<AppRole> appRoleList = appRoleRepository.findAll();
        assertThat(appRoleList).hasSize(databaseSizeBeforeCreate + 1);
        AppRole testAppRole = appRoleList.get(appRoleList.size() - 1);
        assertThat(testAppRole.getRoleName()).isEqualTo(DEFAULT_ROLE_NAME);
    }

    @Test
    @Transactional
    void createAppRoleWithExistingId() throws Exception {
        // Create the AppRole with an existing ID
        appRole.setId(1L);
        AppRoleDTO appRoleDTO = appRoleMapper.toDto(appRole);

        int databaseSizeBeforeCreate = appRoleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppRoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppRole in the database
        List<AppRole> appRoleList = appRoleRepository.findAll();
        assertThat(appRoleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRoleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = appRoleRepository.findAll().size();
        // set the field null
        appRole.setRoleName(null);

        // Create the AppRole, which fails.
        AppRoleDTO appRoleDTO = appRoleMapper.toDto(appRole);

        restAppRoleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appRoleDTO)))
            .andExpect(status().isBadRequest());

        List<AppRole> appRoleList = appRoleRepository.findAll();
        assertThat(appRoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAppRoles() throws Exception {
        // Initialize the database
        appRoleRepository.saveAndFlush(appRole);

        // Get all the appRoleList
        restAppRoleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME)));
    }

    @Test
    @Transactional
    void getAppRole() throws Exception {
        // Initialize the database
        appRoleRepository.saveAndFlush(appRole);

        // Get the appRole
        restAppRoleMockMvc
            .perform(get(ENTITY_API_URL_ID, appRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appRole.getId().intValue()))
            .andExpect(jsonPath("$.roleName").value(DEFAULT_ROLE_NAME));
    }

    @Test
    @Transactional
    void getNonExistingAppRole() throws Exception {
        // Get the appRole
        restAppRoleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAppRole() throws Exception {
        // Initialize the database
        appRoleRepository.saveAndFlush(appRole);

        int databaseSizeBeforeUpdate = appRoleRepository.findAll().size();

        // Update the appRole
        AppRole updatedAppRole = appRoleRepository.findById(appRole.getId()).get();
        // Disconnect from session so that the updates on updatedAppRole are not directly saved in db
        em.detach(updatedAppRole);
        updatedAppRole.roleName(UPDATED_ROLE_NAME);
        AppRoleDTO appRoleDTO = appRoleMapper.toDto(updatedAppRole);

        restAppRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appRoleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appRoleDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppRole in the database
        List<AppRole> appRoleList = appRoleRepository.findAll();
        assertThat(appRoleList).hasSize(databaseSizeBeforeUpdate);
        AppRole testAppRole = appRoleList.get(appRoleList.size() - 1);
        assertThat(testAppRole.getRoleName()).isEqualTo(UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    void putNonExistingAppRole() throws Exception {
        int databaseSizeBeforeUpdate = appRoleRepository.findAll().size();
        appRole.setId(count.incrementAndGet());

        // Create the AppRole
        AppRoleDTO appRoleDTO = appRoleMapper.toDto(appRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appRoleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppRole in the database
        List<AppRole> appRoleList = appRoleRepository.findAll();
        assertThat(appRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppRole() throws Exception {
        int databaseSizeBeforeUpdate = appRoleRepository.findAll().size();
        appRole.setId(count.incrementAndGet());

        // Create the AppRole
        AppRoleDTO appRoleDTO = appRoleMapper.toDto(appRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppRole in the database
        List<AppRole> appRoleList = appRoleRepository.findAll();
        assertThat(appRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppRole() throws Exception {
        int databaseSizeBeforeUpdate = appRoleRepository.findAll().size();
        appRole.setId(count.incrementAndGet());

        // Create the AppRole
        AppRoleDTO appRoleDTO = appRoleMapper.toDto(appRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppRoleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appRoleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppRole in the database
        List<AppRole> appRoleList = appRoleRepository.findAll();
        assertThat(appRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppRoleWithPatch() throws Exception {
        // Initialize the database
        appRoleRepository.saveAndFlush(appRole);

        int databaseSizeBeforeUpdate = appRoleRepository.findAll().size();

        // Update the appRole using partial update
        AppRole partialUpdatedAppRole = new AppRole();
        partialUpdatedAppRole.setId(appRole.getId());

        partialUpdatedAppRole.roleName(UPDATED_ROLE_NAME);

        restAppRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppRole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppRole))
            )
            .andExpect(status().isOk());

        // Validate the AppRole in the database
        List<AppRole> appRoleList = appRoleRepository.findAll();
        assertThat(appRoleList).hasSize(databaseSizeBeforeUpdate);
        AppRole testAppRole = appRoleList.get(appRoleList.size() - 1);
        assertThat(testAppRole.getRoleName()).isEqualTo(UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    void fullUpdateAppRoleWithPatch() throws Exception {
        // Initialize the database
        appRoleRepository.saveAndFlush(appRole);

        int databaseSizeBeforeUpdate = appRoleRepository.findAll().size();

        // Update the appRole using partial update
        AppRole partialUpdatedAppRole = new AppRole();
        partialUpdatedAppRole.setId(appRole.getId());

        partialUpdatedAppRole.roleName(UPDATED_ROLE_NAME);

        restAppRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppRole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppRole))
            )
            .andExpect(status().isOk());

        // Validate the AppRole in the database
        List<AppRole> appRoleList = appRoleRepository.findAll();
        assertThat(appRoleList).hasSize(databaseSizeBeforeUpdate);
        AppRole testAppRole = appRoleList.get(appRoleList.size() - 1);
        assertThat(testAppRole.getRoleName()).isEqualTo(UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingAppRole() throws Exception {
        int databaseSizeBeforeUpdate = appRoleRepository.findAll().size();
        appRole.setId(count.incrementAndGet());

        // Create the AppRole
        AppRoleDTO appRoleDTO = appRoleMapper.toDto(appRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appRoleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppRole in the database
        List<AppRole> appRoleList = appRoleRepository.findAll();
        assertThat(appRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppRole() throws Exception {
        int databaseSizeBeforeUpdate = appRoleRepository.findAll().size();
        appRole.setId(count.incrementAndGet());

        // Create the AppRole
        AppRoleDTO appRoleDTO = appRoleMapper.toDto(appRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appRoleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppRole in the database
        List<AppRole> appRoleList = appRoleRepository.findAll();
        assertThat(appRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppRole() throws Exception {
        int databaseSizeBeforeUpdate = appRoleRepository.findAll().size();
        appRole.setId(count.incrementAndGet());

        // Create the AppRole
        AppRoleDTO appRoleDTO = appRoleMapper.toDto(appRole);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppRoleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(appRoleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppRole in the database
        List<AppRole> appRoleList = appRoleRepository.findAll();
        assertThat(appRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppRole() throws Exception {
        // Initialize the database
        appRoleRepository.saveAndFlush(appRole);

        int databaseSizeBeforeDelete = appRoleRepository.findAll().size();

        // Delete the appRole
        restAppRoleMockMvc
            .perform(delete(ENTITY_API_URL_ID, appRole.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppRole> appRoleList = appRoleRepository.findAll();
        assertThat(appRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
