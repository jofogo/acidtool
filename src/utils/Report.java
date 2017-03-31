package utils;

import java.io.IOException;
import java.util.List;


public class Report {

	public static void SummarizePerformance() {
		Debug.Dump(Global.PerformanceResults);
	}
	
	private static void HTMLHeader() {
		WriteToHTMLSummary("<head>");
		WriteToHTMLSummary("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">");
		WriteToHTMLSummary("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.1.3/Chart.min.js\"></script>");
		WriteToHTMLSummary("<title>" + Global.ProjectName + " Summary Report as of " + Global.ProcessEnd + "</title>");
		try {
			Files.SetFileCSS();
			String[] cssContents = Files.ReadFromFile(Files.FileCSS);
			for (String cssLine: cssContents) {
				WriteToHTMLSummary(cssLine);
			}
		} catch (IOException ioe) {
			Debug.ExceptionError(ioe);
		}			
		WriteToHTMLSummary("</head>");
	}
	
	private static void Title() {
		WriteToHTMLSummary("<div class=\"header\" align=\"center\">");
		WriteToHTMLSummary("<h1>" + Global.ProjectName + " Test Report as of " + Global.ProcessEnd + "</h1>");			
	}

	private static void Logo() {
		WriteToHTMLSummary("<div class=\"logo\">");
		WriteToHTMLSummary("<img src=\"./company_logo.png\" />");
		WriteToHTMLSummary("</div>");
	}
	
	private static void Summary() {
		WriteToHTMLSummary("<div class=\"params\" >");
		WriteToHTMLSummary("<h2> Over-all Summary </h2>");
		WriteToHTMLSummary("<b> Project: </b> " + Global.ProjectName + "<br />");
		WriteToHTMLSummary("<b> Base URL: </b> " + Global.BaseURL + "<br />");
		WriteToHTMLSummary("<b> Browser: </b> " + Global.BrowserName + "<br />");
		WriteToHTMLSummary("<b> Started: </b> " + Global.ProcessStart + "<br />");
		WriteToHTMLSummary("<b> Ended: </b> " + Global.ProcessEnd + "<br />");
		WriteToHTMLSummary("<b> Elapsed Time: </b> " + Global.ProcessElapsed + " secs(s) <br />");
		WriteToHTMLSummary("</div>");
	}
	
	private static void Charts() {
		WriteToHTMLSummary("<div class=\"charts\">");
		ChartLeft();
		ChartRight();
		WriteToHTMLSummary("</div>");
		WriteToHTMLSummary("</div>");			
	}
	
	private static void ChartLeft() {
		String ChartTitle = "Performance Summary";
		String labels="";
		String data="";
		String templine = "";
		
		for (int perfIdx = 0; perfIdx < Collections.ListGetTotalItems(Global.PerformanceResults); perfIdx++) {
			List<String> perfLine = Scribe.StringConvertCSVToList(Collections.ListGetValueFromIndex(Global.PerformanceResults, perfIdx));
			labels += "\"" + perfLine.get(0) + "\",";
			data += Integer.parseInt(perfLine.get(3)) + ",";
		}
		
		data = Scribe.StringRemoveLastChar(data);
		labels = Scribe.StringRemoveLastChar(labels);

		WriteToHTMLSummary("<span class=\"perfgraph\" align=\"center\">");
		WriteToHTMLSummary("<h3> " + ChartTitle + "</h3>");			
		WriteToHTMLSummary("<br />");			
		WriteToHTMLSummary("<canvas id=\"chart_performance\" width=\"400\" height=\"250\"></canvas>");			
		WriteToHTMLSummary("<script>");			
		WriteToHTMLSummary("var ctx = document.getElementById(\"chart_performance\");");			
		WriteToHTMLSummary("var chart_performance = new Chart(ctx, {");			
		WriteToHTMLSummary("type: 'line',");			
		WriteToHTMLSummary("data: {");
		templine = "labels: [" + labels +"],";
		WriteToHTMLSummary(templine); //
		WriteToHTMLSummary("datasets: [{");			
		WriteToHTMLSummary(" label: 'Time to complete(secs)',");			
		WriteToHTMLSummary(" borderColor: \"#000000\",");			
		WriteToHTMLSummary(" pointBorderWidth: 2,");			
		WriteToHTMLSummary(" pointHoverRadius: 5,");			
		WriteToHTMLSummary("");			
		templine = "data: [" + data + "]";
		WriteToHTMLSummary(templine); //
		WriteToHTMLSummary("}]");			
		WriteToHTMLSummary("},");			
		WriteToHTMLSummary("options: { ");			
		WriteToHTMLSummary("scales: {");			
		WriteToHTMLSummary("yAxes: [{");			
		WriteToHTMLSummary("ticks: {");			
		WriteToHTMLSummary("beginAtZero:true,");
		WriteToHTMLSummary("fontColor: \"#000000\"");
		WriteToHTMLSummary("}");			
		WriteToHTMLSummary("}]");			
		WriteToHTMLSummary("}");			
		WriteToHTMLSummary("}");			
		WriteToHTMLSummary("});");			
		WriteToHTMLSummary("</script>");			
		WriteToHTMLSummary("</span>");			
		
	}

	private static void ChartRight() {
		String ChartTitle = "Execution Summary";
		String labels="";
		String data="";
		String templine = "";
		
		for (int testIdx = 0; testIdx < Collections.ListGetTotalItems(Global.TestSummary); testIdx++) {
			List<String> testLine = Scribe.StringConvertCSVToList(Collections.ListGetValueFromIndex(Global.TestSummary, testIdx));
			labels += "\"" + testLine.get(1) + "\",";
			data += testLine.get(7) + ",";
		}
		
		data = Scribe.StringRemoveLastChar(data);
		labels = Scribe.StringRemoveLastChar(labels);

		WriteToHTMLSummary("<span class=\"summchart\" align=\"center\">");
		WriteToHTMLSummary("<h3> " + ChartTitle + " </h3>");			
		WriteToHTMLSummary("<br />");			
		WriteToHTMLSummary("<canvas id=\"chart_summary\" width=\"400\" height=\"250\"></canvas>");			
		WriteToHTMLSummary("<script>");			
		WriteToHTMLSummary("var ctx = document.getElementById(\"chart_summary\");");			
		WriteToHTMLSummary("var chart_performance = new Chart(ctx, {");			
		WriteToHTMLSummary("type: 'line',");			
		WriteToHTMLSummary("data: {");
		templine = "labels: [" + labels +"],";
		WriteToHTMLSummary(templine); //
		WriteToHTMLSummary("datasets: [{");			
		WriteToHTMLSummary(" label: 'Execution rate(%)',");			
		WriteToHTMLSummary(" borderColor: \"#000000\",");			
		WriteToHTMLSummary(" pointBorderWidth: 2,");			
		WriteToHTMLSummary(" pointHoverRadius: 5,");			
		WriteToHTMLSummary("");			
		templine = "data: [" + data + "]";
		WriteToHTMLSummary(templine); //
		WriteToHTMLSummary("}]");			
		WriteToHTMLSummary("},");			
		WriteToHTMLSummary("options: { ");			
		WriteToHTMLSummary("scales: {");			
		WriteToHTMLSummary("yAxes: [{");			
		WriteToHTMLSummary("ticks: {");			
		WriteToHTMLSummary("beginAtZero:true,");
		WriteToHTMLSummary("fontColor: \"#000000\"");
		WriteToHTMLSummary("}");			
		WriteToHTMLSummary("}]");			
		WriteToHTMLSummary("}");			
		WriteToHTMLSummary("}");			
		WriteToHTMLSummary("});");			
		WriteToHTMLSummary("</script>");			
		WriteToHTMLSummary("</span>");			
		
	}
	
	private static void SuiteBody() {
		for (int perfIdx = 0; perfIdx < Collections.ListGetTotalItems(Global.PerformanceResults); perfIdx++) {
			List<String> perfLine = Scribe.StringConvertCSVToList(Collections.ListGetValueFromIndex(Global.PerformanceResults, perfIdx));
			if (perfIdx % 2 == 0 || perfIdx == 0) {
				WriteToHTMLSummary("<tr class=\"step_odd\">");
			} else {
				WriteToHTMLSummary("<tr class=\"step_even\">");
				
			}
			String perfData = "";
			perfData += "<td> " + perfLine.get(0) + " </td> ";
			perfData += "<td align=\"center\"> " + perfLine.get(1) + " </td> ";
			perfData += "<td align=\"center\"> " + perfLine.get(2) + " </td> ";
			perfData += "<td align=\"right\"> " + perfLine.get(3) + " </td> ";
			WriteToHTMLSummary(perfData);
			WriteToHTMLSummary("</tr>");
		}
	}
	
	private static void Suite() {
		WriteToHTMLSummary("<br /> <div class=\"test_steps\">");
		WriteToHTMLSummary("<h2> Tests Suites </h2>");
		WriteToHTMLSummary("<table class = \"test_steps\">");
		WriteToHTMLSummary("<tr class=\"header\">");
		WriteToHTMLSummary("<td> Suite Name </td>");
		WriteToHTMLSummary("<td> Started </td>");
		WriteToHTMLSummary("<td> Ended </td>");
		WriteToHTMLSummary("<td> Elapsed (secs) </td>");
		WriteToHTMLSummary("</tr>");
		SuiteBody();
		WriteToHTMLSummary("</table>");		
	}
	
	
	private static void TestCase() {
		WriteToHTMLSummary("<br /> <div class=\"steps\">");
		WriteToHTMLSummary("<h2> Tests </h2>");
		WriteToHTMLSummary("<table class = \"test_steps\">");
		WriteToHTMLSummary("<tr class=\"header\">");
		WriteToHTMLSummary("<td> Test Suite </td>");
		WriteToHTMLSummary("<td> Test Case </td>");
		WriteToHTMLSummary("<td> Started </td>");
		WriteToHTMLSummary("<td> Ended </td>");
		WriteToHTMLSummary("<td> Elapsed (secs) </td>");
		WriteToHTMLSummary("<td> Passed </td>");
		WriteToHTMLSummary("<td> Failed </td>");
		WriteToHTMLSummary("<td> Total </td>");
		WriteToHTMLSummary("<td> Rate (%) </td>");
		WriteToHTMLSummary("<td> Status </td>");
		WriteToHTMLSummary("</tr>");
		TestCaseBody();
		WriteToHTMLSummary("</table>");	
		WriteToHTMLSummary("</div>");	
		WriteToHTMLSummary("</div>");	
	}
	
	private static void TestCaseBody() {
		for (int testIdx = 0; testIdx < Collections.ListGetTotalItems(Global.TestSummary); testIdx++) {
			List<String> testLine = Scribe.StringConvertCSVToList(Collections.ListGetValueFromIndex(Global.TestSummary, testIdx));
		if (testIdx % 2 == 0 || testIdx == 0) {
				WriteToHTMLSummary("<tr class=\"step_odd\">");
			} else {
				WriteToHTMLSummary("<tr class=\"step_even\">");
				
			}
			String testdata = "";
			testdata += "<td> " + testLine.get(0) + " </td> ";
			testdata += "<td> " + testLine.get(1) + " </td> ";
			testdata += "<td align=\"center\"> " + testLine.get(2) + " </td> ";
			testdata += "<td align=\"center\"> " + testLine.get(3) + " </td> ";
			testdata += "<td align=\"right\"> " + testLine.get(4) + " </td> ";
			testdata += "<td align=\"right\"> " + testLine.get(5) + " </td> ";
			testdata += "<td align=\"right\"> " + testLine.get(6) + " </td> ";
			testdata += "<td align=\"right\"> " + (Integer.valueOf(testLine.get(5)) + Integer.valueOf(testLine.get(6))) + " </td> ";
			testdata += "<td align=\"right\"> " + testLine.get(7) + " </td> ";
			testdata += "<td> " + testLine.get(8) + " </td>";
			WriteToHTMLSummary(testdata);
			WriteToHTMLSummary("</tr>");
		}
	}
	
	private static void WriteToHTMLSummary(String line) {
		Collections.writeReportAll(line);
	}
	
	public static void WriteHTMLReport() {
		
		WriteToHTMLSummary("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		WriteToHTMLSummary("<html>");
		HTMLHeader();
		WriteToHTMLSummary("<body>");
		Title();
		Logo();
		Summary();
		Charts();
		Suite();
		TestCase();
		

		WriteToHTMLSummary("</body>");
		Collections.writeReportAll("</html>");
		Files.SetFileReport();
		Files.WriteToFile(Global.ReportAll, Files.FileReport);
		
	}
}
