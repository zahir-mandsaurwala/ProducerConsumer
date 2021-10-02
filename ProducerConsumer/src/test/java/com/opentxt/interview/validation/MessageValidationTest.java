package com.opentxt.interview.validation;

import static org.junit.Assert.*;

import org.junit.Test;

import com.zm.constant.MessageTypeEnum;
import com.zm.model.Message;
import com.zm.validation.MessageValidation;

public class MessageValidationTest {

	@Test
	public void testCompleteValidateMessage() {
		
		Message msg = new Message("A|1000|a-01", "A", 100, "a-01");
		MessageTypeEnum msgValEnum = MessageValidation.validateMessage(msg);
		assertEquals(msgValEnum.COMPLETE_MESSAGE.name(), MessageTypeEnum.COMPLETE_MESSAGE.name());
	}
	
	@Test
	public void testSleepValidateMessage() {
		
		Message msg = new Message("|1000|", "", 100, "");
		MessageTypeEnum msgValEnum = MessageValidation.validateMessage(msg);
		assertEquals(msgValEnum.SLEEP_MESSAGE.name(), MessageTypeEnum.SLEEP_MESSAGE.name());
	}
}
