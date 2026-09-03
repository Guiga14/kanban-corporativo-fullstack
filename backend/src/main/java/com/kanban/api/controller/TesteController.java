package com.kanban.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {

    @GetMapping("/hello")
    public String dizerOla() {
        return "Olá, Guilherme! O Spring Boot está rodando perfeitamente!";
    }
}
