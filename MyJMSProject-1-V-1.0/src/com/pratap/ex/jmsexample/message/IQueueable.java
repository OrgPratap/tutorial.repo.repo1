package com.pratap.ex.jmsexample.message;

import java.util.Properties;

public interface IQueueable {
	
	Properties getProperties();
	String getQueueName();
}
