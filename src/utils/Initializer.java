package utils;

import java.util.List;

public class Initializer {
	public static void Process() {
		Global.ctrTotalPass = 0;
		Global.ctrTotalFail = 0;
		Global.ctrTotalRate = 0.0;
		
		Global.ProcessStart = "";
		Global.ProcessEnd = "";
		Global.ProcessElapsed = 0;
		
		Global.TestSummary = new String[0];
		Global.PerformanceResults = new String[0];
		Global.TestSteps = new String[0];
		Global.ReportAll = new String[0];
		Timers.TimeProcessStart = 0;
		Timers.TimeProcessEnd = 0;
		Timers.ProcessStart();
		Maps.SetStatuses();
		Maps.SetActionDescriptions();
		
		Debug.Log("Starting the Test Automation Tool...");
		
	}
	
	public static void Paths() {
		if (Global.configName.isEmpty()) {
			Files.SetFileConfig();			
		} else {
			Files.SetFileConfig(Global.configName);			
		}

		Files.SetFileLog();
	}
	
	public static void Config() {
		if (! Files.CheckIfExists(Files.FileConfig)) {
			Files.WriteToFile(Global.Config, Files.FileConfig);
		}
		List<String[]> cfg = Files.CSVToList(Files.FileConfig);
		Global.ProjectName = Collections.ListGetValueFromKey(cfg, "Project Name");
		Global.BrowserName = Collections.ListGetValueFromKey(cfg, "Browser");
		Global.BaseURL = Collections.ListGetValueFromKey(cfg, "Base URL");
	}

	public static void PageInTest() {
		Timers.SuiteStart();
		Global.TestSuiteName = "Load Base URL";
		Global.TestSuiteStatus = "Run";
		Files.DirScreenshots += Global.ProjectName + "\\" + Scribe.DateTimeGetStamp() + "\\";
		Browser.LoadBrowser(Global.BrowserName);
		Web.LoadURL(Global.BaseURL);
		Timers.SuiteEnd();
		Suites.Summarize();
	}
	
	public static void Suite() {
		Global.SuiteStart = "n/a";
		Global.SuiteEnd = "n/a";
		Global.SuiteElapsed = 0;
		Timers.TimeSuiteStart = 0;
		Timers.TimeSuiteEnd = 0;
		Global.TestSuiteStatus = "n/a";
		Global.TestCaseName = null;
	}
	
	public static void TestCase() {
		Global.TestStart = "";
		Global.TestEnd = "";
		Global.TestElapsed = 0;
		Timers.TimeTestCaseStart = 0;
		Timers.TimeTestCaseEnd = 0;
		TestCase.ctrPass = 0;
		TestCase.ctrFail = 0;
		Global.TestActionName = null;
		Global.TestActionParms = "";
		Global.ReportTest = new String[0];
		
	}
	
	public static void Action() {
		Global.ActionStart = "";
		Global.ActionEnd = "";
		Global.ActionElapsed = 0;
		Global.ctrActionStatus = 0;
		
		Timers.TimeActionStart = 0;
		Timers.TimeActionEnd = 0;
		Global.ObjectAction = "";
		Global.ObjectID = "";
		Global.ObjectName = "";
		Global.ObjectParameter = "";
	}
}
