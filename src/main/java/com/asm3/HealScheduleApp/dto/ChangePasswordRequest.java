package com.asm3.HealScheduleApp.dto;

import com.asm3.HealScheduleApp.validation.FieldMatch;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
public class ChangePasswordRequest {

    @NotNull(message = "password is required")
    private String password;

    @NotNull(message = "password is required")
    private String matchingPassword;
}
