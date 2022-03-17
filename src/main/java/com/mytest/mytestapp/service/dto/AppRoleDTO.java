package com.mytest.mytestapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mytest.mytestapp.domain.AppRole} entity.
 */
public class AppRoleDTO implements Serializable {

    private Long id;

    @NotNull
    private String roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppRoleDTO)) {
            return false;
        }

        AppRoleDTO appRoleDTO = (AppRoleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appRoleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppRoleDTO{" +
            "id=" + getId() +
            ", roleName='" + getRoleName() + "'" +
            "}";
    }
}
