package com.zm.model;

/**
 * @author Zahir Mandsaurwala
 * 
 * POJO for the Message Key
 *
 */
public class MessageKey {

	private String messageId;
	
	private String isProcessing;
	
	public MessageKey(String messageId) {
		super();
		this.messageId = messageId;
		this.isProcessing = "NO";
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getIsProcessing() {
		return isProcessing;
	}

	public void setIsProcessing(String isProcessing) {
		this.isProcessing = isProcessing;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageKey other = (MessageKey) obj;
		if (messageId == null) {
			if (other.messageId != null)
				return false;
		} else if (!messageId.equals(other.messageId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MessageKey [messageId=" + messageId + ", isProcessing=" + isProcessing + "]";
	}
}
