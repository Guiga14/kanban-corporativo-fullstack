package com.kanban.api.service;

import com.kanban.api.entity.Task;
import com.kanban.api.entity.Team;
import com.kanban.api.entity.User;
import com.kanban.api.repository.TaskRepository;
import com.kanban.api.repository.TeamRepository;
import com.kanban.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, TeamRepository teamRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    // Ação 1: Listar com filtros
    public List<Task> listarTodas(Long userId, Long teamId) {
        if (userId != null && teamId != null) {
            return taskRepository.findByUserIdAndTeamId(userId, teamId);
        } else if (userId != null) {
            return taskRepository.findByUserId(userId);
        } else if (teamId != null) {
            return taskRepository.findByTeamId(teamId);
        }
        return taskRepository.findAll();
    }

    // Ação 2: Criar tarefa associando Equipe e Usuário
    public Task criarTarefa(String title, String description, String status, LocalDate dueDate, Long userId, Long teamId) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status != null ? status : "TODO");
        task.setDueDate(dueDate);

        if (userId != null) {
            User user = userRepository.findById(userId).orElse(null);
            task.setUser(user);
        }
        if (teamId != null) {
            Team team = teamRepository.findById(teamId).orElse(null);
            task.setTeam(team);
        }

        return taskRepository.save(task);
    }

    // Ação 3: Atualização dinâmica (perfeita para o drag-and-drop do React)
    public Task atualizarTarefa(Long id, String title, String description, String status, LocalDate dueDate, Long userId, Long teamId) {
        Task taskExistente = buscarPorId(id);

        if (title != null) taskExistente.setTitle(title);
        if (description != null) taskExistente.setDescription(description);
        if (status != null) taskExistente.setStatus(status);
        if (dueDate != null) taskExistente.setDueDate(dueDate);

        if (userId != null) {
            User user = userRepository.findById(userId).orElse(null);
            taskExistente.setUser(user);
        }
        if (teamId != null) {
            Team team = teamRepository.findById(teamId).orElse(null);
            taskExistente.setTeam(team);
        }

        return taskRepository.save(taskExistente);
    }

    public Task buscarPorId(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com o ID: " + id));
    }

    public void deletarTarefa(Long id) {
        buscarPorId(id);
        taskRepository.deleteById(id);
    }
}