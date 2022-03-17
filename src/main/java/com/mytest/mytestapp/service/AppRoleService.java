package com.mytest.mytestapp.service;

import com.mytest.mytestapp.service.dto.AppRoleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mytest.mytestapp.domain.AppRole}.
 */
public interface AppRoleService {
    /**
     * Save a appRole.
     *
     * @param appRoleDTO the entity to save.
     * @return the persisted entity.
     */
    AppRoleDTO save(AppRoleDTO appRoleDTO);

    /**
     * Partially updates a appRole.
     *
     * @param appRoleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppRoleDTO> partialUpdate(AppRoleDTO appRoleDTO);

    /**
     * Get all the appRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AppRoleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" appRole.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppRoleDTO> findOne(Long id);

    /**
     * Delete the "id" appRole.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
