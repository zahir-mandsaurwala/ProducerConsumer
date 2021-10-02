package com.zm.model;

/**
 * @author Zahir Mandsaurwala
 * 
 * POJO to hold the Consumer display message
 *
 */
public class OutputDisplay {

	private String message;
	
	private String threadName;
	
	private String startTime;
	
	private String endTime;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "OutputDisplay [message=" + message + ", threadName=" + threadName + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}
}
