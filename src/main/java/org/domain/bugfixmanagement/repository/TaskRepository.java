package org.domain.bugfixmanagement.repository;

import org.domain.bugfixmanagement.entity.Bug;
import org.domain.bugfixmanagement.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "INSERT INTO tasks(project_id, user_id, title, task_status, task_description, created) VALUES(?, ?, ?, ?, ?, ?)", nativeQuery = true)
    Task insert_task(long projectId, long userId, String title, String task_status, String task_description, String created);

    @Query(value = "SELECT * FROM tasks WHERE project_id = ?", nativeQuery = true)
    List<Task> getTasksByProject(long projectId);
}
