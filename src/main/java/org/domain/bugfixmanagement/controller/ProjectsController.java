package org.domain.bugfixmanagement.controller;

import org.domain.bugfixmanagement.entity.Project;
import org.domain.bugfixmanagement.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProjectsController {

    public final ProjectService projectService;

    public ProjectsController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public List<Project> projects() {
        return projectService.getAllProjects();
    }
}
