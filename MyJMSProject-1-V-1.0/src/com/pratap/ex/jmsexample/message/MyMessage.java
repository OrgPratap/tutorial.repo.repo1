package com.pratap.ex.jmsexample.message;

import java.util.Properties;

public class MyMessage extends AbstractQueueMessage{
	private Properties properties;

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
}
