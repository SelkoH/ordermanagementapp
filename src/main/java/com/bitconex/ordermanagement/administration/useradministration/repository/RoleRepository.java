package com.bitconex.ordermanagement.administration.useradministration.repository;

import com.bitconex.ordermanagement.administration.useradministration.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
