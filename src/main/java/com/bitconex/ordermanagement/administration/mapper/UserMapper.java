package com.bitconex.ordermanagement.administration.mapper;

import com.bitconex.ordermanagement.administration.dto.UserDTO;
import com.bitconex.ordermanagement.administration.entity.BuyerUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    //@Mapping(target = "password", ignore = true)

    //AdressDTO adressToAdressDTO(Adress adress);
   // UserDTO userToUserDTO(User user);

//    List<UserDTO> usersToUserDTOs(List<User> users);
//
//    //@Mapping(target = "adress", source = "adress")
//    UserDTO customerUserToUserDTO(CustomerUser customerUser);
//
//    List<UserDTO> customerUsersToUserDTOs(List<CustomerUser> customerUsers);

    UserDTO mapBuyerUserToDTO(BuyerUser buyerUser);

    //List<UserDTO> mapUsersToUserDTOs(List<User> users);
    List<UserDTO> mapBuyerToUserDTOs(List<BuyerUser> buyerUsers);

   // String roleToString(Role role);

   // Role stringToRole(String role);


}
