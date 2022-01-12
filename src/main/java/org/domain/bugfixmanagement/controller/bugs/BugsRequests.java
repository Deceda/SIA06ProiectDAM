package org.domain.bugfixmanagement.controller.bugs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BugsRequests {
    private Long project_id;
    private Long user_id;
    private String title;
    private String bug_description;
    private String bug_status;

    public boolean validateParameters(){
        return (title.matches("[^A-Za-z0-9 _.?!-]+") && bug_description.matches("[^A-Za-z0-9 _.?!-]+") && bug_status.matches("[^A-Za-z0-9 _.?!-]+"));
    }
}
