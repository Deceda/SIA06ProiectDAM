package org.domain.bugfixmanagement.repository;

import org.domain.bugfixmanagement.entity.Bug;
import org.domain.bugfixmanagement.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query(value = "INSERT INTO projects(user_id, title, project_description, readme) VALUES(?, ?, ?, ?)", nativeQuery = true)
    Project insert_project(long userId, String title, String project_description, String readme);

    @Query(value = "SELECT * FROM projects WHERE project_id = ?", nativeQuery = true)
    List<Project> getProjectByProjectId(long projectId);
}
