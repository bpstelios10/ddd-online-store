package org.stelios.courses.ddd.branches.infrastructure.springweb.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.stelios.courses.ddd.branches.application.BranchService;
import org.stelios.courses.ddd.branches.application.errors.BranchAlreadyExistsException;
import org.stelios.courses.ddd.branches.repositories.OnlineBranchEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OnlineBranchControllerTest {

    @Mock
    private BranchService branchService;
    @InjectMocks
    private OnlineBranchController controller;

    @Test
    void getAllBranches_returnsBranches_whenSomeExist() {
        OnlineBranchEntity branch1 = new OnlineBranchEntity("id1", "description1", "owner1", "city1");
        OnlineBranchEntity branch2 = new OnlineBranchEntity("id2", "description2", "owner2", "city2");
        when(branchService.getAllOnlineBranches()).thenReturn(List.of(branch1, branch2));
        ResponseEntity<List<OnlineBranchEntity>> expectedResponse = ResponseEntity.ok(List.of(branch1, branch2));

        ResponseEntity<List<OnlineBranchEntity>> actualResponse = controller.getAllBranches();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void getAllBranches_returnsEmptyResponse_whenNoBranches() {
        when(branchService.getAllOnlineBranches()).thenReturn(List.of());
        ResponseEntity<List<OnlineBranchEntity>> expectedResponse = ResponseEntity.ok(List.of());

        ResponseEntity<List<OnlineBranchEntity>> actualResponse = controller.getAllBranches();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void create_returnsOK_whenBranchDoesntExist() throws BranchAlreadyExistsException {
        OnlineBranchController.BranchCreationRequestBody requestBody =
                new OnlineBranchController.BranchCreationRequestBody("id1", "description1", "owner1", "city1");
        OnlineBranchEntity branch = new OnlineBranchEntity("id1", "description1", "owner1", "city1");
        doNothing().when(branchService).save(branch);

        ResponseEntity<Void> actualResponse = controller.create(requestBody);

        assertThat(actualResponse.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void create_throwsException_whenBranchExists() throws BranchAlreadyExistsException {
        OnlineBranchController.BranchCreationRequestBody requestBody =
                new OnlineBranchController.BranchCreationRequestBody("id1", "description1", "owner1", "city1");
        OnlineBranchEntity branch = new OnlineBranchEntity("id1", "description1", "owner1", "city1");
        doThrow(BranchAlreadyExistsException.class).when(branchService).save(branch);

        assertThatThrownBy(() -> controller.create(requestBody))
                .isInstanceOf(BranchAlreadyExistsException.class);
    }
}
