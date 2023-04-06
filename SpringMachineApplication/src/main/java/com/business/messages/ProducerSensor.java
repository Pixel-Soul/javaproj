package com.business.messages;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

import com.business.entity.Machine;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Invia dati sensore a cosa di RabbitMQ
 * 
 */
public class ProducerSensor {
	
	public static void main(String[] args) throws UnsupportedEncodingException, IOException, InterruptedException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		  factory.setHost("localhost");
		  Connection c = factory.newConnection();
		  Channel ch = c.createChannel();
		  
		  Machine m1 = new Machine("LSD23431OP");
		  Machine m2 = new Machine("LLOPRER13P");
		  
		  boolean stop = false;
		  while(!stop) {
			  Integer make1 = (int)(Math.random() * ((99) + 1));
			  String payload1 =  m1.getMachineid()+"_"+make1;
			  ch.basicPublish("", "make", null, payload1.getBytes("UTF-8"));
			  Thread.sleep(1000);
			  
			  Integer make2 = (int)(Math.random() * ((99) + 1));
			  String payload2 =  m2.getMachineid()+"_"+make2;
			  ch.basicPublish("", "make", null, payload2.getBytes("UTF-8"));
			  Thread.sleep(1000);
		  }
		  ch.close();
		  c.close(); 
	}

}
