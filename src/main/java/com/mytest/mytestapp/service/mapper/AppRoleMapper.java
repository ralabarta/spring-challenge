package com.mytest.mytestapp.service.mapper;

import com.mytest.mytestapp.domain.AppRole;
import com.mytest.mytestapp.service.dto.AppRoleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppRole} and its DTO {@link AppRoleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AppRoleMapper extends EntityMapper<AppRoleDTO, AppRole> {
    @Named("roleName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "roleName", source = "roleName")
    AppRoleDTO toDtoRoleName(AppRole appRole);
}
