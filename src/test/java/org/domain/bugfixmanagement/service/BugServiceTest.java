package org.domain.bugfixmanagement.service;

import org.domain.bugfixmanagement.entity.Bug;
import org.domain.bugfixmanagement.entity.Project;
import org.domain.bugfixmanagement.entity.User;
import org.domain.bugfixmanagement.repository.BugRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.assertj.core.api.Assertions.assertThat;

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
}
