package org.stelios.courses.ddd.branches.infrastructure.springweb.controllers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.stelios.courses.ddd.branches.application.BranchService;
import org.stelios.courses.ddd.branches.application.errors.BranchAlreadyExistsException;
import org.stelios.courses.ddd.branches.repositories.PhysicalBranchEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/branches/physical")
public class PhysicalBranchController {

    private final BranchService branchService;

    public PhysicalBranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping
    public ResponseEntity<List<PhysicalBranchEntity>> getAllBranches() {
        log.info("request for all branches");

        return ResponseEntity.ok(branchService.getAllPhysicalBranches());
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody BranchCreationRequestBody requestBody) throws BranchAlreadyExistsException {
        log.info("request:" + requestBody);

        PhysicalBranchEntity onlineBranch =
                new PhysicalBranchEntity(requestBody.getId(), requestBody.getDescription(), requestBody.getOwner(), requestBody.getCity(), requestBody.getAddress());
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
        @NotBlank(message = "Branch address may not be missing or empty")
        private final String address;
    }
}
