package utils;

import java.util.LinkedHashMap;

public class Maps {
	public static LinkedHashMap<String, String> Actions = new LinkedHashMap<String, String>();
	public static LinkedHashMap<String, String> ActionDescriptions = new LinkedHashMap<String, String>();
	public static LinkedHashMap<String, String> Statuses = new LinkedHashMap<String, String>();
	public static LinkedHashMap<String, String> TestValues = new LinkedHashMap<String, String>();
	
	
	//Set default statuses
	public static void SetStatuses() {
		Statuses.put("0", "Pass");
		Statuses.put("1", "Warning");
		Statuses.put("2", "Fail");
		Statuses.put("3", "Skipped");
		Statuses.put("4", "Fail - Object not found");
		Statuses.put("5", "Fail - Item not found in collection");
	}

	//Set action helper descriptions
	public static void SetActionDescriptions() {
		ActionDescriptions.put("LOAD OBJECT REPOSITORY", "Load Object Repository:");
		ActionDescriptions.put("CLICK", "Click object");
		ActionDescriptions.put("TYPE", "Type text"); 
		ActionDescriptions.put("VALIDATE", "Validate if");
		ActionDescriptions.put("SELECT", "Select item");
		ActionDescriptions.put("TOGGLE", "Toggle option");
		ActionDescriptions.put("HOVER", "Hover over");
		ActionDescriptions.put("STORE", "Store the text value of");
		ActionDescriptions.put("GET", "Return the value of");
	}

	public static String GetMapValue(LinkedHashMap<String, String> map, String key) {
		try {
			return map.get(key);
		} catch (NullPointerException npe) {
			Debug.ExceptionError(npe);
			Debug.Log("No value found for key: " + key);
			return null;
		}
	}
	
	public static void StoreTestValue(String key, String value) {
		try {
			TestValues.put(key, value);
			Debug.Log("Stored " + key + " with value: " + value);			
		} catch (NullPointerException npe) {
			Debug.ExceptionError(npe);
		}

	}
	
	public static String GetTestValue(String key) {
		try {
			String testVal = GetMapValue(TestValues, key);
			return testVal;
		} catch (NullPointerException npe) {
			Debug.Log("No key specified.");
			return null;
		}
	}
	
	
	

	
}
