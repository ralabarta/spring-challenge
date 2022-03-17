package com.mytest.mytestapp.repository;

import com.mytest.mytestapp.domain.AppRole;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AppRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {}
