package com.kanban.api.controller;

import com.kanban.api.entity.Task;
import com.kanban.api.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/metrics")
@CrossOrigin(origins = "*") // Libera pro React
public class MetricsController {

    private final TaskRepository taskRepository;

    public MetricsController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // O nosso DTO de resposta para o React
    public record MetricsResponse(long total, long todo, long doing, long done, long delayed) {
    }

    @GetMapping
    public MetricsResponse getMetrics() {
        List<Task> tasks = taskRepository.findAll();

        long total = tasks.size();

        long todo = tasks.stream().filter(t -> "TODO".equals(t.getStatus())).count();
        long doing = tasks.stream().filter(t -> "DOING".equals(t.getStatus())).count();
        long done = tasks.stream().filter(t -> "DONE".equals(t.getStatus())).count();

        // Calcula as tarefas atrasadas (Data anterior a hoje e que não estão concluídas)
        LocalDate hoje = LocalDate.now();
        long delayed = tasks.stream()
                .filter(t -> t.getDueDate() != null
                        && t.getDueDate().isBefore(hoje)
                        && !"DONE".equals(t.getStatus()))
                .count();

        return new MetricsResponse(total, todo, doing, done, delayed);
    }
}