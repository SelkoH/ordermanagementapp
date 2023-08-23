package com.bitconex.ordermanagement.administration.useradministration.security;

import com.bitconex.ordermanagement.administration.useradministration.entity.User;
import com.bitconex.ordermanagement.administration.useradministration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        User user = userRepository.findByLoginName(loginName);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + loginName);

        }

        // .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + loginName));
        //Convert Set<Role> into Set<GrantedAuthority>
        //Spring security expected this GrantedAuthority
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        //Convert User objcto into spring security provided user object

        return new org.springframework.security.core.userdetails.User(user.getLoginName(),
                user.getPassword(),
                authorities);
    }
}
