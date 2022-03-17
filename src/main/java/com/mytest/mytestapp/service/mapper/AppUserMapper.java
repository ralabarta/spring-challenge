package com.mytest.mytestapp.service.mapper;

import com.mytest.mytestapp.domain.AppUser;
import com.mytest.mytestapp.service.dto.AppUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppUser} and its DTO {@link AppUserDTO}.
 */
@Mapper(componentModel = "spring", uses = { AppRoleMapper.class })
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {
    @Mapping(target = "rol", source = "rol", qualifiedByName = "roleName")
    AppUserDTO toDto(AppUser s);
}
