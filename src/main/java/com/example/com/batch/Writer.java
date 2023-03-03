package com.example.com.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.com.model.Employee;
import com.example.com.repo.EmployeeRepository;

@Component
public class Writer implements ItemWriter<Employee >
{
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public void write(List<?extends Employee>employee) throws Exception
	{
		System.out.println("EMployee Data Saved"+employee);
		employeeRepository.saveAll(employee);
	}

}