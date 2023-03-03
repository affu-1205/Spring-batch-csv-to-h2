package com.example.com.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.com.model.Employee;
import org.springframework.core.io.Resource;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {	
	
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory,
					StepBuilderFactory stepBuilderFactory,
					ItemReader<Employee> itemReader,
					ItemProcessor<Employee, Employee> itemProcessor,
					ItemWriter<Employee> itemWriter)
	    { 
		    Step step=stepBuilderFactory.get("ETL-JOB")
		    			.<Employee,Employee>chunk(5)
		    			.reader(itemReader)
		    			.processor(itemProcessor)
		    			.writer(itemWriter)
		    			.build();
		    
		    return jobBuilderFactory.get("ETL-JOB")
		    		.incrementer(new RunIdIncrementer())
		    		.start(step)
		    		.build();
	    }
		
	@Bean
	public FlatFileItemReader<Employee>itemReader(@Value("${input1}") Resource resource)
	{
		FlatFileItemReader<Employee> flatFileItemReader=new FlatFileItemReader<>()	;
		flatFileItemReader.setResource(resource);
		flatFileItemReader.setName("CSV_Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}
	
	@Bean 
	public LineMapper<Employee> lineMapper()
	{
		DefaultLineMapper<Employee> defaultLineMapper=new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer= new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("employeeId","employeeName");
		
		BeanWrapperFieldSetMapper<Employee> fieldSetMapper =new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Employee.class);
		
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		
		return defaultLineMapper;
		
	}
}