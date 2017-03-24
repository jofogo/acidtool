package utils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class Collections {
	
	
/*	public static void ListExpand(Object[] list) {
		if (ListIsNotNull(list)) {
			list = Arrays.copyOf(list, list.length + 1);
		};
	}*/
	public static void writeCSS (String line) {
		int len = Global.CSS.length;
		Global.CSS = Arrays.copyOf(Global.CSS,  len + 1);
		Global.CSS[len] = line;
	}

	public static void writeTestActions (String line) {
		int len = Global.TestActions.length;
		Global.TestActions = Arrays.copyOf(Global.TestActions,  len + 1);
		Global.TestActions[len] = line;
	}	
	
	public static void writeTestSummary (String line) {
		int len = Global.TestSummary.length;
		Global.TestSummary = Arrays.copyOf(Global.TestSummary,  len + 1);
		Global.TestSummary[len] = line;
	}

	public static void writeTestSteps (String line) {
		int len = Global.TestSteps.length;
		Global.TestSteps = Arrays.copyOf(Global.TestSteps,  len + 1);
		Global.TestSteps[len] = line;
	}

	
	public static void writePerformanceResults (String line) {
		int len = Global.PerformanceResults.length;
		Global.PerformanceResults = Arrays.copyOf(Global.PerformanceResults,  len + 1);
		Global.PerformanceResults[len] = line;
	}

	public static void writeConfig (String line) {
		int len = Global.Config.length;
		Global.Config = Arrays.copyOf(Global.Config,  len + 1);
		Global.Config[len] = line;
	}
	
	public static void writeLog (String line) {
		int len = Global.Logs.length;
		Global.Logs = Arrays.copyOf(Global.Logs,  len + 1);
		Global.Logs[len] = line;
	}
	
	public static void writeReportAll (String line) {
		int len = Global.ReportAll.length;
		Global.ReportAll = Arrays.copyOf(Global.ReportAll,  len + 1);
		Global.ReportAll[len] = line;
	}
	
	public static void writeReportTest (String line) {
		int len = Global.ReportTest.length;
		Global.ReportTest = Arrays.copyOf(Global.ReportTest,  len + 1);
		Global.ReportTest[len] = line;
	}
	
	
	//Check if list is null
	public static boolean ListIsNotNull(Object[] array) {
		try {
			array.getClass();
			return true;
		} catch (NullPointerException npe){
			return false;
		}
	}

	//Return the total number of items in a list
	public static int ListGetTotalItems(Object[] array) {
		if (ListIsNotNull(array)) {
			return array.length;
		}
		return 0;
	}
	
	//Check if the list contains the specified value
	public static boolean ListContainsValue(Object[] array, String value) {
		if (ListIsNotNull(array)) {
			int valueFound = 0;
			for (Object item : array) {
				if(item == value) {
					valueFound++;
					break;
				}
			}
			if (valueFound > 0) {
				Debug.Log(value + " was found in the list.");
				return true;
			}
		}
		Debug.Log(value + " missing from the list.");
		return false;
	}
	
	//Get the value from a list by index number
	public static String ListGetValueFromIndex(Object[] array, int index) {
		try {
			int totalListItems = ListGetTotalItems(array);
			if (totalListItems > 0) {
				if (index < totalListItems) {
					String itemFound = array[index].toString();
					Debug.Trace(itemFound + " was retrieved from index " + index + " of the list.");
					return array[index].toString();
				} 
			}
		} catch (NullPointerException npe) {
			Debug.ExceptionError(npe);
		}
		Debug.Trace("List does not have an index: " + index);
		return "";
	}
	
	public static String ListGetValueFromKey(List<String[]> list, String key) {
		return ListGetValueFromKey(list, key, 1);
	}
	
	//Get the value from a list by key (first item on the list)
	public static String ListGetValueFromKey(List<String[]> list, String key, int columnNumber) {
		
		try {
			int totalListItems = list.size();
			if (totalListItems > 0) {
				for(String[] listItems : list) {
					if (listItems[0].equalsIgnoreCase(key)) {
						String value = listItems[columnNumber];
						Debug.Trace(value + " found for key: " + key);
						return value;
					}
				}
			}
			
		} catch (NullPointerException npe) {
			Debug.ExceptionError(npe);
		}
		Debug.Trace("List does not have a matching key: " + key);
		return "";
	}
	
	//Get the key from a map by value
	public static String MapGetKeyFromValue(LinkedHashMap<String, String> map, String value) {
		int keysFound = 0;
		try {
			if (map.containsValue(value)) {
				for (String key : map.keySet()) {
					if (!value.isEmpty() && map.get(key) == value ) {
						Debug.Log("Key: " + key + " found for value: " + value);
						keysFound++;
						return key;
					}
				}
				if (keysFound  < 1) {
					if (value.isEmpty()) {
						Debug.Log("No key found for blank value");
					} else {
						Debug.Log("No key found for value: " + value);
					}
					return "";
				}
			} else {
				Debug.Log("Value: " + value + " is not in the map!");
			}
		} catch (NullPointerException npe) {
			Debug.ExceptionError(npe);
		}
		Debug.Log("No key found for value: " + value);
		return "";
	}
	
}
