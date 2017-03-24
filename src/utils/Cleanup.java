package utils;

import java.util.List;

public class Cleanup {

	public static void Process() {
		Timers.ProcessEnd();
		Debug.Log("Terminating...");
		CloseSummary();
	}
	
	public static void Terminate() {
		Timers.ProcessEnd();
		Debug.Log("An unexpected error has occured! Terminating...");
		CloseSummary();
	}
	
	private static void CloseSummary() {
		Browser.Close();
		Debug.Log("Total elapsed time (secs): " + Timers.ProcessTotal());
		Debug.Log("Writing log file to : " + Files.FileLog);
		Summarize();
		Report.WriteHTMLReport();
		Files.WriteToFile(Global.Logs, Files.FileLog);
		System.exit(0);
		
	}
	
	public static void Summarize() {
		Debug.Log("----------");
		Debug.Log(Global.ProjectName + "Test Report as of " + Scribe.DateTimeGetNow());
		Debug.Log("--- Over-all Summary ---");
		Debug.Log("Project: " + Global.ProjectName);
		Debug.Log("Base URL: " + Global.BaseURL);
		Debug.Log("Browser: " + Global.BrowserName);
		Debug.Log("Started: " + Global.ProcessStart);
		Debug.Log("Ended: " + Global.ProcessEnd);
		Debug.Log("Elapsed: " + Global.ProcessElapsed);
		Debug.Log("--- Performance Summary ---");
		Debug.Log("Time to complete(secs)");
		for (int perfIdx = 0; perfIdx < Collections.ListGetTotalItems(Global.PerformanceResults); perfIdx++) {
			List<String> perfLine = Scribe.StringConvertCSVToList(Collections.ListGetValueFromIndex(Global.PerformanceResults, perfIdx));
			Debug.Log(perfLine.get(0) + "," + perfLine.get(3) );
		}
		Debug.Log("--- Test Suites ---");
		Debug.Log("SuiteName, Started, Ended, Elapsed(secs)");
		for (int suiteIdx = 0; suiteIdx < Collections.ListGetTotalItems(Global.PerformanceResults); suiteIdx++) {
			List<String> suiteLine = Scribe.StringConvertCSVToList(Collections.ListGetValueFromIndex(Global.PerformanceResults, suiteIdx));
			Debug.Log(suiteLine.get(0) + "," + suiteLine.get(1) + "," + suiteLine.get(2) + "," + suiteLine.get(3));
		}
		Debug.Log("--- Test Cases ---");
		Debug.Log("Test Suite, Test Case, Started, Ended, Elapsed(secs), Passed, Failed, Total, Rate(%), Status");
		for (int testIdx = 0; testIdx < Collections.ListGetTotalItems(Global.TestSummary); testIdx++) {
			List<String> testLine = Scribe.StringConvertCSVToList(Collections.ListGetValueFromIndex(Global.TestSummary, testIdx));
			Debug.Log(testLine.get(0) + "," + testLine.get(1) + "," + testLine.get(2) + "," + testLine.get(3) + 
					"," + testLine.get(4) + "," + testLine.get(5) + "," + testLine.get(6) + "," + (Integer.valueOf(testLine.get(5)) + Integer.valueOf(testLine.get(6))) +
					"," + testLine.get(7) + "," + testLine.get(8)
					);
		}		
	}
}
