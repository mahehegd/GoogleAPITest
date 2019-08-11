package com.google.listeners;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Listener implements ITestListener, ISuiteListener, IInvokedMethodListener {

	@Override
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
		String textMsg = "Completed executing following method : " + returnMethodName(arg0.getTestMethod());
		 
		Reporter.log(textMsg, true);
		
	}

	@Override
	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
		String textMsg = "About to begin executing following method : " + returnMethodName(arg0.getTestMethod());

		Reporter.log(textMsg, true);

	}

	@Override
	public void onFinish(ISuite arg0) {
		Reporter.log("Completed executing Suite " + arg0.getName(), true);
		
	}

	@Override
	public void onStart(ISuite arg0) {
		Reporter.log("About to begin executing Suite " + arg0.getName(), true);
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailure(ITestResult arg0) {
		printTestResults(arg0);
		
	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		printTestResults(arg0);
		
	}

	@Override
	public void onTestStart(ITestResult arg0) {
		Reporter.log("The execution of the main test starts now");
		
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		printTestResults(arg0);
		
	}
	
	private String returnMethodName(ITestNGMethod method) {

		return method.getRealClass().getSimpleName() + "." + method.getMethodName();

	}
	
	private void printTestResults(ITestResult result) {

		Reporter.log("Test Method resides in " + result.getTestClass().getName(), true);

		if (result.getParameters().length != 0) {
			String params = null;
			for (Object parameter : result.getParameters()) {
				params += parameter.toString() + ",";
			}
			Reporter.log("Test Method had the following parameters : " + params, true);

		}
		String status = null;
		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			status = "Pass";
			break;
		case ITestResult.FAILURE:
			status = "Failed";
			break;
		case ITestResult.SKIP:
			status = "Skipped";
		}
		Reporter.log("Test Status: " + status, true);
	}

	@Override
	public void onFinish(ITestContext arg0) {
		Reporter.log("About to begin executing Test " + arg0.getName(), true);
	}

	@Override
	public void onStart(ITestContext arg0) {
		Reporter.log("Completed executing Suite " + arg0.getName(), true);
		
	}
}
