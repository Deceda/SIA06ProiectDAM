package org.domain.bugfixmanagement.service;

import lombok.AllArgsConstructor;
import org.domain.bugfixmanagement.entity.Project;
import org.domain.bugfixmanagement.entity.User;
import org.domain.bugfixmanagement.repository.BugRepository;
import org.domain.bugfixmanagement.entity.Bug;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BugService {
    private final BugRepository bugRepository;

    public List<Bug> getAllBugs(){
        return bugRepository.findAll();
    }

    public Bug getBugById(Long id){
        return bugRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Bug not found"));
    }

    public List<Bug> getBugByProjectId(Long id){
        return bugRepository.getBugsByProject(id);
    }

    public void deleteBug(Long bugId) {
        bugRepository.findById(bugId).orElseThrow(() -> new IllegalArgumentException("Bug not found"));
        bugRepository.deleteById(bugId);
    }

    public Bug addBug(long projectId, long userId, String title, String description, String status) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        String current = formatter.format(date);

        return bugRepository.insert_value(projectId, userId, title, description, current, status);
    }

    public Bug updateBug(long id, String status){
        bugRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Bug not found"));
        return bugRepository.updateBug(id, status);
    }

    public Bug updateBugv2(Bug bug) {
        bug.setBugId(2L);
        bug.setBug_status("created");
        return bug;
    }
}
