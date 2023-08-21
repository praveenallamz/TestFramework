package step_definitions;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;

import org.apache.commons.compress.utils.IOUtils;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.LogStatus;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import testlink.api.java.client.TestLinkAPIResults;
import utilities.ConfigReader;
import utilities.DriverUtil;
import utilities.GlobalUtil;
import utilities.HTMLReportUtil;
import utilities.KeywordUtil;
import utilities.LogUtil;

public class Hooks {

	String imagePath;
	String pathForLogger;
	String testCaseDescription;
	
	public static String imagePath1;
	public static String concatt =".";

	@Before("@Amazon")
	public void beforeMethodAmazon(Scenario scenario) {

		if (scenario.getName().contains("_"))
			testCaseDescription = scenario.getName().split("_")[1];
		else
			testCaseDescription = scenario.getName();
		RunCukesTest.logger = RunCukesTest.extent.startTest(testCaseDescription);

		LogUtil.infoLog(getClass(),
				"\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Test Started: " + scenario.getName());

		LogUtil.infoLog("Test Environment",
				"Test is executed in Environment: " + GlobalUtil.getCommonSettings().getExecutionEnv());

		LogUtil.infoLog("TestStarted", "Test is started with browser: " + GlobalUtil.getCommonSettings().getBrowser());
		GlobalUtil.setDriver(DriverUtil.getBrowser(GlobalUtil.getCommonSettings().getExecutionEnv(),GlobalUtil.getCommonSettings().getBrowser()));
	}

	@Before("@APItests")
	public void beforeAPIMethod(Scenario scenario) {

		if (scenario.getName().contains("_"))
			testCaseDescription = scenario.getName().split("_")[1];
		else
			testCaseDescription = scenario.getName();
		RunCukesTest.logger = RunCukesTest.extent.startTest(testCaseDescription);

		LogUtil.infoLog(getClass(),
				"\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "API Test Started: " + scenario.getName());
		LogUtil.infoLog("Test Started",
				"Test is started using base URL: " + GlobalUtil.getCommonSettings().getRestURL());
	}

	@Before("@APItestsAcceptance")
	public void beforeAPIMethodAcceptance(Scenario scenario) {

		if (scenario.getName().contains("_"))
			testCaseDescription = scenario.getName().split("_")[1];
		else
			testCaseDescription = scenario.getName();
		RunCukesTest.logger = RunCukesTest.extent.startTest(testCaseDescription);

		LogUtil.infoLog(getClass(),
				"\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "API Test Started: " + scenario.getName());
		LogUtil.infoLog("Test Started",
				"Test is started using base URL: " + GlobalUtil.getCommonSettings().getRestURL());
	}

	@Before("@APItestsSmoke")
	public void beforeAPIMethodSmoke(Scenario scenario) {

		if (scenario.getName().contains("_"))
			testCaseDescription = scenario.getName().split("_")[1];
		else
			testCaseDescription = scenario.getName();
		RunCukesTest.logger = RunCukesTest.extent.startTest(testCaseDescription);

		LogUtil.infoLog(getClass(),
				"\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "API Test Started: " + scenario.getName());
		LogUtil.infoLog("Test Started",
				"Test is started using base URL: " + GlobalUtil.getCommonSettings().getRestURL());
	}

	@Before("@MobileTest")
	public void beforeMobileMethod(Scenario scenario) throws Exception {

		testCaseDescription = scenario.getName().split("_")[1];
		RunCukesTest.logger = RunCukesTest.extent.startTest(testCaseDescription);

		LogUtil.infoLog(getClass(),"\n+---------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Mobile Tests Started: " + scenario.getName());
		LogUtil.infoLog(getClass(),"\n+---------------------------------------------------------------------------------------------+");
		LogUtil.infoLog("Mobile Test Environment","Mobile Test is executed in OS: " + GlobalUtil.getCommonSettings().getAndroidName());

		//GlobalUtil.setMDriver(DriverUtil.getMobileApp());
	  // GlobalUtil.setMDriver(DriverUtil.getMobileApp(GlobalUtil.getCommonSettings().getExecutionEnv()));
	}

	@Before("@MobileTestAcceptance")
	public void beforeMobileMethodAcceptance(Scenario scenario) throws Exception {

		testCaseDescription = scenario.getName().split("_")[1];
		RunCukesTest.logger = RunCukesTest.extent.startTest(testCaseDescription);

		LogUtil.infoLog(getClass(),
				"\n+----------------------------------------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Mobile Tests Started: " + scenario.getName());

		LogUtil.infoLog("Mobile Test Environment","Mobile Test is executed in OS: " + GlobalUtil.getCommonSettings().getAndroidName());

		// GlobalUtil.setMDriver(DriverUtil.getMobileApp());
		GlobalUtil.setMDriver(DriverUtil.getMobileApp(GlobalUtil.getCommonSettings().getExecutionEnv()));
	}

	@Before("@MobileTestSmoke")
	public void beforeMobileMethodSmoke(Scenario scenario) throws Exception {

		testCaseDescription = scenario.getName().split("_")[1];
		RunCukesTest.logger = RunCukesTest.extent.startTest(testCaseDescription);

		LogUtil.infoLog(getClass(),"\n+-----------------------------------------------------------------------------------------------+");
		LogUtil.infoLog(getClass(), "Mobile Tests Started: " + scenario.getName());

		LogUtil.infoLog("Mobile Test Environment","Mobile Test is executed in OS: " + GlobalUtil.getCommonSettings().getAndroidName());

		// GlobalUtil.setMDriver(DriverUtil.getMobileApp());
		//GlobalUtil.setMDriver(DriverUtil.getMobileApp(GlobalUtil.getCommonSettings().getExecutionEnv()));
	}

	@After("@Amazon")
	public void afterMethodSmoke(Scenario scenario) {
		String testName = scenario.getName().split("_")[0].trim();
		if (scenario.isFailed()) {
			try {
				String scFileName = "ScreenShot_" + System.currentTimeMillis();
				//String screenshotFilePath = "./ExecutionReports/FailedScreenshots/" + scFileName + ".png";
				String screenshotFilePath = ConfigReader.getValue("screenshotPath") + "\\" + scFileName + ".png";
				imagePath = HTMLReportUtil.testFailTakeScreenshot(screenshotFilePath);
				
				 InputStream is = new FileInputStream(imagePath);
				 byte[] imageBytes = IOUtils.toByteArray(is);
			     Thread.sleep(2000);
			     String base64 = Base64.getEncoder().encodeToString(imageBytes);
			     pathForLogger = RunCukesTest.logger.addBase64ScreenShot("data:image/png;base64,"+base64);
				//pathForLogger = RunCukesTest.logger.addScreenCapture(base64);
		         RunCukesTest.logger.log(LogStatus.FAIL,HTMLReportUtil.failStringRedColor("Failed at point: " + pathForLogger) + GlobalUtil.e);

				scenario.write("Current Page URL is " + GlobalUtil.getDriver().getCurrentUrl());

				byte[] screenshot = KeywordUtil.takeScreenshot(imagePath);
				scenario.embed(screenshot, "image/png");

				// report the bug
//				String bugID = "Please check the Bug tool Configuration";
//				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Mantis")) {
//					bugID = MantisReport
//							.reporIssue(scenario.getName(), GlobalUtil.ErrorMsg, "General",
//									"Automated on Browser: " + GlobalUtil.getCommonSettings().getBrowser()
//											+ " and Build Name: " + GlobalUtil.getCommonSettings().getBuildNumber(),
//											imagePath);
//					//screenshotFilePath);
//				}
//
//				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Jira")) {
//					// getting the os name to report the bug
//					String osName = System.getProperty("os.name");
//					if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")) {
//						osName = GlobalUtil.getCommonSettings().getRemoteOS();
//					}
//					bugID = GlobalUtil.jiraapi.reporIssue(scenario.getName(),
//							"Automated on OS: " + osName + ",\n Automated on Browser: "
//									+ GlobalUtil.getCommonSettings().getBrowser() + ",\n Build Name: "
//									+ GlobalUtil.getCommonSettings().getBuildNumber() + ". \n\n\n\n"
//									+ GlobalUtil.ErrorMsg,
//									imagePath);
//				}
//
//				// updating the results in Testmangement tool
//				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("TestLink")) {
//					GlobalUtil.testlinkapi
//							.updateTestLinkResult(
//									testName, "Please find the BUGID in "
//											+ GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID,
//									TestLinkAPIResults.TEST_PASSED);
//				}
//				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
//					GlobalUtil.jiraapi.updateJiraTestResults(testName, "Please find the BUGID in "
//							+ GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID, "Fail");
//				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			LogUtil.infoLog("TestEnded",
					"Test has ended closing browser: " + GlobalUtil.getCommonSettings().getBrowser());
			// updating the results in Testmangement tool
//			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("TestLink")) {
//				GlobalUtil.testlinkapi.updateTestLinkResult(testName, "This test is passed",
//						TestLinkAPIResults.TEST_PASSED);
//			}
//			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
//				GlobalUtil.jiraapi.updateJiraTestResults(testName, "This test is passed", "Pass");
//			}
		}

		// close the browsers
		//DriverUtil.closeAllDriver();
		GlobalUtil.getDriver().quit();
		RunCukesTest.extent.endTest(RunCukesTest.logger);
	}

	@After("@APItests")
	public void afterAPIMethod(Scenario scenario) {
		String testName = scenario.getName().split("_")[0].trim();
		if (scenario.isFailed()) {
			try {

				// report the bug
				String bugID = "Please check the Bug tool Configuration";
				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Mantis")) {
				//	bugID = MantisReport.reporIssue(scenario.getName(), GlobalUtil.ErrorMsg, "General",
							//" Build Name: " + GlobalUtil.getCommonSettings().getBuildNumber(), "RestAPI");
				}

				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Jira")) {
					// getting the os name to report the bug
					String osName = System.getProperty("os.name");
					if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")) {
						osName = GlobalUtil.getCommonSettings().getRemoteOS();
					}
					bugID = GlobalUtil.jiraapi.reporIssue(scenario.getName(),
							"Automated on OS: " + osName + "on Build Name: "
									+ GlobalUtil.getCommonSettings().getBuildNumber() + ". \n\n\n\n"
									+ GlobalUtil.ErrorMsg,
							"RestAPI");
				}

				// updating the results in Testmangement tool
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("TestLink")) {
					GlobalUtil.testlinkapi
							.updateTestLinkResult(
									testName, "Please find the BUGID in "
											+ GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID,
									TestLinkAPIResults.TEST_PASSED);
				}
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
					GlobalUtil.jiraapi.updateJiraTestResults(testName, "Please find the BUGID in "
							+ GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID, "Fail");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			LogUtil.infoLog("TestEnded", "API Test has ended ");
			// updating the results in Testmangement tool
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("TestLink")) {
				GlobalUtil.testlinkapi.updateTestLinkResult(testName, "This test is passed",
						TestLinkAPIResults.TEST_PASSED);
			}
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
				GlobalUtil.jiraapi.updateJiraTestResults(testName, "This test is passed", "Pass");
			}
		}

		// close the browsers
		RunCukesTest.extent.endTest(RunCukesTest.logger);
	}

	@After("@APItestsAcceptance")
	public void afterAPIMethodAcceptance(Scenario scenario) {
		String testName = scenario.getName().split("_")[0].trim();
		if (scenario.isFailed()) {
			try {

				// report the bug
				String bugID = "Please check the Bug tool Configuration";
				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Mantis")) {
					//bugID = MantisReport.reporIssue(scenario.getName(), GlobalUtil.ErrorMsg, "General",
						//	" Build Name: " + GlobalUtil.getCommonSettings().getBuildNumber(), "RestAPI");
				}

				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Jira")) {
					// getting the os name to report the bug
					String osName = System.getProperty("os.name");
					if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")) {
						osName = GlobalUtil.getCommonSettings().getRemoteOS();
					}
					bugID = GlobalUtil.jiraapi.reporIssue(scenario.getName(),
							"Automated on OS: " + osName + "on Build Name: "
									+ GlobalUtil.getCommonSettings().getBuildNumber() + ". \n\n\n\n"
									+ GlobalUtil.ErrorMsg,
							"RestAPI");
				}

				// updating the results in Testmangement tool
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("TestLink")) {
					GlobalUtil.testlinkapi
							.updateTestLinkResult(
									testName, "Please find the BUGID in "
											+ GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID,
									TestLinkAPIResults.TEST_PASSED);
				}
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
					GlobalUtil.jiraapi.updateJiraTestResults(testName, "Please find the BUGID in "
							+ GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID, "Fail");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			LogUtil.infoLog("TestEnded", "API Test has ended ");
			// updating the results in Testmangement tool
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("TestLink")) {
				GlobalUtil.testlinkapi.updateTestLinkResult(testName, "This test is passed",
						TestLinkAPIResults.TEST_PASSED);
			}
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
				GlobalUtil.jiraapi.updateJiraTestResults(testName, "This test is passed", "Pass");
			}
		}

		// close the browsers
		RunCukesTest.extent.endTest(RunCukesTest.logger);
	}

	@After("@APItestsSmoke")
	public void afterAPIMethodSmoke(Scenario scenario) {
		String testName = scenario.getName().split("_")[0].trim();
		if (scenario.isFailed()) {
			try {

				// report the bug
				String bugID = "Please check the Bug tool Configuration";
				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Mantis")) {
					//bugID = MantisReport.reporIssue(scenario.getName(), GlobalUtil.ErrorMsg, "General",
						//	" Build Name: " + GlobalUtil.getCommonSettings().getBuildNumber(), "RestAPI");
				}

				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Jira")) {
					// getting the os name to report the bug
					String osName = System.getProperty("os.name");
					if (GlobalUtil.getCommonSettings().getExecutionEnv().equalsIgnoreCase("Remote")) {
						osName = GlobalUtil.getCommonSettings().getRemoteOS();
					}
					bugID = GlobalUtil.jiraapi.reporIssue(scenario.getName(),
							"Automated on OS: " + osName + "on Build Name: "
									+ GlobalUtil.getCommonSettings().getBuildNumber() + ". \n\n\n\n"
									+ GlobalUtil.ErrorMsg,
							"RestAPI");
				}

				// updating the results in Testmangement tool
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("TestLink")) {
					GlobalUtil.testlinkapi
							.updateTestLinkResult(
									testName, "Please find the BUGID in "
											+ GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID,
									TestLinkAPIResults.TEST_PASSED);
				}
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
					GlobalUtil.jiraapi.updateJiraTestResults(testName, "Please find the BUGID in "
							+ GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID, "Fail");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			LogUtil.infoLog("TestEnded", "API Test has ended ");
			// updating the results in Testmangement tool
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("TestLink")) {
				GlobalUtil.testlinkapi.updateTestLinkResult(testName, "This test is passed",
						TestLinkAPIResults.TEST_PASSED);
			}
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
				GlobalUtil.jiraapi.updateJiraTestResults(testName, "This test is passed", "Pass");
			}
		}

		// close the browsers
		RunCukesTest.extent.endTest(RunCukesTest.logger);
	}

	@After("@MobileTest")
	public void afterMobileMethod(Scenario scenario) {
		String testName = scenario.getName().split("_")[0].trim();
		if (scenario.isFailed()) {
			try {
				String scFileName = "ScreenShot_" + System.currentTimeMillis();
				String screenshotFilePath = ConfigReader.getValue("screenshotPath") + "\\" + scFileName + ".png";

				imagePath = HTMLReportUtil.testFailMobileTakeScreenshot(screenshotFilePath);
				 pathForLogger = RunCukesTest.logger.addScreenCapture(imagePath);
				 RunCukesTest.logger.log(LogStatus.FAIL, HTMLReportUtil.failStringRedColor("Failed at point: " + pathForLogger) + GlobalUtil.e);

				scenario.write("Current Page URL is " +
				 GlobalUtil.getMDriver().getCurrentUrl());

				 byte[] screenshot = KeywordUtil.takeMobileScreenshot(screenshotFilePath);

				scenario.embed(screenshot, "image/png");

				// report the bug
				String bugID = "Please check the Bug tool Configuration";
				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Mantis")) {
					//bugID = MantisReport.reporIssue(scenario.getName(), GlobalUtil.ErrorMsg, "General",
						//	"Automated on Android Device Version: " + GlobalUtil.getCommonSettings().getAndroidVersion()
						//			+ " and Build Name: " + GlobalUtil.getCommonSettings().getBuildNumber(),
						//	screenshotFilePath);
				}

				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Jira")) {
					bugID = GlobalUtil.jiraapi.reporIssue(scenario.getName(),
							"Automated on Android Device Version: " + GlobalUtil.getCommonSettings().getAndroidVersion()
									+ ",\n Build Name: " + GlobalUtil.getCommonSettings().getBuildNumber()
									+ ". \n\n\n\n" + GlobalUtil.ErrorMsg,
							screenshotFilePath);
				}

				// updating the results in Testmangement tool
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("TestLink")) {
					GlobalUtil.testlinkapi
							.updateTestLinkResult(
									testName, "Please find the BUGID in "
											+ GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID,
									TestLinkAPIResults.TEST_PASSED);
				}
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
					GlobalUtil.jiraapi.updateJiraTestResults(testName, "Please find the BUGID in "
							+ GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID, "Fail");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			LogUtil.infoLog("TestEnded",
					"Test has ended closing Application: " + GlobalUtil.getCommonSettings().getAndroidName());
			// updating the results in Testmangement tool
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("TestLink")) {
				GlobalUtil.testlinkapi.updateTestLinkResult(testName, "This test is passed",
						TestLinkAPIResults.TEST_PASSED);
			}
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
				GlobalUtil.jiraapi.updateJiraTestResults(testName, "This test is passed", "Pass");
			}
		}

		// close the browsers

		// We need to write the quit for local mobile device for time being we commented for browser stack
		GlobalUtil.getMDriver().quit();
		//DriverUtil.closeAllDriver();
		RunCukesTest.extent.endTest(RunCukesTest.logger);
	}

	@After("@MobileTestAcceptance")
	public void afterMobileMethodAcceptance(Scenario scenario) {
		String testName = scenario.getName().split("_")[0].trim();
		if (scenario.isFailed()) {
			try {
				String scFileName = "ScreenShot_" + System.currentTimeMillis();
				String screenshotFilePath = ConfigReader.getValue("screenshotPath") + "\\" + scFileName + ".png";

				imagePath = HTMLReportUtil.testFailMobileTakeScreenshot(screenshotFilePath);
				pathForLogger = RunCukesTest.logger.addScreenCapture(imagePath);
				RunCukesTest.logger.log(LogStatus.FAIL,
						HTMLReportUtil.failStringRedColor("Failed at point: " + pathForLogger) + GlobalUtil.e);

				// scenario.write("Current Page URL is " +
				// GlobalUtil.getDriver().getCurrentUrl());

				byte[] screenshot = KeywordUtil.takeMobileScreenshot(screenshotFilePath);
				scenario.embed(screenshot, "image/png");

				// report the bug
				String bugID = "Please check the Bug tool Configuration";
				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Mantis")) {
					//bugID = MantisReport.reporIssue(scenario.getName(), GlobalUtil.ErrorMsg, "General",
							//"Automated on Android Device Version: " + GlobalUtil.getCommonSettings().getAndroidVersion()
							//		+ " and Build Name: " + GlobalUtil.getCommonSettings().getBuildNumber(),
						//	screenshotFilePath);
				}

				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Jira")) {
					bugID = GlobalUtil.jiraapi.reporIssue(scenario.getName(),
							"Automated on Android Device Version: " + GlobalUtil.getCommonSettings().getAndroidVersion()
									+ ",\n Build Name: " + GlobalUtil.getCommonSettings().getBuildNumber()
									+ ". \n\n\n\n" + GlobalUtil.ErrorMsg,
							screenshotFilePath);
				}

				// updating the results in Testmangement tool
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("TestLink")) {
					GlobalUtil.testlinkapi
							.updateTestLinkResult(
									testName, "Please find the BUGID in "
											+ GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID,
									TestLinkAPIResults.TEST_PASSED);
				}
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
					GlobalUtil.jiraapi.updateJiraTestResults(testName, "Please find the BUGID in "
							+ GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID, "Fail");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			LogUtil.infoLog("TestEnded",
					"Test has ended closing Application: " + GlobalUtil.getCommonSettings().getAndroidName());
			// updating the results in Testmangement tool
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("TestLink")) {
				GlobalUtil.testlinkapi.updateTestLinkResult(testName, "This test is passed",
						TestLinkAPIResults.TEST_PASSED);
			}
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
				GlobalUtil.jiraapi.updateJiraTestResults(testName, "This test is passed", "Pass");
			}
		}

		// close the browsers
		GlobalUtil.getMDriver().quit();
		RunCukesTest.extent.endTest(RunCukesTest.logger);
	}

	@After("@MobileTestSmoke")
	public void afterMobileMethodSmoke(Scenario scenario) {
		String testName = scenario.getName().split("_")[0].trim();
		if (scenario.isFailed()) {
			try {
				String scFileName = "ScreenShot_" + System.currentTimeMillis();
				String screenshotFilePath = ConfigReader.getValue("screenshotPath") + "\\" + scFileName + ".png";

				imagePath = HTMLReportUtil.testFailMobileTakeScreenshot(screenshotFilePath);
				pathForLogger = RunCukesTest.logger.addScreenCapture(imagePath);
				RunCukesTest.logger.log(LogStatus.FAIL,
						HTMLReportUtil.failStringRedColor("Failed at point: " + pathForLogger) + GlobalUtil.e);

				// scenario.write("Current Page URL is " +
				// GlobalUtil.getDriver().getCurrentUrl());

				byte[] screenshot = KeywordUtil.takeMobileScreenshot(screenshotFilePath);
				scenario.embed(screenshot, "image/png");

				// report the bug
				String bugID = "Please check the Bug tool Configuration";
				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Mantis")) {
					//bugID = MantisReport.reporIssue(scenario.getName(), GlobalUtil.ErrorMsg, "General",
						//	"Automated on Android Device Version: " + GlobalUtil.getCommonSettings().getAndroidVersion()
								//	+ " and Build Name: " + GlobalUtil.getCommonSettings().getBuildNumber(),
						//	screenshotFilePath);
				}

				if (GlobalUtil.getCommonSettings().getBugToolName().equalsIgnoreCase("Jira")) {
					bugID = GlobalUtil.jiraapi.reporIssue(scenario.getName(),
							"Automated on Android Device Version: " + GlobalUtil.getCommonSettings().getAndroidVersion()
									+ ",\n Build Name: " + GlobalUtil.getCommonSettings().getBuildNumber()
									+ ". \n\n\n\n" + GlobalUtil.ErrorMsg,
							screenshotFilePath);
				}

				// updating the results in Testmangement tool
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("TestLink")) {
					GlobalUtil.testlinkapi
							.updateTestLinkResult(
									testName, "Please find the BUGID in "
											+ GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID,
									TestLinkAPIResults.TEST_PASSED);
				}
				if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
					GlobalUtil.jiraapi.updateJiraTestResults(testName, "Please find the BUGID in "
							+ GlobalUtil.getCommonSettings().getBugToolName() + " : " + bugID, "Fail");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			LogUtil.infoLog("TestEnded",
					"Test has ended closing Application: " + GlobalUtil.getCommonSettings().getAndroidName());
			// updating the results in Testmangement tool
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("TestLink")) {
				GlobalUtil.testlinkapi.updateTestLinkResult(testName, "This test is passed",
						TestLinkAPIResults.TEST_PASSED);
			}
			if (GlobalUtil.getCommonSettings().getManageToolName().equalsIgnoreCase("Jira")) {
				GlobalUtil.jiraapi.updateJiraTestResults(testName, "This test is passed", "Pass");
			}
		}

		// close the browsers
		GlobalUtil.getMDriver().quit();
		RunCukesTest.extent.endTest(RunCukesTest.logger);
	}

}