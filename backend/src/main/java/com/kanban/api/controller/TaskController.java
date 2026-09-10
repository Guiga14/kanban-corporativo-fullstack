package com.kanban.api.controller;

import com.kanban.api.entity.Task;
import com.kanban.api.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> listarTodas(@RequestParam(required = false) Long userId,
                                  @RequestParam(required = false) Long teamId) {
        return taskService.listarTodas(userId, teamId);
    }

    // DTO Moderno: Essa linha traduz perfeitamente o JSON do React para o Java
    public record TaskRequest(String title, String description, String status, LocalDate dueDate, Long userId, Long teamId) {}

    @PostMapping
    public ResponseEntity<Task> criarTarefa(@RequestBody TaskRequest req) {
        Task novaTarefa = taskService.criarTarefa(req.title(), req.description(), req.status(), req.dueDate(), req.userId(), req.teamId());
        return ResponseEntity.status(HttpStatus.CREATED).body(novaTarefa);
    }

    @PutMapping("/{id}")
    public Task atualizar(@PathVariable Long id, @RequestBody TaskRequest req) {
        return taskService.atualizarTarefa(id, req.title(), req.description(), req.status(), req.dueDate(), req.userId(), req.teamId());
    }

    @GetMapping("/{id}")
    public Task buscarPorId(@PathVariable Long id) {
        return taskService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        taskService.deletarTarefa(id);
    }
}