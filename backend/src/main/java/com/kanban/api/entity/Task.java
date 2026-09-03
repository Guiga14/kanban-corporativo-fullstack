package com.kanban.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "O título da tarefa é obrigatório e não pode ficar em branco.")
    private String title;

    private String description;

    @Column(nullable = false)
    private String status;

    // NOVO: Data de entrega
    private LocalDate dueDate;

    // NOVO: Relacionamento Muitos-Para-Um com Usuário
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // NOVO: Relacionamento Muitos-Para-Um com Equipe
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}