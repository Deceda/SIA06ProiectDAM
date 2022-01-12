package org.domain.bugfixmanagement.controller.projects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectRequest {
    private Long user_id;
    private String title;
    private String project_description;
    private String readme;

    public boolean validateParameters(){
        return (title.matches("[^A-Za-z0-9 _.?!-]+") && project_description.matches("[^A-Za-z0-9 _.?!-]+") && readme.matches("[^A-Za-z0-9 _.?!-]+"));
    }
}
