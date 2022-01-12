package org.domain.bugfixmanagement.service;

import org.domain.bugfixmanagement.entity.Bug;
import org.domain.bugfixmanagement.entity.User;
import org.domain.bugfixmanagement.repository.UserRepository;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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

    @Test
    public void shouldReturnUserById(){
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User expected = userService.getUserById(user.getId());
        assertThat(expected).isSameAs(user);
        verify(userRepository).findById(user.getId());
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldThrowExceptionWhenUserDoesntExist() {
        User user = new User();
        user.setId(1L);
        user.setFname("Ionescu");

        given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        userService.getUserById(user.getId());
    }

    @Test
    public void shouldDeleteUsergById(){
        User user = new User();
        user.setId(1L);
        user.setFname("Ionescu");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.deleteUser(user.getId());
        verify(userRepository).deleteById(user.getId());
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldThrowExceptionWhenUserDoesntExistToBeDeleted() {
        User user = new User();
        user.setId(1L);
        user.setFname("Ionescu");

        given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        userService.deleteUser(user.getId());
    }

    @Test
    public void shouldReturnAddedUser() {
        User user = new User();
        user.setFname("Ionescu");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.addUser(any(String.class), any(String.class), any(String.class), any(String.class), any(String.class));

        assertThat(createdUser.getFname()).isSameAs(user.getFname());
        verify(userRepository).save(user);
    }

}
