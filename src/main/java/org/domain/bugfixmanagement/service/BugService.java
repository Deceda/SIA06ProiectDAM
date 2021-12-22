package org.domain.bugfixmanagement.service;

import lombok.AllArgsConstructor;
import org.domain.bugfixmanagement.repository.BugRepository;
import org.domain.bugfixmanagement.entity.Bug;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BugService {
    private final BugRepository bugRepository;

    public List<Bug> getAllBugs(){
        return bugRepository.findAll();
    }
}
