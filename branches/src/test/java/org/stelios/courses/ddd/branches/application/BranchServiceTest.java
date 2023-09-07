package org.stelios.courses.ddd.branches.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.stelios.courses.ddd.branches.application.errors.BranchAlreadyExistsException;
import org.stelios.courses.ddd.branches.repositories.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchServiceTest {

    @Mock
    private IOnlineBranchRepository onlineBranchRepository;
    @Mock
    private IPhysicalBranchRepository physicalBranchRepository;
    @InjectMocks
    private BranchService branchService;

    @Test
    void getAll_returnsBranches_whenSomeExist() {
        OnlineBranchEntity branchEntity1 = new OnlineBranchEntity("id1", "description1", "owner1", "city1");
        OnlineBranchEntity branchEntity2 = new OnlineBranchEntity("id2", "description2", "owner2", "city2");
        when(onlineBranchRepository.findAll()).thenReturn(List.of(branchEntity1, branchEntity2));
        PhysicalBranchEntity branchEntity3 = new PhysicalBranchEntity("id1", "description1", "owner1", "city1", "address1");
        PhysicalBranchEntity branchEntity4 = new PhysicalBranchEntity("id2", "description2", "owner2", "city2", "address2");
        when(physicalBranchRepository.findAll()).thenReturn(List.of(branchEntity3, branchEntity4));
        List<BranchEntity> expectedResponse = List.of(branchEntity1, branchEntity2, branchEntity3, branchEntity4);

        List<BranchEntity> actualResponse = branchService.getAll();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void getAll_returnsEmptyResponse_whenNoBranches() {
        when(onlineBranchRepository.findAll()).thenReturn(List.of());
        when(physicalBranchRepository.findAll()).thenReturn(List.of());
        List<BranchEntity> expectedResponse = List.of();

        List<BranchEntity> actualResponse = branchService.getAll();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void getAllPhysical_returnsBranches_whenSomeExist() {
        PhysicalBranchEntity branchEntity1 = new PhysicalBranchEntity("id1", "description1", "owner1", "city1", "address1");
        PhysicalBranchEntity branchEntity2 = new PhysicalBranchEntity("id2", "description2", "owner2", "city2", "address2");
        when(physicalBranchRepository.findAll()).thenReturn(List.of(branchEntity1, branchEntity2));
        List<BranchEntity> expectedResponse = List.of(branchEntity1, branchEntity2);

        List<PhysicalBranchEntity> actualResponse = branchService.getAllPhysicalBranches();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void getAllPhysical_returnsEmptyResponse_whenNoBranches() {
        when(physicalBranchRepository.findAll()).thenReturn(List.of());
        List<PhysicalBranchEntity> expectedResponse = List.of();

        List<PhysicalBranchEntity> actualResponse = branchService.getAllPhysicalBranches();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void getAllOnline_returnsBranches_whenSomeExist() {
        OnlineBranchEntity branchEntity1 = new OnlineBranchEntity("id1", "description1", "owner1", "city1");
        OnlineBranchEntity branchEntity2 = new OnlineBranchEntity("id2", "description2", "owner2", "city2");
        when(onlineBranchRepository.findAll()).thenReturn(List.of(branchEntity1, branchEntity2));
        List<BranchEntity> expectedResponse = List.of(branchEntity1, branchEntity2);

        List<OnlineBranchEntity> actualResponse = branchService.getAllOnlineBranches();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void getAllOnline_returnsEmptyResponse_whenNoBranches() {
        when(onlineBranchRepository.findAll()).thenReturn(List.of());
        List<OnlineBranchEntity> expectedResponse = List.of();

        List<OnlineBranchEntity> actualResponse = branchService.getAllOnlineBranches();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void saveOnlineBranch_returnsOk() {
        OnlineBranchEntity branchEntity1 = new OnlineBranchEntity("id1", "description1", "owner1", "city1");
        when(onlineBranchRepository.findById("id1")).thenReturn(Optional.empty());
        when(onlineBranchRepository.save(branchEntity1)).thenReturn(branchEntity1);

        assertDoesNotThrow(() -> branchService.save(branchEntity1));
    }

    @Test
    void saveOnlineBranch_throwsException_whenBranchExists() {
        OnlineBranchEntity branchEntity1 = new OnlineBranchEntity("id1", "description1", "owner1", "city1");
        when(onlineBranchRepository.findById("id1")).thenReturn(Optional.of(branchEntity1));

        assertThatThrownBy(() -> branchService.save(branchEntity1))
                .isInstanceOf(BranchAlreadyExistsException.class)
                .hasMessage("Trying to save existing branch");
    }

    @Test
    void savePhysicalBranch_returnsOk() {
        PhysicalBranchEntity branchEntity1 = new PhysicalBranchEntity("id1", "description1", "owner1", "city1", "address1");
        when(physicalBranchRepository.findById("id1")).thenReturn(Optional.empty());
        when(physicalBranchRepository.save(branchEntity1)).thenReturn(branchEntity1);

        assertDoesNotThrow(() -> branchService.save(branchEntity1));
    }

    @Test
    void savePhysicalBranch_throwsException_whenBranchExists() {
        PhysicalBranchEntity branchEntity1 = new PhysicalBranchEntity("id1", "description1", "owner1", "city1", "address1");
        when(physicalBranchRepository.findById("id1")).thenReturn(Optional.of(branchEntity1));

        assertThatThrownBy(() -> branchService.save(branchEntity1))
                .isInstanceOf(BranchAlreadyExistsException.class)
                .hasMessage("Trying to save existing branch");
    }
}
