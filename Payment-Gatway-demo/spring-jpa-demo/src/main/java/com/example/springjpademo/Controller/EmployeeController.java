package com.example.springjpademo.Controller;

import com.example.springjpademo.Dto.Employeedta;
import com.example.springjpademo.Entity.Employee;
import com.example.springjpademo.Exception.IdCardException;
import com.example.springjpademo.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    ResponseEntity<Long>createEmp(@RequestBody Employeedta employee) throws IdCardException {
        Long id = employeeService.createEmp(employee);
        return ResponseEntity.ok(id);

    }
    @GetMapping
    ResponseEntity<List<Employee>>getAll(){
        return ResponseEntity.ok(employeeService.getAll());
    }




}
