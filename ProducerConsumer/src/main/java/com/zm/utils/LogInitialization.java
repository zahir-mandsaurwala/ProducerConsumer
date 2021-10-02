package com.zm.utils;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

/**
 * @author Zahir Mandsaurwala
 * 
 * Log4J properties initialization
 *
 */
public final class LogInitialization {

	private static final Properties properties=new Properties();
	public static void initializeLog4J() {
		
		properties.setProperty("log4j.rootLogger","DEBUG, FILE, CONSOLE");
	    properties.setProperty("log4j.rootCategory","TRACE");

	    properties.setProperty("log4j.appender.CONSOLE",     "org.apache.log4j.ConsoleAppender");
	    properties.setProperty("log4j.appender.FILE",  "org.apache.log4j.FileAppender");
	    properties.setProperty("log4j.appender.FILE.File","C:\\temp1\\InterviewSolution.log");

	    properties.setProperty("log4j.appender.CONSOLE.layout", "org.apache.log4j.PatternLayout");
	    properties.setProperty("log4j.appender.CONSOLE.layout.conversionPattern", "%d{yyyy/MM/dd HH:mm:ss.SSS} [%5p] %t (%F) - %m%n");
	    properties.setProperty("log4j.appender.FILE.layout",  "org.apache.log4j.PatternLayout");
	    properties.setProperty("log4j.appender.FILE.layout.conversionPattern","%d{yyyy/MM/dd HH:mm:ss.SSS} [%5p] %t (%F) - %m%n");

	    PropertyConfigurator.configure(properties);
	}
}
