package com.bitconex.ordermanagement.administration.repository;

import com.bitconex.ordermanagement.administration.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
