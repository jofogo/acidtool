package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

public class Files {
	private static File file;
	public static String DirData = "\\Data\\";
	public static String DirTestCases = "\\Test Cases\\";
	public static String DirTestObjects = "\\Test Objects\\";
	public static String DirTestActions = "\\Test Actions\\";
	public static String DirTestSuites = "\\Test Suites\\";
	public static String DirReports = "\\HTML Reports\\";
	public static String DirLogs = "\\Logs\\";
	public static String DirScreenshots = "\\Screenshots\\";
	public static String DirResults = "\\TestResults\\";
	public static String FileConfig, FileData, FileLog, FileScreenshot, FileTestResults, FileReport, FileCSS;
	public static String FileTestActions, FileTestObjects, FileTestCase, FileTestSuite;
	
	public static boolean CheckIfExists(String path) {
		try {
			file = new File(path);
			if (file.exists() && file.canRead()) {
				return true;
			}
		} catch (NullPointerException npe) {
			Debug.Trace(path + " not found.");
		}
		return false;
	}
	
	public static String CheckType(String path) {
		if(CheckIfExists(path)) {
			file = new File(path);
			if (file.isFile()) {
				return "File";
			} else if (file.isDirectory()) {
				return "Directory";
			}
		}
		Debug.Log(path + " could not be accessed.");
		return "";
	}
	
	public static void CreateDirectory(String path) {
		if (! CheckIfExists(path) && CheckType(path) != "File") {
			try {
				file.mkdir();
				Debug.Log(path + " directory created.");
			} catch (SecurityException se) {
				Debug.ExceptionError(se);
			}
		}
		Debug.Log(path + " directory exists.");
	}
	
	
	public static void WriteToFile(String[] contents, String pathFile) {
		try {
			PrintWriter pw = new PrintWriter(pathFile, "UTF-8");
			for (String line : contents) {
				pw.println(line);
			}
			pw.close();
		} catch (IOException ioe) {
			Debug.ExceptionError(ioe);
		}
	}

	public static String[] ReadFromFile(String pathFile) throws IOException {
		
		BufferedReader bufferedReader = null;
		String[] fileContents = new String[0];
		try {
			String line;
			bufferedReader = new BufferedReader(new FileReader(pathFile));
			while ((line = bufferedReader.readLine()) != null) {
				fileContents = Arrays.copyOf(fileContents, fileContents.length+1);
				fileContents[fileContents.length-1] = line;
			}
		} catch (Exception e) {
			Debug.ExceptionError(e);
			return null;
		} finally {
			bufferedReader.close();
		}
		return fileContents;
	}
	
	public static List<String[]> CSVToList(String csvfile) {
		CsvParserSettings parserSettings = new CsvParserSettings();
		parserSettings.getFormat().setLineSeparator("\n");
		CsvParser parser = new CsvParser(parserSettings);
		try {
			if (CheckIfExists(csvfile)) {
				return parser.parseAll(new FileReader(csvfile));
			} else {
				Debug.Log("Could not find the CSV file: " + csvfile);
			}
		} catch (Exception e){
			Debug.ExceptionError(e);
		}
		return null;
	}	
	
	/*Set file paths*/

	public static void SetFileConfig() {
		Files.FileConfig = Global.ProjectPath + "\\" + Global.ProjectName + Global.fileConfigFormat;
	}

	public static void SetFileConfig(String configName) {
		Files.FileConfig = Global.ProjectPath + "\\" + configName + Global.fileConfigFormat;
	}

	public static void SetFileLog() {
		String filePath = Global.ProjectPath + Files.DirLogs;
		CreateDirectory(filePath);
		Files.FileLog = filePath + Scribe.DateTimeGetStamp() + Global.fileLogFormat;
	}

	public static void SetFileData() {
		String filePath = Global.ProjectPath + Files.DirData;
		CreateDirectory(filePath);
		Files.FileData = filePath + Global.TestSuiteName + Global.fileDataFormat;
	}

	public static void SetFileTestActions() {
		String filePath = Global.ProjectPath + Files.DirTestActions;
		CreateDirectory(filePath);
		Files.FileTestActions = filePath + Global.TestActionName + Global.fileDataFormat;
	}
	
	public static void SetFileTestObjects() {
		String filePath = Global.ProjectPath + Files.DirTestObjects;
		CreateDirectory(filePath);
		Files.FileTestObjects = filePath + Global.ObjectName + Global.fileDataFormat;
	}
	
	public static void SetFileTestCase() {
		String filePath = Global.ProjectPath + Files.DirTestCases;
		CreateDirectory(filePath);
		Files.FileTestCase = filePath + Global.TestCaseName + Global.fileDataFormat;
	}
	
	public static void SetFileTestSuite() {
		String filePath = Global.ProjectPath + Files.DirTestSuites;
		CreateDirectory(filePath);
		Files.FileTestSuite = filePath + Global.TestSuiteName + Global.fileDataFormat;
	}

	public static void SetFileResults() {
		String filePath = Global.ProjectPath + Files.DirResults;
		CreateDirectory(filePath);
		Files.FileTestResults = filePath + Global.TestSuiteName + Global.fileDataFormat;
	}

	public static void SetFileCSS() {
		String filePath = Global.ProjectPath + Files.DirReports;
		CreateDirectory(filePath);
		Files.FileCSS = filePath + "reports.css";
	}	
	
	public static void SetFileReport() {
		String filePath = Global.ProjectPath + Files.DirReports;
		CreateDirectory(filePath);
		Files.FileReport = filePath + Global.ProjectName +"_"+ Scribe.DateTimeGetStamp() + Global.fileReportFormat;
	}

	public static void SetFilesScreenshot() {
		String filePath = Global.ProjectPath + Files.DirScreenshots;
		CreateDirectory(filePath);
		filePath += String.format("%03d",Global.ctrSShots) + "_";
		filePath += Global.TestSuiteName.replaceAll("\\s", "") + ".";
		if (Global.ObjectName != null) {
			filePath += Global.ObjectName.replaceAll("\\s", "") + ".";
		}
		if(Global.TestCaseName != null) {
			Files.FileScreenshot += Global.TestCaseName.replaceAll("\\s", "") + "."; 
		} 
		

		Files.FileScreenshot = filePath;

		Files.FileScreenshot += Global.fileScreenshotFormat;
		Global.ctrSShots ++;
	}

	
}
