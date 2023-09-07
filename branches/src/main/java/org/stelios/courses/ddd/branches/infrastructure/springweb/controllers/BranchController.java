package org.stelios.courses.ddd.branches.infrastructure.springweb.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stelios.courses.ddd.branches.application.BranchService;
import org.stelios.courses.ddd.branches.repositories.BranchEntity;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/branches")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping
    public ResponseEntity<List<BranchEntity>> getAllBranches() {
        log.info("request for all branches");

        return ResponseEntity.ok(branchService.getAll());
    }
}
