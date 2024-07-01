package com.example.springjpademo.Service;

import com.example.springjpademo.Dto.Employeedta;
import com.example.springjpademo.Entity.Address;
import com.example.springjpademo.Entity.Branch;
import com.example.springjpademo.Entity.Employee;
import com.example.springjpademo.Exception.IdCardException;
import com.example.springjpademo.Exception.LaptopException;
import com.example.springjpademo.Repo.BranchRepo;
import com.example.springjpademo.Repo.EmployeeRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private BranchRepo branchRepo;

    private boolean idCard=true;

    private boolean laptop=true;

     @Transactional
    public Long createEmp(Employeedta employeereq) throws IdCardException{
        Employee employee = new Employee();
        employee.setName(employeereq.getEmpName());
        employee.setEmail(employeereq.getEmpEmail());
        Address address = new Address();
                 address.setLine1(employeereq.getLine1());
                address.setLine2(employeereq.getLine2());
                address.setCity(employeereq.getCity());
                employee.setAddress(address);
           Branch branch =branchRepo.findById(employeereq.getBranchId()).get();

           employee.setBranch(branch);
        employee = employeeRepo.save(employee);

        if(!laptop==false){
            throw new LaptopException("laptop not allocated");

        }
        if(!idCard==false){
            throw new IdCardException("id not allocated");
        }



        return employee.getId();

    }

    public List<Employee> getAll(){
        return employeeRepo.findAll();
    }
}
