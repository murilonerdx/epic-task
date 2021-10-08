package com.murilonerdx.epictask.entities;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {
    @Email
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}