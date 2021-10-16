package com.murilonerdx.epictask.api;

import com.murilonerdx.epictask.entities.Tarefa;
import com.murilonerdx.epictask.repository.TarefaRepository;
import io.swagger.annotations.*;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/task")
@Api(tags="Endpoint de tarefas")
@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                required = true, dataType = "string", paramType = "header") })
public class ApiTaskController {

    @Autowired
    private TarefaRepository repository;

    @GetMapping
    @ApiOperation(value = "Buscando por paginação")
    @Cacheable("tasks")
    public Page<Tarefa> index(
            @RequestParam(required = false) String title, @PageableDefault(size = 5) Pageable pageable) {
        if (title == null)
            return repository.findAll(pageable);
        return repository.findByTitleContaining(title, pageable);
    }

    @PostMapping()
    @ApiOperation(value = "Criando uma tarefa")
    @CacheEvict(value = "tasks", allEntries = true)
    public ResponseEntity<Tarefa> create(
            @RequestBody @Valid Tarefa tarefa, UriComponentsBuilder uriBuilder
    ) {
        repository.save(tarefa);
        URI uri = uriBuilder
                .path("api/task/{id}")
                .buildAndExpand(tarefa.getId())
                .toUri();
        return ResponseEntity.created(uri).body(tarefa);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Buscar tarefa por id")
    public ResponseEntity<Tarefa> detail(@PathVariable Long id) {
        return ResponseEntity.of(repository.findById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletar tarefa por id")
    public ResponseEntity<Tarefa> delete(@PathVariable Long id) {
        Optional<Tarefa> task = repository.findById(id);
        if (task.isEmpty())
            return ResponseEntity.notFound().build();
        repository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualizando tarefa por id")
    @CacheEvict(value = "tasks", allEntries = true)
    public ResponseEntity<Tarefa> update(
            @PathVariable Long id,
            @RequestBody @Valid Tarefa newTarefa) {
        Optional<Tarefa> optional = repository.findById(id);

        if (optional.isEmpty())
            return ResponseEntity.notFound().build();

        Tarefa tarefa = optional.get();
        tarefa.setProgress(newTarefa.getProgress());
        tarefa.setTitle(newTarefa.getTitle());
        tarefa.setDescription(newTarefa.getDescription());
        tarefa.setScore(newTarefa.getScore());
        if(!newTarefa.isObtain()){
            tarefa.setPerfil(null);
        }

        repository.save(tarefa);

        return ResponseEntity.ok(tarefa);
    }


}
