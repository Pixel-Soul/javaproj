package com.business.messages;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.business.entity.Machine;
import com.business.entity.Production;
import com.business.repository.MachineRepository;
import com.business.repository.ProductionRepository;
import com.rabbitmq.client.AMQP.BasicProperties;


/**
 * Reperisce dati sensore da coda di RabbitMQ
 *
 */
@Component
public class ConsumerSensor implements Runnable{
	
	//@Autowired
	private MachineRepository machineRepo;
	//@Autowired
	private ProductionRepository productionRepo;
	
	private final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String URL = "jdbc:mysql://localhost:3306/machinedb";
	
	private final String QUEUE_NAME = "make";
	private final int INTERVAL = 5000;
	
	private ConnectionFactory factory;
	
	private HashMap<Integer, List<Integer>> prod = new HashMap<>();
	
	private int lastHour;
	
	
	private enum Cities{
		ROME,
		MILAN,
		BOLOGNA,
		FLORENCE
	}
	
	public ConsumerSensor(MachineRepository machineRepo, ProductionRepository productionRepo) {
		factory = new ConnectionFactory();
		factory.setHost("localhost");
		lastHour = getHour();
		this.machineRepo = machineRepo;
		this.productionRepo = productionRepo;
	}
	
	private String getRandomCity() {
		Cities[] cities = Cities.values();
		int size = cities.length;
		return cities[new Random().nextInt(size)].toString();
	}
	
	
	public void take() throws IOException, TimeoutException{
		
		try (
		Connection c = factory.newConnection();
		Channel ch = c.createChannel();
		){
		ch.queueDeclare(QUEUE_NAME, true, false, false, null);
		Consumer consumer = new DefaultConsumer(ch) {

			   @Override
			   public void handleDelivery(String consumerTag
			     , Envelope envelope
			     , BasicProperties properties, byte[] body)
			     throws IOException {
			    
					String message = new String(body, "UTF-8");
					Integer idMachine = Integer.parseInt(message.split("_")[0]);
				    Integer make = Integer.parseInt(message.split("_")[1]);
				    
				    prod.computeIfAbsent(idMachine, k-> new ArrayList<>()).add(make);
				    
				    //ch.basicAck(envelope.getDeliveryTag(), false);
		   		}
		};
		ch.basicConsume("make", true, consumer);
		}
	}
	
	
	private int getHour() {
		Date date = new Date();   // given date
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date);   // assigns calendar to given date
		return calendar.get(calendar.get(Calendar.HOUR));
	}
	
	
	public void sendToDB() {
		for(Integer id : prod.keySet()) {
			
			Integer makeAggr = prod.get(id).stream().reduce(Integer::sum).get();
			/*
			PreparedStatement ps = c.prepareStatement("INSERT INTO production (location, hoursofday, production_number, machine) values (?,?,?,?)");
			ps.setString(1, getRandomCity());
			ps.setInt(2, lastHour);			
			ps.setInt(3, makeAggr);
			ps.setInt(4, id);
			ps.executeUpdate();
			*/
			Production production = new Production();
			production.setHourOfDay(lastHour);
			production.setLocation(getRandomCity());
			
			Machine machine = machineRepo.findById(id).orElseThrow();
			production.setMachine(machine);
			production.setMake(makeAggr);
			
			productionRepo.save(production);
			}
		lastHour++;
    	prod = new HashMap<>();
	}
	
	
	@Override
	@PostConstruct
	public void run() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				try {
					take();
					
				} catch (IOException | TimeoutException e) {
					e.printStackTrace();
				}
			}
		}, 0, INTERVAL);
		sendToDB();
	}
}
