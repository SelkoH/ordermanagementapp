package com.bitconex.ordermanagement.administration.useradministration.repository;

import com.bitconex.ordermanagement.administration.useradministration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    void deleteByLoginName(String loginName);

    User findByLoginName(String loginName);

}
