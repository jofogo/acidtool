package utils;

import java.text.DecimalFormat;
import java.util.List;

public class TestCase {
	
	public static List<String[]> TestCaseList;
	public static List<String[]> TestStepList;
	private static int ctrSkip = 1, ctrStepTotal = 0;
	public static int ctrPass = 0, ctrFail = 0;
	public static int GetTestCaseListCount() {
		try {
			Debug.Log("Retrieving the list of test cases from the test suite file.");
			TestCaseList = Files.CSVToList(Files.FileTestSuite);
			int ctrTestCaseList = TestCaseList.size() - ctrSkip;
			if (! TestCaseList.isEmpty() && ctrTestCaseList > 0) {
				Global.ctrTestCases = ctrTestCaseList;
				Debug.Log("Test Case(s) found: " + Global.ctrTestCases);
				return ctrTestCaseList;
			}
		} catch (NullPointerException npe) {
			Debug.ExceptionError(npe);
		}
		return 0;
	}

	public static int GetTestStepListCount() {
		try {
			Debug.Log("Retrieving the list of test steps from the test case: " + Global.TestCaseName);
			TestStepList = Files.CSVToList(Files.FileTestCase);
			//TestCaseList = Files.CSVToList(Files.FileTestSuite);
			int ctrTestStepList = TestStepList.size() - ctrSkip;
			if (! TestStepList.isEmpty() && ctrTestStepList > 0) {
				ctrStepTotal = ctrTestStepList;
				Debug.Log("Test Step(s) found: " + ctrStepTotal);
				return ctrTestStepList;
			}
		} catch (NullPointerException npe) {
			Debug.ExceptionError(npe);
		}
		return 0;
	}
	
	private static void Set(int ctrTestCase) {
		int ctrTestIndex = ctrSkip + ctrTestCase;
		try {
			if (ctrTestCase > 0 && ctrTestIndex <= TestCaseList.size()) {
				String[] TestLine = TestCaseList.get(ctrTestIndex - 1);
				Global.TestCaseName = TestLine[0];
				Global.TestCaseIn = TestLine[1];
				Global.TestCaseOut = TestLine[2];
				Debug.Log("Loading Test Case: " + Global.TestCaseName);
			}
		} catch (IndexOutOfBoundsException ioobe) {
			Debug.ExceptionError(ioobe);
		}
	}
	
	public static void Load(int TestIndex) {
		String TestCaseStat = "";
		Set(TestIndex);
		Initializer.TestCase();
		try {
			Files.SetFileTestCase();
			if (Files.CheckIfExists(Files.FileTestCase)) {
				Timers.TestCaseStart();
				GetTestStepListCount();
				for (int ctrStep = 1; ctrStep <= ctrStepTotal; ctrStep++) {
					String [] StepLine = TestStepList.get(ctrStep);
					Global.TestActionName = StepLine[0];
					Global.TestActionParms = StepLine[1];
					Debug.Log("Loading Test Action: " + Global.TestActionName + " with parameter(s): " + Global.TestActionParms);
					Action.Load();
				}
				TestCaseStat = "Pass";
				Timers.TestCaseEnd();
			} else {
				TestCaseStat = "Error";
				Debug.Log("Test Case not found!");					
			}
			Global.TestCaseStatus = TestCaseStat;
		} catch (NullPointerException npe) {
			Debug.ExceptionError(npe);
		}
		Summarize();
	}

	
	public static void Summarize() {
		Global.TestElapsed = Timers.TestCaseTotal();
		String line = Global.TestSuiteName + "," + Global.TestCaseName + "," + Global.TestStart + ","
				+ Global.TestEnd + "," + Global.TestElapsed + "," + ctrPass + "," + ctrFail + ",";
		
/*		Debug.Trace("steps passed: " + ctrPass);
		Debug.Trace("steps failed: " + ctrFail);*/
		float rate = ctrPass + ctrFail;
		if (rate==0) {
			rate = 1;
		}
		rate = ctrPass  / rate;
		rate = rate * 100.0f;
		
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		line += df.format(rate) + ",";
		if (!Global.TestCaseStatus.equalsIgnoreCase("Error")) {
			if (rate < 100.00) {
				Global.TestCaseStatus = "Fail";
			}
			if (ctrPass == 0 && ctrFail == 0) {
				Global.TestCaseStatus = "Skipped";
			}

		}
		line += Global.TestCaseStatus;
/*		String line = Global.TestSuiteName + "," + Global.SuiteStart + ","
				+ Global.SuiteEnd + "," + Global.SuiteElapsed + "," + Global.TestSuiteStatus;*/
		Debug.Trace(line);
		Collections.writeTestSummary(line);
	}
	
	
}
