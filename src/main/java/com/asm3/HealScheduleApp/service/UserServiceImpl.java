package com.asm3.HealScheduleApp.service;

import com.asm3.HealScheduleApp.dao.UserRepository;
import com.asm3.HealScheduleApp.entity.Role;
import com.asm3.HealScheduleApp.entity.User;
import com.asm3.HealScheduleApp.dto.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    /**
     * Phương thức này được sử dụng để tải thông tin người dùng từ cơ sở dữ liệu theo địa chỉ email.
     *
     * @param email Địa chỉ email của người dùng cần tải thông tin.
     * @return UserDetails Đối tượng chứa thông tin người dùng đã được tải.
     * @throws UsernameNotFoundException Ném ra khi không tìm thấy người dùng tương ứng với địa chỉ email cung cấp.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                true, // enabled: Tài khoản được kích hoạt
                true,        // accountNonExpired: Tài khoản không hết hạn
                true,        // credentialsNonExpired: Mật khẩu không hết hạn
                user.getActiveStatus().isActive(), // accountNonLocked: Tài khoản không bị khóa
                mapRolesToAuthorities(user.getRoles()) // Danh sách các quyền được gán cho người dùng
        );
    }

    /**
     * Phương thức này chuyển đổi một danh sách các vai trò thành một danh sách các quyền được cấp.
     *
     * @param roles Danh sách các vai trò của người dùng.
     * @return Collection<? extends GrantedAuthority> Danh sách các quyền được cấp.
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User create(User user) {
        user.setId(0);
        user.setRoles(Arrays.asList(roleService.findByName("ROLE_USER")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setMatchingPassword(user.getPassword());
        return userRepository.save(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void changePassword(String email, ChangePasswordRequest changePasswordRequest) {
        User updateUser = userRepository.findByEmail(email);
        updateUser.setPassword(passwordEncoder.encode(changePasswordRequest.getPassword()));
        updateUser.setMatchingPassword(updateUser.getPassword());
        updateUser.setSessionToken("");
        userRepository.save(updateUser);
    }
}
