package com.example.springjpademo.Service;

import com.example.springjpademo.Entity.Branch;
import com.example.springjpademo.Repo.BranchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {

    @Autowired
    private BranchRepo branchRepo;

    public Long createBranch(Branch branch){
        branch= branchRepo.save(branch);

        return branch.getId();

    }
    public List<Branch> getBranch(){
        return branchRepo.findAll();
    }



}
