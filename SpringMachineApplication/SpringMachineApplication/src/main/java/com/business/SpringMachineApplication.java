package com.business;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.business.messages.ConsumerSensor;
import com.business.messages.ProducerSensor;
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
		System.out.println("===========> APP STARTED");
	
		/*
		//Creazione delle macchine (Eseguire solo prima volta con DB vuoto)
		Machine m1 = new Machine("LSD23431OP");
		Machine m2 = new Machine("LLOPRER13P");
		Machine m3 = new Machine("D08ASUJQ3H");
		Machine m4 = new Machine("PDAO34IDFG");
		
		machineRepo.save(m1);
		machineRepo.save(m2);
		machineRepo.save(m3);
		machineRepo.save(m4);
		*/
		
		ProducerSensor producerSensor = new ProducerSensor();
		Thread producer = new Thread(producerSensor);
		producer.start();
		
		ConsumerSensor consumerSensor = new ConsumerSensor(machineRepo,productionRepo);
		Thread consumer = new Thread(consumerSensor);
		consumer.start();
		
		
		
		
		
	}

}
