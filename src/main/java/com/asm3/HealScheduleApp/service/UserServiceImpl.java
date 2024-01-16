package com.asm3.HealScheduleApp.service;

import com.asm3.HealScheduleApp.dao.UserRepository;
import com.asm3.HealScheduleApp.entity.Role;
import com.asm3.HealScheduleApp.entity.User;
import com.asm3.HealScheduleApp.model.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void create(User user) {
        System.out.println("Called create: ");
        user.setId(0);
        user.setRoles(Arrays.asList(roleService.findByName("ROLE_USER")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setMatchingPassword(user.getPassword());
        userRepository.save(user);
    }

    @Override
    public void changePassword(String email, ChangePasswordRequest changePasswordRequest) {
        User updateUser = userRepository.findByEmail(email);
        updateUser.setPassword(passwordEncoder.encode(changePasswordRequest.getPassword()));
        updateUser.setMatchingPassword(updateUser.getPassword());
        userRepository.save(updateUser);
    }
}
