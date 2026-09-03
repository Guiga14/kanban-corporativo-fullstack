package com.kanban.api.controller;

import com.kanban.api.entity.User;
import com.kanban.api.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*") // Liberando o acesso para o React!
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<User> listarTodos() {
        return repository.findAll();
    }
}