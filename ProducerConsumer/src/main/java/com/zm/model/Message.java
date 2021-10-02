package com.zm.model;

/**
 * @author Zahir Mandsaurwala
 * 
 * POJO for the Message
 * 
 * Note: lombok annotations are not used as this requires changes in eclipse ini file 
 *
 */
public class Message {

	private String entireMessage;
	
	private String messageId;
	
	private int sleepTime;
	
	private String payload;
	
	private String IS_PROCESSED;
	
	public Message(String entireMessage, String messageId, int sleepTime, String payload) {
		super();
		this.entireMessage = entireMessage;
		this.messageId = messageId;
		this.sleepTime = sleepTime;
		this.payload = payload;
		this.IS_PROCESSED = "NO";
	}

	public String getEntireMessage() {
		return entireMessage;
	}

	public void setEntireMessage(String entireMessage) {
		this.entireMessage = entireMessage;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public int getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getIS_PROCESSED() {
		return IS_PROCESSED;
	}

	public void setIS_PROCESSED(String iS_PROCESSED) {
		IS_PROCESSED = iS_PROCESSED;
	}

	@Override
	public String toString() {
		return "Message [entireMessage=" + entireMessage + ", messageId=" + messageId + ", sleepTime=" + sleepTime
				+ ", payload=" + payload + ", IS_PROCESSED=" + IS_PROCESSED + "]";
	}
}
