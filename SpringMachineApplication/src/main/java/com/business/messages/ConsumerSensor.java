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
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;


/**
 * Reperisce dati sensore da coda di RabbitMQ
 *
 */
public class ConsumerSensor {
	
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/machinedb";
	
	private static final Integer MAX = 10000;
	
	private static HashMap<Integer, List<Integer>> prod = new HashMap<>();
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection c = factory.newConnection();
		Channel ch = c.createChannel();
		ch.queueDeclare("make", true, false, false, null);
		
		
		
		Consumer consumer = new DefaultConsumer(ch) {

			   @Override
			   public void handleDelivery(String consumerTag
			     , Envelope envelope
			     , BasicProperties properties, byte[] body)
			     throws IOException {
			    
				String message = new String(body, "UTF-8");
				System.out.println(message);

				Integer idMachine = Integer.parseInt(message.split("_")[0]);
			    Integer make = Integer.parseInt(message.split("_")[1]);
			    
			    if(prod.containsKey(idMachine)) {
			    	prod.get(idMachine).add(make);
			    } else {
			    	ArrayList l = new ArrayList<>();
			    	l.add(make);
			    	prod.put(idMachine, l);
			    }
			    
			    
			    /*
			     * Controlliamo che la collezione venga svuotata ogni ora
			     */
			    
			    if(true) {
			    	System.out.println("=========> Dentro IF- Invio a DB");
			    	try ( java.sql.Connection c =  DriverManager.getConnection(URL, "root", "password");
							 Statement s = c.createStatement();
							){
					    	
	    			
	    			for(Integer id:prod.keySet()) {
	    				Integer makeAggr = prod.get(id).stream().reduce(Integer::sum).get();
	    				
	    				PreparedStatement ps = c.prepareStatement("INSERT INTO productions (location, hourofday, make, machineid) values (?,?,?,?)");
						ps.setString(1, "Bologna");

						//Prendiamo ora del giorno
						Date date = new Date();   // given date
						Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
						calendar.setTime(date);   // assigns calendar to given date
						ps.setInt(2, calendar.get(calendar.get(Calendar.HOUR)));
						
						ps.setInt(3, makeAggr);

						ps.setInt(4, id);
						
						
						int n = ps.executeUpdate();
						
				    	//if(n>0)
				    		//ch.basicAck(envelope.getDeliveryTag(), true);
	    			}
	    		
			    		} catch (SQLException e) {
							e.printStackTrace();
						}
			    	//prod = new HashMap<>();
			    }
			   }
			  };
			  
			  ch.basicConsume("make", true, consumer);
			  Thread.sleep(60000);
			  ch.close();
			  c.close(); 
	  
	}
}
