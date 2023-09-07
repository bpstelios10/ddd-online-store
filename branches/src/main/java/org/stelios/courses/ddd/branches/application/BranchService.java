package org.stelios.courses.ddd.branches.application;

import org.springframework.stereotype.Component;
import org.stelios.courses.ddd.branches.application.errors.BranchAlreadyExistsException;
import org.stelios.courses.ddd.branches.repositories.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class BranchService {

    private final IOnlineBranchRepository onlineBranchRepository;
    private final IPhysicalBranchRepository physicalBranchRepository;

    public BranchService(IOnlineBranchRepository onlineBranchRepository, IPhysicalBranchRepository physicalBranchRepository) {
        this.onlineBranchRepository = onlineBranchRepository;
        this.physicalBranchRepository = physicalBranchRepository;
    }

    public List<BranchEntity> getAll() {
        ArrayList<BranchEntity> branches = new ArrayList<>(onlineBranchRepository.findAll());
        branches.addAll(physicalBranchRepository.findAll());

        return branches;
    }

    public List<OnlineBranchEntity> getAllOnlineBranches() {
        return onlineBranchRepository.findAll();
    }

    public List<PhysicalBranchEntity> getAllPhysicalBranches() {
        return physicalBranchRepository.findAll();
    }

    public void save(OnlineBranchEntity branch) throws BranchAlreadyExistsException {
        if (onlineBranchRepository.findById(branch.getId()).isPresent()) throw new BranchAlreadyExistsException();

        onlineBranchRepository.save(branch);
    }

    public void save(PhysicalBranchEntity branch) throws BranchAlreadyExistsException {
        if (physicalBranchRepository.findById(branch.getId()).isPresent()) throw new BranchAlreadyExistsException();

        physicalBranchRepository.save(branch);
    }
}
