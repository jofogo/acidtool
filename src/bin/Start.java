package bin;

import org.apache.commons.lang3.math.NumberUtils;

import utils.Cleanup;
import utils.Debug;
import utils.Files;
import utils.Global;
import utils.Initializer;

import utils.Stub;
import utils.Suites;

public class Start {

	public static void main(String[] args) {
		/*Read command line flags and parameters*/
		for(int ctrArgs = 0; ctrArgs < args.length; ctrArgs+=2) {
			if (!args[ctrArgs].isEmpty() && !args[ctrArgs + 1].isEmpty()){
				String flag = args[ctrArgs];
				String parameter = args[ctrArgs + 1];
				switch (flag.toUpperCase()) {
/*				case "-P":
					Global.ProjectName = parameter;
					break;
*/				case "-V":
					if(NumberUtils.isDigits(parameter)) {
						Debug.Verbosity = Integer.parseInt(parameter);
					} else {
						Debug.Verbosity = 2;
					}
					break;
				case "-D":
					if(Files.CheckIfExists(parameter) && Files.CheckType(parameter)=="Directory") {
						Global.ProjectPath = parameter;
					}
					break;
				case "-S":
					if(Files.CheckIfExists(parameter) && Files.CheckType(parameter)=="File") {
						Global.configName = parameter;
					}
					break;
				}
			}
		}
		/*Start*/
		
		//Debug.Trace("Firefox driver: " + System.getProperty("user.dir").toString()+"\\libs\\geckodriver.exe");
		
		Initializer.Process();
		Stub.Project(); // For test purposes
		Initializer.Paths();
		Stub.ProjectConfig(); // For test purposes
		Initializer.Config();
		Stub.CSS(); //For test purposes
		Initializer.PageInTest();
		/*Read Test Suite*/
		if(Suites.GetTestListCount() > 0) {
			for (int ctrSuite = 1; ctrSuite <= Global.ctrTestSuites;ctrSuite++) {
				Suites.Load(ctrSuite);
				Suites.Summarize();
			}
		}
		
		/*End*/
		Debug.Log("---");
		Debug.Dump(Global.PerformanceResults);
		Debug.Log("---");
		Debug.Dump(Global.TestSummary);
		Debug.Log("---");
		Debug.Dump(Global.TestSteps);
		Cleanup.Process();
		
		
		/*
		 * 01-26 : TD:  Test case load, Action load, Action Summary, Test case summary
		 * HTML reports
		 * */
	}

}
