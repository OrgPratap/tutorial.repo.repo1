package com.pratap.ex.jmsexample.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;

import oracle.jdbc.pool.OracleDataSource;
import oracle.jms.AQjmsFactory;

public class MyMessageConsumer {
	
	public void consumeMessage(OracleDataSource oracleDataSource) throws JMSException{
		ConnectionFactory connectionFactory = AQjmsFactory.getQueueConnectionFactory(oracleDataSource);

		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue loggerQueue = session.createQueue("priorityqueue");
		MessageConsumer consumer = session.createConsumer(loggerQueue);

		int count = 0;
		while (true) {
		    connection.start();
		    Message message = consumer.receive(1000);
		    if (message == null) {
		        break;
		    }
		    count++;
		    System.out.println(count + ".\tPriority [" + message.getJMSPriority() + "]");
		}
		connection.close();
	}

}
