package com.example.springjpademo.Controller;

import com.example.springjpademo.Entity.Branch;
import com.example.springjpademo.Repo.BranchRepo;
import com.example.springjpademo.Service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")
public class BranchController {
    @Autowired
    private BranchService branchService;


    @PostMapping("/create")
    public ResponseEntity<Long>createBranch(@RequestBody Branch branch){
        Long id = branchService.createBranch(branch);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Branch>>getBranch(){
        return ResponseEntity.ok(branchService.getBranch());
    }

}
