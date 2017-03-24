  package utils;

import java.util.List;

public class Suites {
	public static List<String[]> TestSuiteList;
	
	private static int ctrSkip = 4;
	public static int GetTestListCount() {
		try {
			Debug.Log("Retrieving the list of test suites from the config file.");
			TestSuiteList = Files.CSVToList(Files.FileConfig);
			int ctrTestList = TestSuiteList.size() - ctrSkip;
			if (! TestSuiteList.isEmpty() && ctrTestList > 0) {
				Global.ctrTestSuites = ctrTestList;
				Debug.Log("Test Suite(s) found: " + Global.ctrTestSuites);
				return ctrTestList;
			}
		} catch (NullPointerException npe) {
			Debug.ExceptionError(npe);
		}
		return 0;
	}
	
	private static void Set(int ctrTestCase) {
		int ctrTestIndex = ctrSkip + ctrTestCase;
		try {
			if (ctrTestCase > 0 && ctrTestIndex <= TestSuiteList.size()) {
				String[] TestLine = TestSuiteList.get(ctrTestIndex - 1);
				Global.TestSuiteName = TestLine[0];
				Global.Execute = TestLine[1];
				Debug.Log("Loading Test Suite: " + Global.TestSuiteName + ". Execute? " + Global.Execute);
			}
		} catch (IndexOutOfBoundsException ioobe) {
			Debug.ExceptionError(ioobe);
		}
	}
	
	public static void Load(int TestIndex) {
		String SuiteStat = "";
		Set(TestIndex);
		Initializer.Suite();
		try {
			if (Global.Execute.equalsIgnoreCase("YES") || Global.Execute.equalsIgnoreCase("Y")) {
				Files.SetFileTestSuite();
				if (Files.CheckIfExists(Files.FileTestSuite)) {
					Timers.SuiteStart();
					if (TestCase.GetTestCaseListCount()>0) {
						for (int ctrTests = 1; ctrTests <= Global.ctrTestCases; ctrTests++) {
							TestCase.Load(ctrTests);

						}
					}
					
					
					SuiteStat = "Run";
					Timers.SuiteEnd();
				} else {
					SuiteStat = "Error";
					Debug.Log("Test Suite Data file not found!");					
				}
				
			} else {
				Debug.Log("Test Suite : " + Global.TestSuiteName + " skipped. Execute is not set to Yes/Y");
				SuiteStat = "Skipped";
			}
			
			Global.TestSuiteStatus = SuiteStat;
		} catch (NullPointerException npe) {
			Debug.ExceptionError(npe);
		}
	}
	
	public static void Summarize() {
		Global.SuiteElapsed = Timers.SuiteTotal();
		String line = Global.TestSuiteName + "," + Global.SuiteStart + ","
				+ Global.SuiteEnd + "," + Global.SuiteElapsed + "," + Global.TestSuiteStatus;
		Debug.Trace(line);
		Collections.writePerformanceResults(line);
	}
	

	
}
