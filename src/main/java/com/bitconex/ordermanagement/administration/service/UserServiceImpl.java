package com.bitconex.ordermanagement.administration.service;

import com.bitconex.ordermanagement.administration.mapper.UserMapper;
import com.bitconex.ordermanagement.administration.dto.UserDTO;
import com.bitconex.ordermanagement.administration.entity.AdminUser;
import com.bitconex.ordermanagement.administration.entity.BuyerUser;
import com.bitconex.ordermanagement.administration.entity.Role;
import com.bitconex.ordermanagement.administration.entity.User;
import com.bitconex.ordermanagement.administration.repository.RoleRepository;
import com.bitconex.ordermanagement.administration.repository.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    private final RoleRepository roleRepository;

    private final UserMapper userMapper;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }


    @Override
    @Transactional
    public void deleteUserByLoginName(String loginName) {
        User user = userRepository.findByLoginName(loginName);
        if (user != null) {
            userRepository.deleteByLoginName(loginName);
        } else {
            throw new IllegalStateException("User with login name " + loginName + " does not exsist");
        }

    }

    @Override
    public List<UserDTO> getAllUsersWithoutPasswords() {
        List<User> users = userRepository.findAll();

        List<BuyerUser> buyerUsers = users.stream()
                .filter(user -> user instanceof BuyerUser)
                .map(user -> (BuyerUser) user)
                .collect(Collectors.toList());

        List<BuyerUser> adminUsers = users.stream()
                .filter(user -> user instanceof AdminUser)
                .map(user -> {
                    BuyerUser buyerUser = new BuyerUser();
                    buyerUser.setId(user.getId());
                    buyerUser.setLoginName(user.getLoginName());
                    buyerUser.setPassword(user.getPassword());
                    buyerUser.setEmail(user.getEmail());
                    buyerUser.setRoles(user.getRoles());
                    buyerUser.setFirstName(null);
                    buyerUser.setLastName(null);
                    buyerUser.setDateOfBirth(null);

                    return buyerUser;
                }).toList();

        buyerUsers.addAll(adminUsers);

        List<UserDTO> userDTOList = userMapper.mapBuyerToUserDTOs(buyerUsers);

        return userDTOList;
    }

    @Override
    public void registerAdminUser(AdminUser adminUser) throws RoleNotFoundException {
        validateUser(adminUser);
        adminUser.addRole(findRole("ROLE_ADMIN"));
        userRepository.save(adminUser);
    }

    public void registerBuyerUser(BuyerUser buyerUser) throws RoleNotFoundException {
        validateUser(buyerUser);
        buyerUser.addRole(findRole("ROLE_BUYER"));
        userRepository.save(buyerUser);

    }

    public void validateUser(User u) {
        User user = userRepository.findByLoginName(u.getLoginName());
        if (user != null) {
            throw new IllegalStateException("user with Login name " + u.getLoginName() + " already exsists!");
        }

    }

    public Role findRole(String roleName) throws RoleNotFoundException {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new RoleNotFoundException("unknown role!");
        }
        return role;
    }

    @Override
    public User checkUserCredentials(String loginName, String password) {
        User user = userRepository.findByLoginName(loginName);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            System.out.println("User: " + user.getLoginName() + " successfully logged in.");
            System.out.println("-----------------------------------");
            return user;
        } else {
            return null;
        }

    }
}
