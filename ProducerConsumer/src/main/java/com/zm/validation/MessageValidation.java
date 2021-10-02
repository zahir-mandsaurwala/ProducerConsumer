package com.zm.validation;

import com.zm.constant.Constants;
import com.zm.constant.MessageTypeEnum;
import com.zm.model.Message;

/**
 * @author Zahir Mandsaurwala
 * 
 * Message Validation
 *
 */
public final class MessageValidation {
	
	public static MessageTypeEnum validateMessage(Message msg) {
		
		if(null!= msg && null!= msg.getMessageId() && !msg.getMessageId().isBlank()
				&& msg.getSleepTime() != Constants.NEGATIVE_ONE
				&& null!= msg.getPayload() && !msg.getPayload().isBlank()) {
			//Message to be processed by Consumers
			return MessageTypeEnum.COMPLETE_MESSAGE;
		} else if(null!= msg && (null== msg.getMessageId() || msg.getMessageId().isBlank())
				&& msg.getSleepTime() != Constants.NEGATIVE_ONE 
				&& (null== msg.getPayload() || msg.getPayload().isBlank())) {
			//Sleep message for the Producer
			return MessageTypeEnum.SLEEP_MESSAGE;
		} else {
			//Invalid message, do not process
			return MessageTypeEnum.INVALID_MESSAGE;
		}
	}
}
