package com.mytest.mytestapp.service.impl;

import com.mytest.mytestapp.domain.AppRole;
import com.mytest.mytestapp.repository.AppRoleRepository;
import com.mytest.mytestapp.service.AppRoleService;
import com.mytest.mytestapp.service.dto.AppRoleDTO;
import com.mytest.mytestapp.service.mapper.AppRoleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppRole}.
 */
@Service
@Transactional
public class AppRoleServiceImpl implements AppRoleService {

    private final Logger log = LoggerFactory.getLogger(AppRoleServiceImpl.class);

    private final AppRoleRepository appRoleRepository;

    private final AppRoleMapper appRoleMapper;

    public AppRoleServiceImpl(AppRoleRepository appRoleRepository, AppRoleMapper appRoleMapper) {
        this.appRoleRepository = appRoleRepository;
        this.appRoleMapper = appRoleMapper;
    }

    @Override
    public AppRoleDTO save(AppRoleDTO appRoleDTO) {
        log.debug("Request to save AppRole : {}", appRoleDTO);
        AppRole appRole = appRoleMapper.toEntity(appRoleDTO);
        appRole = appRoleRepository.save(appRole);
        return appRoleMapper.toDto(appRole);
    }

    @Override
    public Optional<AppRoleDTO> partialUpdate(AppRoleDTO appRoleDTO) {
        log.debug("Request to partially update AppRole : {}", appRoleDTO);

        return appRoleRepository
            .findById(appRoleDTO.getId())
            .map(existingAppRole -> {
                appRoleMapper.partialUpdate(existingAppRole, appRoleDTO);

                return existingAppRole;
            })
            .map(appRoleRepository::save)
            .map(appRoleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppRoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppRoles");
        return appRoleRepository.findAll(pageable).map(appRoleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppRoleDTO> findOne(Long id) {
        log.debug("Request to get AppRole : {}", id);
        return appRoleRepository.findById(id).map(appRoleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppRole : {}", id);
        appRoleRepository.deleteById(id);
    }
}
