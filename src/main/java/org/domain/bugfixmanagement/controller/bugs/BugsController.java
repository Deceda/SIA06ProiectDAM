package org.domain.bugfixmanagement.controller.bugs;

import org.domain.bugfixmanagement.entity.Bug;
import org.domain.bugfixmanagement.service.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
public class BugsController {

    private final BugService bugService;

    @Autowired
    public BugsController(BugService bugService) {
        this.bugService = bugService;
    }

    @GetMapping("/bugs")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Bug>> bugs() {
        return ResponseEntity.ok().body(bugService.getAllBugs());
    }

    @GetMapping("/get-bug")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Bug> getBugById(@RequestParam(name = "bug_id") Long id){
        return ResponseEntity.ok().body(bugService.getBugById(id));
    }

    @GetMapping("/get-bug-by-projectId")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Bug>> getBugByProjectId(@RequestParam(name = "project_id") Long id){
        return ResponseEntity.ok().body(bugService.getBugByProjectId(id));
    }

    @PostMapping("/add-bug")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Bug> addBug(@RequestBody BugsRequests request) {
        if (request.validateParameters()) {
            Bug createdBug =  bugService.addBug(request.getProject_id(), request.getUser_id(), request.getTitle(), request.getBug_description(), request.getBug_status());

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdBug.getBugId())
                    .toUri();

            return ResponseEntity.created(uri).body(createdBug);
        }
        return null;
    }

    @PutMapping("/update-bug-status-by-id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Bug> updateBugStatus(@RequestParam(name = "bug_id") long id, String status){
        return ResponseEntity.ok().body(bugService.updateBug(id, status));
    }

    @PutMapping("/putMapping")
    public ResponseEntity<Bug> putExample(@RequestBody Bug bug) {
        bug = bugService.updateBugv2(bug);
        return new ResponseEntity<>(bug, HttpStatus.OK);
    }

    @DeleteMapping("/delete-bug")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBug(@RequestParam(name = "bug_id") Long id){
        bugService.deleteBug(id);
    }
}


