package org.stelios.courses.ddd.branches.infrastructure.springweb.controllers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.stelios.courses.ddd.branches.application.BranchService;
import org.stelios.courses.ddd.branches.application.errors.BranchAlreadyExistsException;
import org.stelios.courses.ddd.branches.repositories.OnlineBranchEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/branches/online")
public class OnlineBranchController {

    private final BranchService branchService;

    public OnlineBranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping
    public ResponseEntity<List<OnlineBranchEntity>> getAllBranches() {
        log.info("request for all branches");

        return ResponseEntity.ok(branchService.getAllOnlineBranches());
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody BranchCreationRequestBody requestBody) throws BranchAlreadyExistsException {
        log.info("request:" + requestBody);

        OnlineBranchEntity onlineBranch =
                new OnlineBranchEntity(requestBody.getId(), requestBody.getDescription(), requestBody.getOwner(), requestBody.getCity());
        branchService.save(onlineBranch);


        return ResponseEntity.ok().build();
    }

    @Data
    static class BranchCreationRequestBody {
        @NotBlank(message = "Branch ID may not be missing or empty")
        private final String id;
        @NotBlank(message = "Branch description may not be missing or empty")
        private final String description;
        @NotBlank(message = "Branch owner may not be missing or empty")
        private final String owner;
        @NotBlank(message = "Branch city may not be missing or empty")
        private final String city;
    }
}
