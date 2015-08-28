package com.pratap.ex.jmsexample.queue;

import com.pratap.ex.jmsexample.message.IQueueable;

public class MyQueueManager implements IQueueManager{

	@Override
	public boolean queueMessage(IQueueable message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean queueMessage(IQueueable message, boolean immediate) {
		// TODO Auto-generated method stub
		return false;
	}

}
