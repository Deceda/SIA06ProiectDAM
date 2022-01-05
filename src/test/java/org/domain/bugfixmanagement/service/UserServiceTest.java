package org.domain.bugfixmanagement.service;

import org.domain.bugfixmanagement.entity.Project;
import org.domain.bugfixmanagement.entity.Task;
import org.domain.bugfixmanagement.entity.User;
import org.domain.bugfixmanagement.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserServiceTest {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);

    private final UserService userService = new UserService(userRepository);

    @Test
    public void test1(){
        given(userRepository.findAll()).willReturn(Collections.emptyList());
        List<User> result = userService.getAllUsers();
        assertThat(result).isEmpty();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void test2(){
        given(userRepository.findAll()).willReturn(List.of
                (new User(1L, "Ion", "Ionescu", "ion.ionescu@gmail.com", "ionut", "tester"),
                        new User(2L, "George", "Georgescu", "george.georgescu@gmail.com", "georgica", "developer"),
                        new User(3L, "Mihai", "Mihaescu", "mihai.mihaescu@gmail.com", "mihaita", "CEO")));
        List<User> result = userService.getAllUsers();
        assertThat(result).hasSize(3);
        verify(userRepository, times(1)).findAll();
    }
}
