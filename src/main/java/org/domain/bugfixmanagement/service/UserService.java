package org.domain.bugfixmanagement.service;

import lombok.AllArgsConstructor;
import org.domain.bugfixmanagement.repository.UserRepository;
import org.domain.bugfixmanagement.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public void deleteUser(Long userId){
        userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        userRepository.deleteById(userId);
    }

    public User addUser(String fname, String lname, String email, String password, String position){
        if(fname.length() < 100 && lname.length() < 100 && email.length() < 100 && position.length() < 100){
            return userRepository.insert_user(fname, lname, email, password, position);
        }
        else{
            return null;
        }
    }
}
