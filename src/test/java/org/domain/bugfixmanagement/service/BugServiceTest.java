package org.domain.bugfixmanagement.service;

import org.domain.bugfixmanagement.entity.Bug;
import org.domain.bugfixmanagement.entity.Project;
import org.domain.bugfixmanagement.entity.User;
import org.domain.bugfixmanagement.repository.BugRepository;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BugServiceTest {
    private final BugRepository bugRepository = Mockito.mock(BugRepository.class);

    private final BugService bugService = new BugService(bugRepository);

    @Test
    public void test1(){
        given(bugRepository.findAll()).willReturn(Collections.emptyList());
        List<Bug> result = bugService.getAllBugs();
        assertThat(result).isEmpty();
        verify(bugRepository, times(1)).findAll();
    }

    @Test
    public void test2(){
        given(bugRepository.findAll()).willReturn(List.of
                (new Bug(1L, new Project(), new User(), "titlu", "descriere", "status", "creat"),
                 new Bug(2L, new Project(), new User(), "titlu", "descriere", "status", "creat"),
                 new Bug(3L, new Project(), new User(), "titlu", "descriere", "status", "creat")));
        List<Bug> result = bugService.getAllBugs();
        assertThat(result).hasSize(3);
        verify(bugRepository, times(1)).findAll();
    }

    @Test
    public void shouldReturnBugById(){
        Bug bug = new Bug();
        bug.setBugId(1L);

        when(bugRepository.findById(bug.getBugId())).thenReturn(Optional.of(bug));

        Bug expected = bugService.getBugById(bug.getBugId());
        assertThat(expected).isSameAs(bug);
        verify(bugRepository).findById(bug.getBugId());
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldThrowExceptionWhenBugDoesntExist() {
        Bug bug = new Bug();
        bug.setBugId(1L);
        bug.setTitle("First bug");

        given(bugRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        bugService.getBugById(bug.getBugId());
    }

    @Test
    public void shouldReturnBugByProjectId(){
        Project project = new Project(1L, new User(), "FirstProject", "The first project", "status");
        Bug bug1 = new Bug(1L, project, new User(), "bug1", "descriere1", "status1", "01.01.2022");
        Bug bug2 = new Bug(2L, project, new User(), "bug2", "descriere2", "status2", "01.02.2022");

        when(bugRepository.getBugsByProject(project.getProjectId())).thenReturn(List.of(bug1, bug2));

        List<Bug> result = bugService.getBugByProjectId(project.getProjectId());
        assertThat(result).hasSize(2);
        verify(bugRepository).getBugsByProject(project.getProjectId());
    }

    @Test
    public void shouldDeleteBugById(){
        Bug bug = new Bug();
        bug.setBugId(1L);
        bug.setTitle("First bug");

        when(bugRepository.findById(bug.getBugId())).thenReturn(Optional.of(bug));

        bugService.deleteBug(bug.getBugId());
        verify(bugRepository).deleteById(bug.getBugId());
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldThrowExceptionWhenBugDoesntExistToBeDeleted() {
        Bug bug = new Bug();
        bug.setBugId(1L);
        bug.setTitle("First bug");

        given(bugRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        bugService.deleteBug(bug.getBugId());
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldThrowExceptionWhenProjectDoesntExist() {
        Bug bug = new Bug();
        bug.setBugId(1L);
        bug.setTitle("First bug");

        given(bugRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        bugService.getBugById(bug.getBugId());
    }

    @Test
    public void shouldReturnAddedBug() {
        Bug bug = new Bug();
        bug.setTitle("First bug");

        when(bugRepository.save(any(Bug.class))).thenReturn(bug);

        Bug createdBug = bugService.addBug(any(Long.class), any(Long.class), any(String.class), any(String.class), any(String.class));

        assertThat(createdBug.getTitle()).isSameAs(bug.getTitle());
        verify(bugRepository).save(bug);
    }



}
