package com.asm3.HealScheduleApp.entity;

import com.asm3.HealScheduleApp.validation.FieldMatch;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message = "Các trường mật khẩu phải khớp")
})
@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank(message = "Tên không được để trống.")
    @NotNull(message = "Bắc buộc")
    @Column(name = "full_name")
    private String fullName;

    @NotBlank(message = "Email không được để trống.")
    @NotNull(message = "Bắc buộc")
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email không hợp lệ.")
    @Column(name = "email")
    private String email;

    @Pattern(regexp = "0[0-9]{9}", message = "Số điện thoại không hợp lệ.")
    @NotNull(message = "Bắc buộc")
    @Column(name = "phone")
    private String phone;

    @NotBlank(message = "Giới tính không được để trống.")
    @NotNull(message = "Bắc buộc")
    @Column(name = "gender")
    private String gender;

    @NotBlank(message = "Địa chỉ không được để trống.")
    @NotNull(message = "Bắc buộc")
    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "active")
    private boolean active;

    @NotBlank(message = "Mật khẩu không được để trống")
    @NotNull(message = "Bắc buộc")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "Mật khẩu không được để trống")
    @NotNull(message = "Bắc buộc")
    @Column(name = "matching_password")
    private String matchingPassword;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

}
