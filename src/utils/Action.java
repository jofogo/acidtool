package utils;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


public class Action {
	public static List<String[]> TestActionList;
	public static List<String[]> TestObjectList;
	private static int ctrSkip = 1, ctrTestObjectTotal = 0;
	public static int ctrPass = 0, ctrFail = 0;
	public static int GetActionListCount() {
		try {
			Debug.Log("Retrieving the test actions from the test action file: " + Files.FileTestActions);
			TestActionList = Files.CSVToList(Files.FileTestActions);
			int ctrTestStepList = TestActionList.size() - ctrSkip;
			if (! TestActionList.isEmpty() && ctrTestStepList > 0) {
				Global.ctrTestActions = ctrTestStepList;
				Debug.Log("Action Step(s) found: " + Global.ctrTestActions);
				return ctrTestStepList;
			}
		} catch (NullPointerException npe) {
			Debug.ExceptionError(npe);
		}
		return 0;
	}

	
	private static void Set(int ctrTestSteps) {
		int ctrTestStepIndex = ctrSkip + ctrTestSteps;
		try {
			if (ctrTestSteps > 0 && ctrTestStepIndex <= TestActionList.size()) {
				String[] ActionLine = TestActionList.get(ctrTestStepIndex - 1);
				Global.ObjectAction = ActionLine[0];
				Global.ObjectName = ActionLine[1];
				Global.ObjectParameter = ActionLine[2];

			}
		} catch (IndexOutOfBoundsException ioobe) {
			Debug.ExceptionError(ioobe);
		}
	}
	
	private static String FindObjectID(String objName) {
		if (! TestObjectList.isEmpty()) {
			return Collections.ListGetValueFromKey(TestObjectList, objName);
		}
		return "";
	}
	
	private static int LoadTestObjects() {
		int stat = 0;
		Files.SetFileTestObjects();
		if (Files.CheckIfExists(Files.FileTestObjects)) {
			TestObjectList = Files.CSVToList(Files.FileTestObjects);
			ctrTestObjectTotal = TestObjectList.size() - ctrSkip;
			Debug.Log("Loaded Test Object Repository file: " + Files.FileTestObjects);
		} else {
			Debug.Log("Could not load the Object Repository file!");
			stat = 2;
		}
		return stat;
	}
	
	public static void Load() {
		int ActionStat = 0;
		try {
			if (Global.TestActionName!=null) {
				Initializer.Action();
				Files.SetFileTestActions();
				if (Files.CheckIfExists(Files.FileTestActions)) {
					int ctrActionTotal = 0;
					if(NumberUtils.isDigits(Global.TestActionParms)) {
						ctrActionTotal = NumberUtils.toInt(Global.TestActionParms);
						if (ctrActionTotal > 0 && ctrActionTotal < GetActionListCount()) {
							Debug.Log("Loading only until step " + Global.TestActionParms + ".");
						} else {
							Debug.Log("Loading all steps since " + Global.TestActionParms + " is out of range.");
							ctrActionTotal = GetActionListCount();
						}
							
					} else {
						ctrActionTotal = GetActionListCount();
					}
				
					for (int ctrAction = 1; ctrAction <= ctrActionTotal; ctrAction++) {
						Timers.ActionStart();
						Set(ctrAction);
						Debug.Trace("Action: " + Global.ObjectAction + " Object: " + Global.ObjectName + " Parameter: " + Global.ObjectParameter);
						switch (Global.ObjectAction.toUpperCase()) {
						case "LOAD OBJECT REPOSITORY":
							ActionStat = LoadTestObjects();
							break;
						case "STORE":
							if (FindObjectID(Global.ObjectName)=="" && Global.ObjectParameter!="") {
								Maps.StoreTestValue(Global.ObjectParameter, Global.ObjectName);
								break;
							} 
						default:
							Global.ObjectID = FindObjectID(Global.ObjectName);
							
							if (Global.ObjectID != "") {
								Web.PerformOnObject(Global.ObjectAction, Global.ObjectID, Global.ObjectParameter );
								Debug.Trace("Action: " + Global.ObjectAction + " Object: " + Global.ObjectName + " ID: " + Global.ObjectID +  " Parameter: " + Global.ObjectParameter);
							} else {
								ActionStat = 4;
								Global.ctrActionStatus = 4;
							}
							break;
						}
						Timers.ActionEnd();
						Summarize();

					}
					ActionStat = 0;
				}
			} else {
				ActionStat = 2;
			}
			
		}catch (NullPointerException npe) {
			Debug.ExceptionError(npe);
		}
		
		Global.ctrActionStatus = ActionStat;
		
	}

	
	public static void Summarize() {
		Global.ActionElapsed = Timers.ActionTotal();
		//Files.SetFileData();
		String line = Global.TestSuiteName + "," + Global.TestCaseName + "," + 
				//Global.ActionStart + "," + Global.ActionEnd + "," 
				+ Global.ActionElapsed + "," + 
				Maps.GetMapValue(Maps.ActionDescriptions, Global.ObjectAction.toUpperCase()) + " " + Global.ObjectName;
		if (Global.ObjectParameter != null) {
			line += " " + Global.ObjectParameter;
		}
		line += "," + Maps.Statuses.get(Integer.toString(Global.ctrActionStatus));
		

/*		String line = Global.TestSuiteName + "," + Global.SuiteStart + ","
				+ Global.SuiteEnd + "," + Global.SuiteElapsed + "," + Global.TestSuiteStatus;*/
		Debug.Trace(line);
		Collections.writeTestSteps(line);
	}	
	

}
