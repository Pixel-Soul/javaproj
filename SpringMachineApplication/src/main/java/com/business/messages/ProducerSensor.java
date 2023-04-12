package com.business.messages;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.business.entity.Machine;
import com.business.repository.MachineRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Invia dati sensore a cosa di RabbitMQ
 * 
 */
@Component
public class ProducerSensor implements Runnable{
	
	private final String QUEUE_NAME = "make";
	private final int INTERVAL = 5000;
	
	@Autowired
	public MachineRepository repo;
	
	private ConnectionFactory factory;
	
	private static Iterable<Machine> machineIterable;
	
	public ProducerSensor() throws IOException, TimeoutException {
		factory = new ConnectionFactory();
		factory.setHost("localhost");
	}
	
	@PostConstruct
	public void init() {
		machineIterable = repo.findAll();
	}
	
	public void send(Integer machineId) throws IOException, TimeoutException {
		
		try (
		Connection c = factory.newConnection();
		Channel ch = c.createChannel();
		){
		Random random = new Random();
		Integer maked = random.nextInt(100);
		String payload =  machineId + "_" + maked;
		ch.basicPublish("", QUEUE_NAME, null, payload.getBytes("UTF-8"));
		}
	}
	
	@Override
	public void run(){
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				machineIterable.forEach(machine -> {
					try {
						send(machine.getMachineid());
					} catch (IOException | TimeoutException e) {
						e.printStackTrace();
					}
				});
				
			}
		} , 0, INTERVAL);  
	}
}
