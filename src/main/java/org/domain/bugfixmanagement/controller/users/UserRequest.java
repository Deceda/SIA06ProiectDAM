package org.domain.bugfixmanagement.controller.users;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequest {
    private String fname;
    private String lname;
    private String email;
    private String password;
    private String position;

    public boolean validateParameters(){
        return (fname.matches("[^A-Za-z -]+") && lname.matches("[^A-Za-z]+") && email.matches("[^A-Za-z0-9 _@.?!-]+") && password.matches("[^A-Za-z0-9?!_-]+") && position.matches("[^A-Za-z -]+"));
    }
}
