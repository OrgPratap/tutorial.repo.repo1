package com.pratap.ex.jmsexample.queue;

import com.pratap.ex.jmsexample.message.IQueueable;

public interface IQueueManager {
	public boolean queueMessage(IQueueable message);
	public boolean queueMessage(IQueueable message, boolean immediate);
}
