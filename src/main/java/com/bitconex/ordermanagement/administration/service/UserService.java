package com.bitconex.ordermanagement.administration.service;

import com.bitconex.ordermanagement.administration.dto.UserDTO;
import com.bitconex.ordermanagement.administration.entity.AdminUser;
import com.bitconex.ordermanagement.administration.entity.BuyerUser;
import com.bitconex.ordermanagement.administration.entity.User;

import javax.management.relation.RoleNotFoundException;
import javax.security.auth.login.CredentialException;
import java.util.List;

public interface UserService {

    void deleteUserByLoginName(String loginName);

    List<UserDTO> getAllUsersWithoutPasswords();

    void registerAdminUser(AdminUser adminUser) throws RoleNotFoundException;

    void registerBuyerUser(BuyerUser buyerUser) throws RoleNotFoundException;

    User checkUserCredentials(String loginName, String password) throws CredentialException;

}
