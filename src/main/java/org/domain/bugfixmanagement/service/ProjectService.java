package org.domain.bugfixmanagement.service;

import lombok.AllArgsConstructor;
import org.domain.bugfixmanagement.entity.Project;
import org.domain.bugfixmanagement.entity.Task;
import org.domain.bugfixmanagement.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id){
        return projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Project not found"));
    }

    public void deleteProject(Long projectId){
        projectRepository.findById(projectId).orElseThrow(() -> new IllegalArgumentException("Project not found"));
        projectRepository.deleteById(projectId);
    }

    public Project addProject(long userId, String title, String project_description, String readme){
        if(title.length() < 30 && project_description.length() < 100 && readme.length() < 1000) {
            return projectRepository.insert_project(userId, title, project_description, readme);
        }
        return null;
    }
}
