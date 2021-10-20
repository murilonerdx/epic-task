package com.murilonerdx.epictask.api;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.entities.dto.LoginDTO;
import com.murilonerdx.epictask.entities.dto.PerfilDTO;
import com.murilonerdx.epictask.entities.dto.UsuarioDTO;
import com.murilonerdx.epictask.entities.enums.Role;
import com.murilonerdx.epictask.repository.UsuarioRepository;
import com.murilonerdx.epictask.services.security.IAuthenticationFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.murilonerdx.epictask.util.ImageTransform.returnBytesDefault;

@RestController
@RequestMapping("/api/usuario")
@Api(tags = "Endpoint para usuario")
public class ApiUsuarioController {

  @Autowired private UsuarioRepository repository;

  @Autowired IAuthenticationFacade authenticationFacade;

  @GetMapping
  @ApiOperation(value = "Buscando usuarios")
  @Cacheable("usuarios")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "Authorization token",
        required = true,
        dataType = "string",
        paramType = "header")
  })
  public List<UsuarioDTO> index() {
    List<UsuarioDTO> usuarios =
        repository.findAll().stream()
            .map(
                user -> {
                  UsuarioDTO usuarioDTO = new ModelMapper().map(user, UsuarioDTO.class);
                  return usuarioDTO;
                })
            .collect(Collectors.toList());

    return usuarios;
  }

  @PostMapping()
  @ApiOperation(value = "Criando usuario")
  @CacheEvict(value = "usuarios", allEntries = true)
  public ResponseEntity<Usuario> create(
      @RequestBody @Valid PerfilDTO perfilDTO, UriComponentsBuilder uriBuilder) throws IOException {

    Usuario usuario = new Usuario(
            null,
            perfilDTO.getLogin().getEmail(),
            new BCryptPasswordEncoder().encode(perfilDTO.getLogin().getPassword()),
            Role.USER,
            new Perfil(null, perfilDTO.getName(), returnBytesDefault()));

    repository.save(usuario);

    return ResponseEntity.ok().body(usuario);
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "Buscar usuario por id")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "Authorization token",
        required = true,
        dataType = "string",
        paramType = "header")
  })
  public ResponseEntity<UsuarioDTO> detail(@PathVariable Long id) {
    Usuario user = repository.findById(id).get();
    UsuarioDTO usuarioDTO = new ModelMapper().map(user, UsuarioDTO.class);
    return ResponseEntity.ok(usuarioDTO);
  }

  @DeleteMapping
  @ApiOperation(value = "Deletar usuario por id")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "Authorization token",
        required = true,
        dataType = "string",
        paramType = "header")
  })
  public ResponseEntity<Usuario> delete(@PathVariable Long id) {
    Optional<Usuario> usuario = repository.findById(id);
    if (usuario.isEmpty()) return ResponseEntity.notFound().build();
    repository.deleteById(id);

    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  @ApiOperation(value = "Atualizando tarefa por id")
  @CacheEvict(value = "usuarios", allEntries = true)
  public ResponseEntity<UsuarioDTO> updatePassword(
      @PathVariable Long id, @RequestBody @Valid LoginDTO loginDTO) {
    Optional<Usuario> optional = repository.findById(id);

    if (optional.isEmpty()) return ResponseEntity.notFound().build();

    Usuario user = optional.get();
    user.setPassword(loginDTO.getPassword());
    repository.save(user);
    UsuarioDTO usuarioDTO = new ModelMapper().map(user, UsuarioDTO.class);
    return ResponseEntity.ok(usuarioDTO);
  }
}
