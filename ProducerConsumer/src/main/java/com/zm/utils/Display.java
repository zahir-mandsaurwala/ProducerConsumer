package com.zm.utils;

import com.zm.constant.Constants;
import com.zm.model.Message;
import com.zm.model.OutputDisplay;

/**
 * @author Zahir Mandsaurwala
 * 
 * Class to display the messages to the console
 *
 */
public final class Display {

	/**
	 * @param msg
	 * @param displayMsg
	 * @param threadName
	 * 
	 * Display the Consumer thread processed message
	 */
	public static void consumerDisplayMsg(Message msg, OutputDisplay displayMsg, String threadName) {
		
		StringBuffer strBuff = new StringBuffer();
		
		strBuff.append(msg.getEntireMessage()).append(Constants.SEMICOLON).append(Constants.SPACE)
			.append(Constants.THREAD).append(threadName).append(Constants.SEMICOLON).append(Constants.SPACE)
			.append(Constants.START).append(displayMsg.getStartTime()).append(Constants.SEMICOLON).append(Constants.SPACE)
			.append(Constants.END).append(displayMsg.getEndTime());
		
		System.out.println(strBuff.toString());
	}
	
	/**
	 * @param numOfConsumers
	 * @param messageFilePath
	 * 
	 * Display the start message
	 * 
	 */
	public static void startMsgDisplay(int numOfConsumers, String messageFilePath) {
		
		StringBuffer strBuff = new StringBuffer();
		
		strBuff.append(DateTimeUtils.getCurrentTime(Constants.DATE_TIME_FORMAT))
			.append(Constants.SPACE).append(Constants.HYPHEN).append(Constants.SPACE).append(Constants.STARTING)
			.append(Constants.SPACE).append(Constants.HYPHEN).append(Constants.SPACE)
			.append(Constants.CONSUMERS).append(Constants.SPACE).append(numOfConsumers)
			.append(Constants.SEMICOLON).append(Constants.SPACE).append(Constants.FILE).append(Constants.SPACE).append(messageFilePath);
			
		System.out.println(strBuff.toString());
		
	}
	
	/**
	 * @param msg
	 * @param threadName
	 * 
	 * Display the producer sleep message
	 */
	public static void producerSleepDisplay(Message msg, String threadName) {
		
		StringBuffer strBuff = new StringBuffer();
		
		strBuff.append(Constants.THREAD).append(threadName).append(Constants.SEMICOLON).append(Constants.SPACE)
			.append(Constants.PRODUCER_THREAD_SLEEPING).append(msg.getSleepTime()).append(Constants.MILII_SECS);
		
		System.out.println(strBuff.toString());
	}

	/**
	 *  Display the End message
	 */
	public static void endMsgDisplay() {
		
		StringBuffer strBuff = new StringBuffer();
		
		strBuff.append(DateTimeUtils.getCurrentTime(Constants.DATE_TIME_FORMAT))
			.append(Constants.SPACE).append(Constants.HYPHEN).append(Constants.SPACE).append(Constants.END1);
		
		System.out.println(strBuff.toString());
	}
}
