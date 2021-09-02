package com.murilonerdx.epictask.api;

import com.murilonerdx.epictask.entities.Tarefa;
import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.repository.TarefaRepository;
import com.murilonerdx.epictask.repository.UsuarioRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
@Api(tags="Endpoint para usuario")
public class ApiUsuarioController {
    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    @ApiOperation(value = "Buscando usuarios")
    @Cacheable("usuarios")
    public List<Usuario> index() {
        return repository.findAll();
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
    public ResponseEntity<Usuario> detail(@PathVariable Long id) {
        return ResponseEntity.of(repository.findById(id));
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
    public ResponseEntity<Usuario> updatePassword(
            @PathVariable Long id,
            @RequestBody @Valid Usuario newUsuario) {
        Optional<Usuario> optional = repository.findById(id);

        if (optional.isEmpty())
            return ResponseEntity.notFound().build();

        Usuario usuario = optional.get();
        usuario.setPassword(newUsuario.getPassword());
        repository.save(usuario);

        return ResponseEntity.ok(usuario);
    }
}
