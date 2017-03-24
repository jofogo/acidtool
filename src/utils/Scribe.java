package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Scribe {
	
	public static List<String> StringConvertCSVToList(String csvLine) {
		return Arrays.asList(csvLine.split("\\s*,\\s*"));
	}
	
	public static String StringRemoveLastChar(String text) {
		if (text.length() > 0) {
			return text.substring(0, text.length() - 1);
		} else {
			return text;
		}
	}
	
	public static String DateGetToday() {
		DateFormat df = new SimpleDateFormat(Global.dateFormat);
		Date date = new Date();
		return df.format(date);
	}

	public static String DateGetToday(int days) {
		DateFormat df = new SimpleDateFormat(Global.dateFormat);
		Date date = new Date(2);
		return df.format(date);
	}	
	
	public static String TimeGetNow() {
		DateFormat df = new SimpleDateFormat(Global.timeFormat);
		Date time = new Date();
		return df.format(time);
	}
	
	public static String DateTimeGetStamp() {
		DateFormat df = new SimpleDateFormat(Global.stampFormat);
		Date datetime = new Date();
		return df.format(datetime);
	}

	public static String DateTimeGetNow() {
		return DateGetToday() + " " + TimeGetNow();
	}
	

}
