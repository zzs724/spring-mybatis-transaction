package com.zzs.springboot.service.impl;

import com.zzs.springboot.bean.Department;
import com.zzs.springboot.bean.Employee;
import com.zzs.springboot.mapper.EmployeeMapper;
import com.zzs.springboot.service.DepartmentService;
import com.zzs.springboot.service.EmployeeService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private DepartmentService departmentService;


    @Override
    public Employee getEmpById(Integer id) {
        return employeeMapper.getEmpById(id);
    }

    @Override
    public void insertEmp(Employee employee) {
        Department department = new Department();
        department.setDepartmentName("测试事务");
        employeeMapper.insertEmp(employee);

        //1、EmployeeServiceImpl（简称A）中调用DepartmentServiceImpl（简称B）中的方法
        //   当A和B中的方法事务都是REQUIRED，B中抛异常回滚，A中catch住B的异常，A中事务还是会回滚，
        // 并抛出：UnexpectedRollbackException: Transaction rolled back because it has been marked as rollback-only
        //
//        try {
//            departmentService.insertDept(department);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
        try {
            ((EmployeeService) AopContext.currentProxy()).addEmp(employee);
        }catch (Exception e) {
            System.out.println(e);
        }


    }

    @Override
    public void addEmp(Employee employee) {
        employee.setEmail("zzzzzz");
        employeeMapper.insertEmp(employee);
        int i = 1/0;

    }
}
