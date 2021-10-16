package com.murilonerdx.epictask.api;

import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.entities.dto.UsuarioDTO;
import com.murilonerdx.epictask.repository.UsuarioRepository;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuario")
@Api(tags="Endpoint para usuario")
@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                required = true, dataType = "string", paramType = "header") })
public class ApiUsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    @ApiOperation(value = "Buscando usuarios")
    @Cacheable("usuarios")
    public List<UsuarioDTO> index() {
        List<UsuarioDTO> usuarios = repository.findAll().stream().map(user -> {
            UsuarioDTO usuarioDTO = new ModelMapper().map(user, UsuarioDTO.class);
            return usuarioDTO;
        }).collect(Collectors.toList());
        return usuarios;
    }

    @PostMapping()
    @ApiOperation(value = "Criando usuario")
    @CacheEvict(value = "usuarios", allEntries = true)
    public ResponseEntity<Usuario> create(
            @RequestBody @Valid Usuario usuario, UriComponentsBuilder uriBuilder
    ) {
        repository.save(usuario);
        URI uri = uriBuilder
                .path("api/usuario/{id}")
                .buildAndExpand(usuario.getId())
                .toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Buscar usuario por id")
    public ResponseEntity<UsuarioDTO> detail(@PathVariable Long id) {
        Usuario user = repository.findById(id).get();
        UsuarioDTO usuarioDTO = new ModelMapper().map(user, UsuarioDTO.class);
        return ResponseEntity.ok(usuarioDTO);
    }

    @DeleteMapping
    @ApiOperation(value = "Deletar usuario por id")
    public ResponseEntity<Usuario> delete(@PathVariable Long id) {
        Optional<Usuario> usuario = repository.findById(id);
        if (usuario.isEmpty())
            return ResponseEntity.notFound().build();
        repository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualizando tarefa por id")
    @CacheEvict(value = "usuarios", allEntries = true)
    public ResponseEntity<UsuarioDTO> updatePassword(
            @PathVariable Long id,
            @RequestBody @Valid Usuario newUsuario) {
        Optional<Usuario> optional = repository.findById(id);

        if (optional.isEmpty())
            return ResponseEntity.notFound().build();

        Usuario user = optional.get();
        user.setPassword(newUsuario.getPassword());
        repository.save(user);
        UsuarioDTO usuarioDTO = new ModelMapper().map(user, UsuarioDTO.class);
        return ResponseEntity.ok(usuarioDTO);
    }
}
