package com.zm.main;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.zm.constant.Constants;
import com.zm.consumer.Consumer;
import com.zm.model.Message;
import com.zm.model.MessageKey;
import com.zm.producer.Producer;
import com.zm.utils.Display;
import com.zm.utils.LogInitialization;

public class Main {

	public static void main(String[] args) throws Exception {
		
		int numOfConsumers;
		String messageFilePath;
		
		LogInitialization.initializeLog4J();
		
		final Logger LOG = Logger.getLogger(Main.class);
		
		//Check the input
		if (args.length == 2 && Integer.parseInt(args[0]) > 0) {
			numOfConsumers = Integer.parseInt(args[0]);
			messageFilePath = args[1];
		} else {
			LOG.error(Constants.INVALID_INPUT);
			return;
		}
		
		//Display the start message
		Display.startMsgDisplay(numOfConsumers, messageFilePath);
		
		//Initialize the shared variables
		ConcurrentHashMap<MessageKey, ConcurrentLinkedQueue<Message>> sharedMessages = 
				new ConcurrentHashMap<MessageKey, ConcurrentLinkedQueue<Message>>();
		
		//To keep track if all the messages are processed
		AtomicInteger numOfmessages = new AtomicInteger(0);
		//To keep track if the entire is processed
		AtomicBoolean moreData = new AtomicBoolean(true);
		CountDownLatch countDownLatch = new CountDownLatch(numOfConsumers);

		ExecutorService producerService = Executors.newFixedThreadPool(Constants.ONE);
        ExecutorService consumerService = Executors.newFixedThreadPool(numOfConsumers);
        
        //Initialize the Consumers as per the user input
        for(int i=1; i<=numOfConsumers; ++i) {
        	consumerService.submit(new Consumer(sharedMessages, Constants.CONSUMER_THREAD + Constants.HYPHEN + i,
        						numOfmessages, moreData, countDownLatch));
        }
               
        producerService.submit(new Producer(sharedMessages, Constants.PRODUCER_THREAD + Constants.HYPHEN + Constants.ONE, 
				messageFilePath, numOfmessages, moreData));
        
        producerService.shutdown();
        consumerService.shutdown();
        
        countDownLatch.await();
        
        Display.endMsgDisplay();
	}

}
