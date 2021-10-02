package com.zm.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Zahir Mandsaurwala
 * 
 * Date Time Utils
 *
 */
public class DateTimeUtils {
	
	/**
	 * @param format
	 * @return String
	 * 
	 * Get the current system time in a particular input format
	 * 
	 */
	public static String getCurrentTime(String format) {
		LocalTime now = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		String formattedDate = now.format(formatter);
		return formattedDate;
	}
}
