package com.qa.extentReportListener;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReportListener implements org.testng.IReporter{
	
	private ExtentReports extent;

	public void generateReport(List<XmlSuite> XmlSuites, List<ISuite> Suites, String OutputDirectory) {
		
		extent=new ExtentReports(OutputDirectory+File.separator+"Extent.html",true);
		
		for(ISuite suite: Suites){
			Map<String, ISuiteResult> result=suite.getResults();
			for(ISuiteResult r:result.values()){
				ITestContext context=r.getTestContext();
				bulidTestNodes(context.getPassedTests(),LogStatus.PASS);
				bulidTestNodes(context.getFailedTests(),LogStatus.FAIL);
				bulidTestNodes(context.getSkippedTests(),LogStatus.SKIP);
				
			}
			extent.flush();
			extent.close();
		}
	}
	
	public void bulidTestNodes(IResultMap tests,LogStatus status){
		ExtentTest test;
		if(tests.size()>0){
			for(ITestResult result:tests.getAllResults()){
				test=extent.startTest(result.getMethod().getMethodName());
				test.setStartedTime(getTime(result.getStartMillis()));
				test.setEndedTime(getTime(result.getEndMillis()));
				for(String group:result.getMethod().getGroups())
					test.assignCategory(group);
				
				if(result.getThrowable()!=null){
					test.log(status, result.getThrowable());
				}
				else {
					test.log(status, "Test" +status.toString().toLowerCase() + "ed");
				}
				extent.endTest(test);
			}
		}
	}
	
	private Date getTime(long millis){
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	
	}

}
