package com.zm.consumer;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.zm.constant.Constants;
import com.zm.model.Message;
import com.zm.model.MessageKey;
import com.zm.model.OutputDisplay;
import com.zm.utils.DateTimeUtils;
import com.zm.utils.Display;

/**
 * @author Zahir Mandsaurwala
 * 
 * Consumer class for processing messages from the Queue
 *
 */
public class Consumer implements Runnable {

	private ConcurrentHashMap<MessageKey, ConcurrentLinkedQueue<Message>> sharedMessages;
	private String threadName;
	private AtomicInteger numOfmessages;
	private AtomicBoolean moreData;
	private CountDownLatch countDownLatch;
	
	private static final Logger LOG = Logger.getLogger(Consumer.class);
	
	private static final ReentrantLock lock = new ReentrantLock();
	
	/**
	 * @param sharedMessages
	 * @param threadName
	 * @param numOfmessages
	 * @param moreData
	 * @param countDownLatch
	 */
	public Consumer(ConcurrentHashMap<MessageKey, ConcurrentLinkedQueue<Message>> sharedMessages, 
					String threadName, AtomicInteger numOfmessages, AtomicBoolean moreData, CountDownLatch countDownLatch) {
		super();
		this.sharedMessages = sharedMessages;
		this.threadName = threadName;
		this.numOfmessages = numOfmessages;
		this.moreData = moreData;
		this.countDownLatch = countDownLatch;
	}

	/**
	 * Process messages from the Queue
	 */
	public void run() {
		while (moreData.get() || numOfmessages.get() > 0) {
			MessageKey messageKey = getNextMessageQueue(sharedMessages);
			processMessages(messageKey);
		}
		countDownLatch.countDown();
	}

	/**
	 * @param msg
	 * @return OutputDisplay
	 * 
	 * Process the message
	 * 
	 */
	private OutputDisplay readMessage(Message msg) {

		OutputDisplay displayMsg = new OutputDisplay();
		displayMsg.setThreadName(threadName);
		displayMsg.setStartTime(DateTimeUtils.getCurrentTime(Constants.DATE_TIME_FORMAT));
				
		try {
			Thread.sleep(msg.getSleepTime());
		} catch (InterruptedException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		
		displayMsg.setEndTime(DateTimeUtils.getCurrentTime(Constants.DATE_TIME_FORMAT));
		
		return displayMsg;
	}

	/**
	 * @param sharedMessages
	 * @return MessageKey
	 * 
	 * Fetch the next available un-processed Message Key from the Map
	 * Remove the Message Key, once all its messages have been processed
	 * 
	 */
	private MessageKey getNextMessageQueue(
			ConcurrentHashMap<MessageKey, ConcurrentLinkedQueue<Message>> sharedMessages) {

		MessageKey messageKey = null;
		
		lock.lock();
			try {
				Iterator<MessageKey> itSharedMessages = sharedMessages.keySet().iterator();

				while (itSharedMessages.hasNext()) {

					messageKey = itSharedMessages.next();
					//fetch un-processed message key
					if (messageKey.getIsProcessing().equals(Constants.NO)) {
						messageKey.setIsProcessing(Constants.YES);
						
						//remove the message Key if all its messages are processed
						if(!moreData.get() && null!= sharedMessages.get(messageKey) && sharedMessages.get(messageKey).isEmpty()) {
							sharedMessages.remove(messageKey);
							return null;
						}
						return messageKey;
					}
				}
			} catch (Exception e) {
				LOG.error(e.getMessage());
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		return null;
	}
		
	/**
	 * @param msgKey
	 * 
	 * Fetch un-processed messages for the input message key and process it
	 * 
	 */
	private void processMessages(MessageKey msgKey) {

		if (null != msgKey) {

			ConcurrentLinkedQueue<Message> messagesQueue = sharedMessages.get(msgKey);

			if (null != messagesQueue) {
				synchronized (messagesQueue) {
					Iterator<Message> itMessagesQueue = messagesQueue.iterator();
					
					//While there are available messages for the Message Key
					while (itMessagesQueue.hasNext()) {
						Message msg = (Message) itMessagesQueue.next();
						
						//Process the next un-processed message
						if (msg.getIS_PROCESSED().equals(Constants.NO)) {
							OutputDisplay displayMsg = readMessage(msg);
							Display.consumerDisplayMsg(msg, displayMsg, threadName);
							msg.setIS_PROCESSED(Constants.YES);
							itMessagesQueue.remove();
							numOfmessages.decrementAndGet();
						}
					}
					//Make the message Key available to be processed by another Consumers once new messages are added by the producer
					msgKey.setIsProcessing(Constants.NO);
				}
			} 
		}
	}
}
