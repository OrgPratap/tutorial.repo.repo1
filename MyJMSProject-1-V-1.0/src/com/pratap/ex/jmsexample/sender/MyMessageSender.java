package com.pratap.ex.jmsexample.sender;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.sql.DataSource;

import oracle.jms.AQjmsFactory;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.pratap.ex.jmsexample.message.IQueueable;

public class MyMessageSender {
	
	private IQueueable message;
	private int priority=3;
	private JmsTemplate jmsTemplate;
	
	public IQueueable getMessage() {
		return message;
	}

	public void setMessage(IQueueable message) {
		this.message = message;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void sendMyMessage(DataSource dataSource) throws JMSException{
		initJms(dataSource);
		ConnectionFactory connectionFactory = AQjmsFactory.getQueueConnectionFactory(dataSource);
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
		
		//jmsTemplate.send("myQueue", messageCreator);
	}

	private void initJms(DataSource dataSource) {
		try {
			ConnectionFactory connectionFactory = AQjmsFactory.getQueueConnectionFactory(dataSource);
			jmsTemplate = new JmsTemplate(connectionFactory);
			jmsTemplate.setExplicitQosEnabled(true);
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
