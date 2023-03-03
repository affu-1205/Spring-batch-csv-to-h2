package com.example.com.batch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.com.model.Employee;
@Component
public class Processor implements ItemProcessor<Employee, Employee>
{
	/*public static final Map<String,String> employeeName =new HashMap<>();

	
	 public Processor()
		{
		 employeeName.put("affu", "a");
		 employeeName.put("akki", "b");
			
		}

	@Override
	public Employee process(Employee employee) throws Exception {
		
		String name=employee.getEmployeeName();
		System.out.println(name);
		String Update_Name=employeeName.get(name);
		System.out.println(Update_Name);

		employee.setEmployeeName(Update_Name);
		System.out.println("Processor"+employee);
		
		
		 
		return employee;
	}*/
	@Override
	public Employee process(Employee employee) throws Exception {
		return employee;
	}	
	
}