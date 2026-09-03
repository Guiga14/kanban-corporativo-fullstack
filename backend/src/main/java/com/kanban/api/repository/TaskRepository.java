package com.kanban.api.repository;

import com.kanban.api.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // O Spring Boot cria as queries no banco sozinho só lendo esses nomes!
    List<Task> findByUserIdAndTeamId(Long userId, Long teamId);
    List<Task> findByUserId(Long userId);
    List<Task> findByTeamId(Long teamId);
}