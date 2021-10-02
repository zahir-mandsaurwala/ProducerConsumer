package com.opentxt.interview.mapper;

import static org.junit.Assert.*;

import org.junit.Test;

import com.zm.mapper.MessageMapper;
import com.zm.model.Message;

public class MessageMapperTest {

	@Test
	public void testDeserialiseInputCompleteMsg() {
		
		String fileLine = "B|1000|b-01";
		MessageMapper msgMapper = new MessageMapper();
		
		Message msg = msgMapper.deserialiseInput(fileLine);
		
		assertEquals(msg.toString(), new Message("B|1000|b-01", "B", 1000, "b-01").toString());
	}
	
	@Test
	public void testDeserialiseInputSleepMsg() {
		
		String fileLine = "|1000|";
		MessageMapper msgMapper = new MessageMapper();
		
		Message msg = msgMapper.deserialiseInput(fileLine);
		
		assertEquals(msg.toString(), new Message("|1000|", "", 1000, "").toString());
	}
	
	@Test
	public void testDeserialiseInputInvalidMsg() {
		
		String fileLine = "abcd";
		MessageMapper msgMapper = new MessageMapper();
		
		Message msg = msgMapper.deserialiseInput(fileLine);
		
		assertEquals(msg.toString(), new Message("abcd", "", -1, "").toString());
	}

}
