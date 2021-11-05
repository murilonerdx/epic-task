package com.murilonerdx.epictask.entities.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDTO {
    private String name;
    private LoginDTO login;
}
