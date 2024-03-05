package com.asm3.HealScheduleApp.entity;

import com.asm3.HealScheduleApp.validation.FieldMatch;
import com.asm3.HealScheduleApp.validation.ValidEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
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

    @NotBlank(message = "cannot be empty.")
    @NotNull(message = "is required")
    @Column(name = "full_name")
    private String fullName;

    @ValidEmail
    @Column(name = "email")
    private String email;

    @Pattern(regexp = "0[0-9]{9}", message = "Invalid phone number.")
    @NotNull(message = "is required")
    @Column(name = "phone")
    private String phone;

    @NotBlank(message = "cannot be empty.")
    @NotNull(message = "is required")
    @Column(name = "gender")
    private String gender;

    @NotBlank(message = "cannot be empty.")
    @NotNull(message = "is required")
    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "secret_key")
    private String sessionToken;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "active_id")
    private ActiveStatus activeStatus = new ActiveStatus();

    @Column(name = "password")
    private String password;

    @Column(name = "matching_password")
    private String matchingPassword;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;
}
