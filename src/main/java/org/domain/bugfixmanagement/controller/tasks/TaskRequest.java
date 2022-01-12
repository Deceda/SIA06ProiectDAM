package org.domain.bugfixmanagement.controller.tasks;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskRequest {
    private Long project_id;
    private Long user_id;
    private String title;
    private String task_status;
    private String task_description;

    public boolean validateParameters(){
        return (title.matches("[^A-Za-z0-9 _.?!-]+") && task_status.matches("[^A-Za-z0-9 -]+") && task_description.matches("[^A-Za-z0-9 _.?!-]+"));
    }
}
