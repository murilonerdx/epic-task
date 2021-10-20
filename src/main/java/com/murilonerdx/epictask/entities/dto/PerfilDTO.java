package com.murilonerdx.epictask.entities.dto;


import com.murilonerdx.epictask.entities.Perfil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDTO {
    private String name;
    private LoginDTO login;
}
