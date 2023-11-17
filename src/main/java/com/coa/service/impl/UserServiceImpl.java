package com.coa.service.impl;

import com.coa.model.Role;
import com.coa.model.User;
import com.coa.repository.RoleRepository;
import com.coa.repository.UserRepository;
import com.coa.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    public User findByUserName(String userName) {
        return  userRepository.findByUserName(userName);
    }

    @Override
    @Transactional
    public void save(User user) {
       User dbUser=new User();
       dbUser.setUserName(user.getUserName());
       dbUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
       dbUser.setName(user.getName());
       dbUser.setPosition(user.getPosition());
       dbUser.setRoles(List.of(roleRepository.findByRoleName("EMPLOYEE")));
       dbUser.setActive(true);

       userRepository.save(dbUser);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user=userRepository.findByUserName(username);

       if(user==null){
           throw new UsernameNotFoundException("Username does not exist. Please try again");
       }

        Collection<SimpleGrantedAuthority> authorities =mapRolesToAuthorities(user.getRoles());

       return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        List<SimpleGrantedAuthority> authorities= roles.stream()
                .map(role->new SimpleGrantedAuthority(String.format("ROLE_%s",role.getName())))
                .collect(Collectors.toList());


        return authorities;
    }
}
