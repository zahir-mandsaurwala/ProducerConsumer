package com.zm.mapper;

import java.util.StringTokenizer;

import com.zm.constant.Constants;
import com.zm.model.Message;

/**
 * @author Zahir Mandsaurwala
 * 
 * Class for Message deserialization
 *
 */
public class MessageMapper {

	public Message deserialiseInput(String fileLine) {
		
		StringTokenizer strFileLine = new StringTokenizer(fileLine, Constants.PIPE);
		
		String messageId = Constants.BLANK;
		int sleepTime = Constants.NEGATIVE_ONE;;
		String payload = Constants.BLANK;
		
		String sleepTimeStr;
		
		if(strFileLine.countTokens() == 3) {
			//Message to be processed for the Consumers
			messageId = strFileLine.nextToken();
			sleepTimeStr = strFileLine.nextToken();
			if(sleepTimeStr.matches("\\d+")) {
				sleepTime = Integer.parseInt(sleepTimeStr);
			}
			payload = strFileLine.nextToken();
			
		} else if(strFileLine.countTokens() == 2) {
			//Message to be processed by Producer
			sleepTimeStr = strFileLine.nextToken();
			if(sleepTimeStr.matches("\\d+")) {
				sleepTime = Integer.parseInt(sleepTimeStr);
			}
			payload = strFileLine.nextToken();
		} else if(strFileLine.countTokens() == 1) {
			//Message to be processed by Producer
			sleepTimeStr = strFileLine.nextToken();
			if(sleepTimeStr.matches("\\d+")) {
				sleepTime = Integer.parseInt(sleepTimeStr);
			}
		} else {
			//Invalid message, not to be processed
			System.out.println(Constants.INVALID_MESSAGE + Constants.HYPHEN + fileLine);
			return null;
		}
		
		return new Message(fileLine, messageId, sleepTime, payload);
	}
}
