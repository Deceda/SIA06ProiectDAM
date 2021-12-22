package org.domain.bugfixmanagement.controller;

import org.domain.bugfixmanagement.entity.Bug;
import org.domain.bugfixmanagement.service.BugService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BugsController {

    private final BugService bugService;

    public BugsController(BugService bugService) {
        this.bugService = bugService;
    }

    @GetMapping("/bugs")
    public List<Bug> bugs() {
        return bugService.getAllBugs();
    }
}
