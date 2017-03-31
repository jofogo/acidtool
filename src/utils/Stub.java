package utils;

public class Stub {
	
	public static void Project() {
		Debug.Verbosity = 4;
		try {
			Global.ProjectName.isEmpty();
		} catch (NullPointerException npe) {
			Global.ProjectName="DCC";
			Debug.Trace("Stub project: " + Global.ProjectName);
		}
		try {
			Global.ProjectPath.isEmpty();
		} catch (NullPointerException npe) {
			Global.ProjectPath="c:\\TestAutomation_Projects\\DCC";
			Files.CreateDirectory(Global.ProjectPath);
			Files.CheckIfExists(Global.ProjectPath);
			Debug.Trace("Stub project path: " + Global.ProjectPath);
		}
	}
	
	public static void ProjectConfig() {
		Collections.writeConfig("Project Name,DCC" + Global.ProjectName);
		Collections.writeConfig("Browser,FireFox");
		Collections.writeConfig("Base URL," + "http://www.dccameras.com.au");
		Collections.writeConfig("Test Suite,Execute");
		Collections.writeConfig("WelcomeScreen,Y");
	}
	
	public static void CSS() {
		Files.SetFileCSS();
		String FileCSS = Files.FileCSS;
		if (! Files.CheckIfExists(FileCSS)) {
			Collections.writeCSS("<style>");
			Collections.writeCSS("body { font-family: Arial, Helvetica, sans-serif; color: AFC226; background-color: black; }");
			Collections.writeCSS("h1 { font-size: 22px; text-align: center; }");
			Collections.writeCSS("h2 { font-size: 20px; text-transform: capitalize; text-align: center; }");
			Collections.writeCSS("h3 { font-size: 18px; text-align: center; text-transform: capitalize; color: black; }");
			Collections.writeCSS("div { width:100% position: absolute; }");
			Collections.writeCSS("div.params { float: left; padding: 0px 5px 5px 50px; text-align: left; }");
			Collections.writeCSS("div.logo { float: right; padding: 0px 50px 5px 5px; }");
			Collections.writeCSS("div.charts{ clear: both; padding: 0px 5px 5px 5px; background-color:C3CFCE; }");
			Collections.writeCSS("div.steps { padding: 0px 5px 5px 5px; }");
			Collections.writeCSS("span { word-wrap: break-word; display: inline-block; }");
			Collections.writeCSS("span.perfgraph { width: 550px; margin: 5px; padding: 5px; }");
			Collections.writeCSS("span.summchart { width: 550px; margin: 5px; padding: 5px; }");
			Collections.writeCSS("table.test_steps { width: 100%; border: 1px; border-style: solid; }");
			Collections.writeCSS("tr.header { font-weight: bold; text-align: center; }");
			Collections.writeCSS("tr.step_title { font-weight: bold; background-color: B0B0B0; }");
			Collections.writeCSS("tr.step_odd { color: black; background-color: rgb(142,153,61); }");
			Collections.writeCSS("tr.step_even { }");
			Collections.writeCSS("</style>");
			Files.WriteToFile(Global.CSS, FileCSS);
			Debug.Trace("Stub CSS file path: " + FileCSS);
		}
		
	}
}
