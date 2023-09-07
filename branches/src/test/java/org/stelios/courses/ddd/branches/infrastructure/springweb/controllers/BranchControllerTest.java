package org.stelios.courses.ddd.branches.infrastructure.springweb.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.stelios.courses.ddd.branches.application.BranchService;
import org.stelios.courses.ddd.branches.repositories.BranchEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchControllerTest {

    @Mock
    private BranchService branchService;
    @InjectMocks
    private BranchController controller;

    @Test
    void getAllBranches_returnsBranches_whenSomeExist() {
        BranchEntity branch1 = new BranchEntity("id1", "description1", "owner1", "city1");
        BranchEntity branch2 = new BranchEntity("id2", "description2", "owner2", "city2");
        when(branchService.getAll()).thenReturn(List.of(branch1, branch2));
        ResponseEntity<List<BranchEntity>> expectedResponse = ResponseEntity.ok(List.of(branch1, branch2));

        ResponseEntity<List<BranchEntity>> actualResponse = controller.getAllBranches();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void getAllBranches_returnsEmptyResponse_whenNoBranches() {
        when(branchService.getAll()).thenReturn(List.of());
        ResponseEntity<List<BranchEntity>> expectedResponse = ResponseEntity.ok(List.of());

        ResponseEntity<List<BranchEntity>> actualResponse = controller.getAllBranches();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }
}
