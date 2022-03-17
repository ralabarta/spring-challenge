package com.mytest.mytestapp.web.rest;

import com.mytest.mytestapp.repository.AppRoleRepository;
import com.mytest.mytestapp.service.AppRoleService;
import com.mytest.mytestapp.service.dto.AppRoleDTO;
import com.mytest.mytestapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mytest.mytestapp.domain.AppRole}.
 */
@RestController
@RequestMapping("/api")
public class AppRoleResource {

    private final Logger log = LoggerFactory.getLogger(AppRoleResource.class);

    private static final String ENTITY_NAME = "appRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppRoleService appRoleService;

    private final AppRoleRepository appRoleRepository;

    public AppRoleResource(AppRoleService appRoleService, AppRoleRepository appRoleRepository) {
        this.appRoleService = appRoleService;
        this.appRoleRepository = appRoleRepository;
    }

    /**
     * {@code POST  /app-roles} : Create a new appRole.
     *
     * @param appRoleDTO the appRoleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appRoleDTO, or with status {@code 400 (Bad Request)} if the appRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-roles")
    public ResponseEntity<AppRoleDTO> createAppRole(@Valid @RequestBody AppRoleDTO appRoleDTO) throws URISyntaxException {
        log.debug("REST request to save AppRole : {}", appRoleDTO);
        if (appRoleDTO.getId() != null) {
            throw new BadRequestAlertException("A new appRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppRoleDTO result = appRoleService.save(appRoleDTO);
        return ResponseEntity
            .created(new URI("/api/app-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-roles/:id} : Updates an existing appRole.
     *
     * @param id the id of the appRoleDTO to save.
     * @param appRoleDTO the appRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appRoleDTO,
     * or with status {@code 400 (Bad Request)} if the appRoleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-roles/{id}")
    public ResponseEntity<AppRoleDTO> updateAppRole(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AppRoleDTO appRoleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppRole : {}, {}", id, appRoleDTO);
        if (appRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appRoleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appRoleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppRoleDTO result = appRoleService.save(appRoleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appRoleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-roles/:id} : Partial updates given fields of an existing appRole, field will ignore if it is null
     *
     * @param id the id of the appRoleDTO to save.
     * @param appRoleDTO the appRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appRoleDTO,
     * or with status {@code 400 (Bad Request)} if the appRoleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appRoleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-roles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppRoleDTO> partialUpdateAppRole(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AppRoleDTO appRoleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppRole partially : {}, {}", id, appRoleDTO);
        if (appRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appRoleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appRoleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppRoleDTO> result = appRoleService.partialUpdate(appRoleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appRoleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-roles} : get all the appRoles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appRoles in body.
     */
    @GetMapping("/app-roles")
    public ResponseEntity<List<AppRoleDTO>> getAllAppRoles(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AppRoles");
        Page<AppRoleDTO> page = appRoleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /app-roles/:id} : get the "id" appRole.
     *
     * @param id the id of the appRoleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appRoleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-roles/{id}")
    public ResponseEntity<AppRoleDTO> getAppRole(@PathVariable Long id) {
        log.debug("REST request to get AppRole : {}", id);
        Optional<AppRoleDTO> appRoleDTO = appRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appRoleDTO);
    }

    /**
     * {@code DELETE  /app-roles/:id} : delete the "id" appRole.
     *
     * @param id the id of the appRoleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-roles/{id}")
    public ResponseEntity<Void> deleteAppRole(@PathVariable Long id) {
        log.debug("REST request to delete AppRole : {}", id);
        appRoleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
