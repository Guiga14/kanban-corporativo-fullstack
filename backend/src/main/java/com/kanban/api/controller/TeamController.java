package com.kanban.api.controller;

import com.kanban.api.entity.Team;
import com.kanban.api.repository.TeamRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@CrossOrigin(origins = "*") // Liberando o acesso para o React!
public class TeamController {

    private final TeamRepository repository;

    public TeamController(TeamRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Team> listarTodos() {
        return repository.findAll();
    }
}