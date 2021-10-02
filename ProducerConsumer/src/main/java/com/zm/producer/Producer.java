package com.zm.producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.zm.constant.Constants;
import com.zm.constant.MessageTypeEnum;
import com.zm.mapper.MessageMapper;
import com.zm.model.Message;
import com.zm.model.MessageKey;
import com.zm.utils.Display;
import com.zm.validation.MessageValidation;

/**
 * @author Zahir Mandsaurwala
 * 
 * Producer Class for reading the messages
 *
 */
public class Producer implements Runnable{
	
	private ConcurrentHashMap<MessageKey, ConcurrentLinkedQueue<Message>> sharedMessages;
	private String threadName;
	private String filePath;
	private AtomicInteger numOfmessages;
	private AtomicBoolean moreData;
	
	BufferedReader reader;
	
	private static final Logger LOG = Logger.getLogger(Producer.class);
		
	/**
	 * @param sharedMessages
	 * @param threadName
	 * @param filePath
	 * @param numOfmessages
	 * @param moreData
	 */
	public Producer(ConcurrentHashMap<MessageKey, ConcurrentLinkedQueue<Message>> sharedMessages, String threadName,
			String filePath, AtomicInteger numOfmessages, AtomicBoolean moreData) {
		super();
		this.sharedMessages = sharedMessages;
		this.threadName = threadName;
		this.filePath = filePath;
		this.numOfmessages = numOfmessages;
		this.moreData = moreData;
	}

	/**
	 * Process the messages from the file
	 */
	public void run() {
		//Initialize the file reader object
		readFile();

		while(moreData.get()) {
			//Read the next line from the file
			Message msg = readNextLine();

			if (null != msg) {
				
				//Validate the message
				MessageTypeEnum msgTypeEnum = MessageValidation.validateMessage(msg);
				if (msgTypeEnum.equals(MessageTypeEnum.COMPLETE_MESSAGE)) {
					try {
						//Create the Message Key using message ID and processing flag
						MessageKey msgKey = createMessageKey(msg);
						//Check if the message ID was received earlier
						if (sharedMessages.containsKey(msgKey)) {
							//If the message ID was received, add the new message to the the existing Queue
							ConcurrentLinkedQueue<Message> msgKeyQueue = sharedMessages.get(msgKey);
							msgKeyQueue.add(msg);
						} else {
							//Else create a new Queue
							ConcurrentLinkedQueue<Message> msgKeyQueue = new ConcurrentLinkedQueue<Message>();
							msgKeyQueue.add(msg);
							sharedMessages.put(msgKey, msgKeyQueue);
						}
						//Atomically Increment the message count
						numOfmessages.incrementAndGet();
					} catch (Exception e) {
						LOG.error(e.getMessage());
						e.printStackTrace();
					}
				} else if (msgTypeEnum.equals(MessageTypeEnum.SLEEP_MESSAGE)) {
					try {
						//In case of sleep message, stop the producer
						numOfmessages.incrementAndGet();
						Display.producerSleepDisplay(msg, threadName);
						Thread.sleep(msg.getSleepTime());
						numOfmessages.decrementAndGet();
					} catch (InterruptedException e) {
						LOG.error(e.getMessage());
						e.printStackTrace();
					}
				} else {
					//Invalid message is not processed and the message is displayed as the output
					System.out.println(Constants.INVALID_MESSAGE + Constants.HYPHEN + msg.toString());
				}
			}
		}
	}

	/**
	 *  Initialize the file reader object
	 */
	private void readFile() {
		
	    Path path = Paths.get(filePath);
	    
		try {
			reader = Files.newBufferedReader(path);
		} catch (IOException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Read each message one at a time from the file and parse it
	 * 
	 * @return Message
	 */
	private Message readNextLine() {
		
	    MessageMapper msgMapper = new MessageMapper();
	    Message msg = null;
	    
		try {
			String fileLine = reader.readLine();
			if(null!= fileLine) {
				msg = msgMapper.deserialiseInput(fileLine);
			} else {
				moreData.set(false);
			}
		} catch (IOException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * Initialize the Message Key object for each message ID
	 * 
	 * @param msg
	 * @returnMessageKey
	 */
	private MessageKey createMessageKey(Message msg) {
		return new MessageKey(msg.getMessageId());
	}
}
