package utils;

public class Global {
	/*Test Objects*/
	public static String ProjectName, ProjectPath; 
	public static String TestSuiteName, TestSuitePath, Execute, TestSuiteList, TestSuiteStatus; 
	public static String TestCaseName, TestCaseIn, TestCaseOut, TestCasePath, TestCaseList, TestCaseStatus;
	public static String TestObjectName, TestObjectPath;
	public static String TestActionName, TestActionParms, TestActionPath;
	public static String ObjectName, ObjectID, ObjectAction, ObjectParameter;
	public static int ctrMaxStepRetry = 3;
	public static int secsMaxTimeOut = 30;
	public static int ctrSShots = 1;

	/*Web Objects*/
	public static String BrowserName, BaseURL;
	
	/*Test Counters*/
	public static int ctrTestSuites = 0;
	public static int ctrTestCases = 0;
	public static int ctrTestActions = 0;
	public static int ctrActionStatus = 0;

	public static int ctrTestPass = 0;
	public static int ctrTestFail = 0;
	public static int ctrTestTotal = 0;
	public static int ctrActionPass = 0;
	public static int ctrActionFail = 0;
	public static int ctrActionTotal = 0;
	
	public static int ctrTotalPass = 0;
	public static int ctrTotalFail = 0;
	public static double ctrTotalRate = 0.0;

	/*Timers*/
	public static String ProcessStart, ProcessEnd;
	public static long ProcessElapsed;
	public static String SuiteStart, SuiteEnd;
	public static long SuiteElapsed;
	public static String TestStart, TestEnd;
	public static long TestElapsed;
	public static String ActionStart, ActionEnd;
	public static long ActionElapsed;
	
	/*Default formats*/
	public static String dateFormat = "MM/dd/yyyy";
	public static String timeFormat = "HH:mm:ss";
	public static String stampFormat = "yyyy-dd-MM_HH.mm.ss";
	public static String configName = "";
	public static String fileConfigFormat = ".csv";
	public static String fileDataFormat = ".csv";
	public static String fileLogFormat = ".txt";
	public static String fileScreenshotFormat = ".png";
	public static String fileReportFormat = ".html";
	
	/*Data Lists*/ 
	public static String[] TestActions = new String[0];  
	public static String[] TestSummary = new String[0]; 
	public static String[] TestSteps = new String[0];  
	public static String[] TestSuite = new String[0];
	
	public static String[] PerformanceResults = new String[0]; 

	public static String[] Logs = new String[0]; //Debug file
	public static String[] Config = new String[0]; //Config file
	public static String[] RTConfig = new String[0]; //Retest Config file
	public static String[] ReportAll = new String[0]; //HTML Overall report file
	public static String[] ReportTest = new String[0]; //HTML Test case report file
	public static String[] CSS = new String[0]; //CSS file
	

}
