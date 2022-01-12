package org.domain.bugfixmanagement.controller.projects;

import org.domain.bugfixmanagement.entity.Project;
import org.domain.bugfixmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProjectsController {

    public final ProjectService projectService;

    @Autowired
    public ProjectsController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Project>> projects() {
        return ResponseEntity.ok().body(projectService.getAllProjects());
    }

    @GetMapping("/get-project")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Project> getProjectById(@RequestParam(name = "project_id") Long id){
        return ResponseEntity.ok().body(projectService.getProjectById(id));
    }

    @PostMapping("/add-project")
    public void addProject(@RequestBody ProjectRequest request) {
        if (request.validateParameters()) {
            projectService.addProject(request.getUser_id(), request.getTitle(), request.getProject_description(), request.getReadme());
        }
    }

    @DeleteMapping("/delete-project")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@RequestParam(name = "project_id") Long id){
        projectService.deleteProject(id);
    }
}
