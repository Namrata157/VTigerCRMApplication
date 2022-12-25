package genericLibraries;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerImplementation implements ITestListener{

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println(" On Test Start");
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println(" On Test Success");
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println(" On Test Failure");
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println(" On Test Skipped");
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		System.out.println(" On Start");
		
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println(" On Finish");
		
	}

}
