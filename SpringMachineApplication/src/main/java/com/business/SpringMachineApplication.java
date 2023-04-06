package com.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.business.entity.Machine;
import com.business.entity.Production;
import com.business.repository.MachineRepository;
import com.business.repository.ProductionRepository;

/**
 * 
 * Main Class per salvare lo stato e runnare la Spirng Application
 *
 */
@SpringBootApplication
public class SpringMachineApplication implements CommandLineRunner{
	
	@Autowired
	private MachineRepository machineRepo;
	
	@Autowired
	private ProductionRepository productionRepo;


	public static void main(String[] args) {
		SpringApplication.run(SpringMachineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("===========> START APP");
		Machine m1 = new Machine("LSD23431OP");
		Machine m2 = new Machine("LLOPRER13P");
		
		machineRepo.save(m1);
		machineRepo.save(m2);
		
	}

}
