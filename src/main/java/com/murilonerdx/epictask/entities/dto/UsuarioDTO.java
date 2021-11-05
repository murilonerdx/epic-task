package com.murilonerdx.epictask.entities.dto;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String email;
    private Role role;
    private Perfil perfil;
}
