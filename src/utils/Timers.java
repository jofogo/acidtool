package utils;

public class Timers {
	public static long TimeProcessStart, TimeProcessEnd, TimeProcessTotal;
	public static long TimeSuiteStart, TimeSuiteEnd, TimeSuiteTotal;
	public static long TimeTestCaseStart, TimeTestCaseEnd, TimeTestCaseTotal;
	public static long TimeActionStart, TimeActionEnd, TimeActionTotal;
	
	/* Entire process */
	public static void ProcessStart() {
		Global.ProcessStart = Scribe.DateTimeGetNow();
		TimeProcessStart = System.currentTimeMillis();
	}
	public static void ProcessEnd() {
		Global.ProcessEnd = Scribe.DateTimeGetNow();
		TimeProcessEnd = System.currentTimeMillis();
	}
	public static long ProcessTotal() {
		TimeProcessTotal = (TimeProcessEnd - TimeProcessStart)/1000;
		Global.ProcessElapsed = TimeProcessTotal;
		return TimeProcessTotal;
	}

	/* Suite */
	public static void SuiteStart() {
		Global.SuiteStart = Scribe.TimeGetNow();
		TimeSuiteStart = System.currentTimeMillis();
	}
	public static void SuiteEnd() {
		Global.SuiteEnd = Scribe.TimeGetNow();
		TimeSuiteEnd = System.currentTimeMillis();
	}
	public static long SuiteTotal() {
		TimeSuiteTotal = (TimeSuiteEnd - TimeSuiteStart)/1000;
		return TimeSuiteTotal;
	}

	/* Test cases */
	public static void TestCaseStart() {
		Global.TestStart = Scribe.TimeGetNow();
		TimeTestCaseStart = System.currentTimeMillis();
	}
	public static void TestCaseEnd() {
		Global.TestEnd = Scribe.TimeGetNow();
		TimeTestCaseEnd = System.currentTimeMillis();
	}
	public static long TestCaseTotal() {
		TimeTestCaseTotal = (TimeTestCaseEnd - TimeTestCaseStart)/1000;
		return TimeTestCaseTotal;
	}
	
	/* Actions */
	public static void ActionStart() {
		Global.ActionStart = Scribe.DateTimeGetNow();
		TimeActionStart = System.currentTimeMillis();
	}
	public static void ActionEnd() {
		Global.ActionEnd = Scribe.DateTimeGetNow();
		TimeActionEnd = System.currentTimeMillis();
	}
	public static long ActionTotal() {
		TimeActionTotal = (TimeActionEnd - TimeActionStart)/1000;
		return TimeActionTotal;
	}	
	
}
