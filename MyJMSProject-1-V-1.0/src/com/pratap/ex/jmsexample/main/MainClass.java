package com.pratap.ex.jmsexample.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.sql.DataSource;

import oracle.jms.AQjmsFactory;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.pratap.ex.jmsexample.db.MyDbFactory;
import com.pratap.ex.jmsexample.message.MyMessage;
import com.pratap.ex.jmsexample.queue.MyQueueManager;
import com.pratap.ex.jmsexample.sender.MyMessageSender;
import com.pratap.ex.jmsexample1.consumer.QueueSend;

public class MainClass {

	public static void main(String[] args) throws SQLException {
		
		MyDbFactory myDbFactory = new MyDbFactory();
		myDbFactory.createOracleDataSource("jdbc:oracle:thin:@localhost:1521:XE", "system", "root");
		DataSource dataSource = myDbFactory.getOracleDataSource();
		Connection connection = dataSource.getConnection();
		//create random unique table name
		String priorityQueueAndTableName = "myQueue";//RandomStringUtils.random( 16, true, true );
		System.out.println("-----> priorityQueueAndTableName : [ "+ priorityQueueAndTableName +" ]");
		
		//Create a queue table
		connection.prepareCall("{call dbms_aqadm.create_queue_table ( queue_table => '"+ priorityQueueAndTableName +"', sort_list => 'PRIORITY,ENQ_TIME', queue_payload_type => 'sys.aq$_jms_map_message', compatible => '8.1.0')}").execute();

		//create a queue
		connection.prepareCall("{call dbms_aqadm.create_queue ( queue_name => '"+ priorityQueueAndTableName +"', queue_table => '"+ priorityQueueAndTableName +"')}").execute();

		//Start the queue
		connection.prepareCall("{call dbms_aqadm.start_queue (queue_name => '"+ priorityQueueAndTableName +"')}").execute();
		
		//-----------------------------------------------------------------------------------------
		
		try{
			
			final int priority =3;
			
			ConnectionFactory connectionFactory = null;
			try {
				connectionFactory = AQjmsFactory.getQueueConnectionFactory(dataSource);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
			jmsTemplate.setExplicitQosEnabled(true);
			MessageCreator messageCreator = new MessageCreator() {
			        @Override
			        public Message createMessage(Session session) throws JMSException {
			            MapMessage message = session.createMapMessage();
			            // ... set some properties
			            message.setJMSPriority(priority);
			            return message;
			        }
			};
			jmsTemplate.setPriority(priority);
			jmsTemplate.setDefaultDestinationName(priorityQueueAndTableName);
			jmsTemplate.setConnectionFactory(connectionFactory);
			jmsTemplate.send(priorityQueueAndTableName, messageCreator);
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		/*
		try {
			
			Properties properties = new Properties();
			properties.put("qname", "myQueue");
			properties.put("content", "message content..");
			
			MyMessage message = new MyMessage();
			message.setProperties(properties);
			
			//MyQueueManager myQueueManager = new MyQueueManager();
			
			
			MyMessageSender messageSender = new MyMessageSender();
			messageSender.setMessage(message);
			messageSender.setPriority(1);
			messageSender.sendMyMessage(dataSource);
			
			
			
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
		try {
			Thread.currentThread().sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//-----------------------------------------------------------------------------------------		
		try {
			connection.prepareCall("{call dbms_aqadm.stop_queue(queue_name => '"+ priorityQueueAndTableName +"')}").execute();
			connection.prepareCall("{call DBMS_AQADM.DROP_QUEUE(queue_name => '"+ priorityQueueAndTableName +"')}").execute();
			connection.prepareCall("{call DBMS_AQADM.DROP_QUEUE_TABLE(queue_table => '"+ priorityQueueAndTableName +"')}").execute();
		} finally {
			connection.close();
			System.out.println("-->FINISH..");
		}
		
		

	}

}
