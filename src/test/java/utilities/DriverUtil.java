package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import mobileutil.AmazonAppFunctions;
import mobileutil.MobileKeywords;
import step_definitions.RunCukesTest;

/**
 * This DriverUtil class refer to browsers, os details, browser versions and
 * will close all browsers
 *
 */

public class DriverUtil {

	// public static final String USERNAME = "praveentx";
	// public static final String ACCESS_KEY =
	// "4f251008-770a-4208-a25c-15830ef46049";
	// public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY +
	// "@ondemand.saucelabs.com:443/wd/hub";

	public static int row;
	public static String NDevice_Name = null;
	public static String ODevice_Name;
	public static String username;

	public static final String IE = "IE";
	public static final String REMOTE = "BrowserStack";
	public static final String EDGE = "edge";
	public static final String CHROME = "Chrome";
	public static final String FIREFOX = "Firefox";
	private static Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();
	// public static AndroidDriver<MobileElement> Mdriver;

	private static HashMap<String, String> checkLogin = new HashMap<String, String>();
	public static String appium_ip_address = mobileutil.MobileKeywords.GetValue("appium_ip_address");
	public static String appium_port = mobileutil.MobileKeywords.GetValue("appium_port");
	public static DesiredCapabilities capabilities = new DesiredCapabilities();

	public static XSSFWorkbook wb;
	public static XSSFSheet sheet1;

	public static String userName = "praveenkumar168";
	public static String accessKey = "TBHicaS3wbThrurzWqPd";

	// public static String userName = "anil613";
	// public static String accessKey = "uzm1dXzhTn4zmrQG8zaq";

 

	/**
	 * will use this getting browser(chrome, ie, ff)
	 * 
	 * @param browserName
	 * @return
	 */
	private DriverUtil() {

	}

	public static AndroidDriver<MobileElement> getMobileApp(String exeEnv) throws Exception {

		if (exeEnv.equalsIgnoreCase(REMOTE)) {
			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "/src/test/resources/ExcelFiles/MobileDevicesList.xlsx");
			wb = new XSSFWorkbook(fis);
			sheet1 = wb.getSheet("AndroidList");
			// int rowCount=sheet1.getLastRowNum()-sheet1.getFirstRowNum();
			int rowCount = sheet1.getLastRowNum();
			for (int row = 1; row < rowCount; row++) {
				String ExecuteFlag = sheet1.getRow(row).getCell(4).getStringCellValue();
				if (ExecuteFlag.equals("Yes")) {
					ODevice_Name = sheet1.getRow(row).getCell(1).getStringCellValue();
					DataFormatter formatter = new DataFormatter();
					String Device_Version = formatter.formatCellValue(sheet1.getRow(row).getCell(2));
					System.out.println(ODevice_Name);
					System.out.println(Device_Version);
					DesiredCapabilities caps = new DesiredCapabilities();
					// caps.setCapability("browserstack.user", "praveenkumar168");
					// caps.setCapability("browserstack.key", "TBHicaS3wbThrurzWqPd");
					caps.setCapability("device", ODevice_Name);
					caps.setCapability("os_version", Device_Version);
					// caps.setCapability("autoAcceptAlerts",true);
					caps.setCapability("autoDismissAlerts", true);
					caps.setCapability("name", "Bstack-[Java]-Mobile Amazon Test");
					// caps.setCapability("app", "bs://27293e170f6da91d257bec9b98a0b8ae1373f293");
					// // This Is for Decathalon
					caps.setCapability("app", "bs://788a32cb56c8cabc2b50a7451ff86ca1c51fe625");
					// {"app_url":"bs://cde73770a410a14f0db359bf321bf784bc7cec90"}

					// caps.setCapability("app", "bs://c700ce60cf13ae8ed97705a55b8e022f13c5827c");
					// // For Wiki
					// caps.setCapability("app", "bs://c700ce60cf13ae8ed97705a55b8e022f13c5827c");;
					// AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new
					// URL("https://"+userName+":"+accessKey+"@hub-cloud.browserstack.com/wd/hub"),
					// caps);
					// caps.setCapability("app", "bs://e8042c4717358f9d1e7dc1bd3193be9c191af461");
					GlobalUtil.mdriver = new AndroidDriver<MobileElement>(
							new URL("https://" + userName + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub"),
							caps);
//GlobalUtil.mdriver  = new AndroidDriver<MobileElement>(new URL("http://hub.browserstack.com/wd/hub"), caps);

					// GlobalUtil.mdriver = new AndroidDriver<MobileElement>(new
					// URL("https://"+userName+":"+accessKey+"@hub-cloud.browserstack.com/wd/hub"),
					// caps);
					GlobalUtil.mdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

					// click_on_SKIP_SIGN_IN_button();
					AmazonAppFunctions.skipSignIn(ODevice_Name, NDevice_Name);
					/*
					 * AmazonAppFunctions.clickShopByCategory(ODevice_Name , NDevice_Name);
					 * AmazonAppFunctions.clickFirstCategoryExpandButton(ODevice_Name ,
					 * NDevice_Name); AmazonAppFunctions.selectProduct(ODevice_Name , NDevice_Name);
					 * AmazonAppFunctions.addProductToCart(ODevice_Name , NDevice_Name);
					 * AmazonAppFunctions.verifyItemAddedToCart(ODevice_Name , NDevice_Name);
					 */

					// Negative TestCase Condition
					// AmazonAppFunctions.loginToAmazonApp(username,ODevice_Name, NDevice_Name);
					NDevice_Name = ODevice_Name;
					// GlobalUtil.mdriver.quit();
				}
			}

			return GlobalUtil.mdriver;
		}

		else {

			try {
				/*
				 * capabilities.setCapability(MobileCapabilityType.BROWSER_NAME,"");
				 * capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
				 * GlobalUtil.getCommonSettings().getAndroidName()); //
				 * capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
				 * GlobalUtil.getCommonSettings().getAndroidVersion());
				 * capabilities.setCapability("platformName",MobileKeywords.GetValue(
				 * "platformName")); //capabilities.setCapability("automationName",
				 * "UiAutomator2"); //
				 * capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
				 * MobileKeywords.GetValue("automationName"));
				 * //capabilities.setCapability(MobileCapabilityType.UDID,
				 * GlobalUtil.getCommonSettings().getAndroidID());
				 * //capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,
				 * MobileKeywords.GetValue("newCommandTimeout")); //File app = new
				 * File(MobileKeywords.GetValue("apkFilePath"));
				 * //capabilities.setCapability(MobileCapabilityType.APP, app);
				 * capabilities.setCapability("appPackage", "in.amazon.mShop.android.shopping");
				 * capabilities.setCapability("appActivity",
				 * "com.amazon.mShop.home.HomeActivity");
				 */
				capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
						GlobalUtil.getCommonSettings().getAndroidName());
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
						GlobalUtil.getCommonSettings().getAndroidVersion());
				capabilities.setCapability("platformName", MobileKeywords.GetValue("platformName"));
				capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
						MobileKeywords.GetValue("automationName"));
				capabilities.setCapability(MobileCapabilityType.UDID, GlobalUtil.getCommonSettings().getAndroidID());
				capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,
						MobileKeywords.GetValue("newCommandTimeout"));
				// File app = new File(MobileKeywords.GetValue("apkFilePath"));
//				capabilities.setCapability(MobileCapabilityType.APP, app);
				capabilities.setCapability("appPackage", "in.amazon.mShop.android.shopping");
				capabilities.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");
				// capabilities.setCapability("appPackage", "com.reddoorz.app");
				// capabilities.setCapability("appActivity",
				// "com.reddoorz.app.ui.activity.MainActivity");

				GlobalUtil.mdriver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"),
						capabilities);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} // else close
		return GlobalUtil.mdriver;

	}

	public static AndroidDriver<MobileElement> invokeLocalMobileApp(String exeEnv, String deviceDetails) {

		String deviceName = deviceDetails.split("_")[0];
		String osVersion = deviceDetails.split("_")[1];

		System.out.println(deviceName);
		System.out.println(osVersion);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, osVersion);
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobileKeywords.GetValue("platformName"));
		// capabilities.setCapability("app", "E:\\Apkz\\AmazonAPK\\Amazon.apk");
		capabilities.setCapability("appPackage", "in.amazon.mShop.android.shopping");
		capabilities.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");
		// capabilities.setCapability("appPackage",
		// "com.amazon.mShop.android.shopping");
		// capabilities.setCapability("appActivity",
		// "com.amazon.mShop.home.web.MShopWebGatewayActivity");
		try {
			GlobalUtil.mdriver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			System.err.println("");
			e.printStackTrace();
		}
		GlobalUtil.mdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		RunCukesTest.logger.log(LogStatus.INFO,
				"<font color=blue>Execution Done By The Device:" + deviceDetails + "</font>");
		return GlobalUtil.mdriver;
	}

	public static AndroidDriver<MobileElement> invokeBrowserStackMobileApp(String deviceDetails) {
		String deviceName = deviceDetails.split("_")[0];
		String osVersion = deviceDetails.split("_")[1];
		System.out.println(deviceName);
		System.out.println(osVersion);

		DesiredCapabilities capss = new DesiredCapabilities();

		capss.setCapability("browserstack.user", "praveenkumar168");
		capss.setCapability("browserstack.key", "TBHicaS3wbThrurzWqPd");
		capss.setCapability("device", deviceName);
		capss.setCapability("os_version", osVersion);
		capss.setCapability("autoDismissAlerts", true);
		capss.setCapability("name", "Bstack-[Java]-Mobile Amazon Test");
		// capss.setCapability("appPackage", "in.amazon.mShop.android.shopping");
		// capss.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");
		// capss.setCapability("app", "bs://bcc21face50ffb87e0e5296f66fba2f8451f8e9a");
		// capss.setCapability("app", "bs://11bfb84e928ab7a58cd1dcbbfeabb79d0dd86ac3");
		// capss.setCapability("app", "bs://f89babba25b450dbd5f3fe3baf7ab3ee1999110e");
		// capss.setCapability("app", "bs://e857f33bef324d967107d9834c6a1547e4dcf13a");
		// capss.setCapability("app", "bs://a659080fa0b4ea38c4a1c7d07a9bdbab609f372c");
		// capss.setCapability("app", "bs://cde73770a410a14f0db359bf321bf784bc7cec90")
		//capss.setCapability("app", "bs://cde73770a410a14f0db359bf321bf784bc7cec90");
		capss.setCapability("app", "bs://3148793988299827312676196c1e90f1c7b62a43");
		try {
			// GlobalUtil.mdriver = new AndroidDriver<MobileElement> (new URL("https://" +
			// userName + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub"), capss);
			GlobalUtil.mdriver = new AndroidDriver<MobileElement>(new URL("http://hub.browserstack.com/wd/hub"), capss);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		GlobalUtil.mdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// click_on_SKIP_SIGN_IN_button();
		/*
		 * AmazonAppFunctions.skipSignIn(ODevice_Name, NDevice_Name);
		 * AmazonAppFunctions.clickShopByCategory(ODevice_Name , NDevice_Name);
		 * AmazonAppFunctions.clickFirstCategoryExpandButton(ODevice_Name ,
		 * NDevice_Name); AmazonAppFunctions.selectProduct(ODevice_Name , NDevice_Name);
		 * AmazonAppFunctions.addProductToCart(ODevice_Name , NDevice_Name);
		 * AmazonAppFunctions.verifyItemAddedToCart(ODevice_Name , NDevice_Name);
		 */

		// Negative TestCase Condition
		// AmazonAppFunctions.loginToAmazonApp(username,ODevice_Name, NDevice_Name);
		// NDevice_Name = ODevice_Name;
		// GlobalUtil.mdriver.quit();
		return GlobalUtil.mdriver;
	}

	/**
	 * @param browserName
	 * @return
	 */
	public static WebDriver getBrowser(String exeEnv, String browserName) { 
		
		WebDriver browser = null;
		try{
			DesiredCapabilities caps;
			String URL = null; 

			if(exeEnv.equalsIgnoreCase(REMOTE)){

				if (browserName.equalsIgnoreCase(CHROME))
				{
					caps = DesiredCapabilities.chrome();
					caps.setCapability("os","WINDOWS");
					if(GlobalUtil.getCommonSettings().getRemoteOS().toUpperCase().contains("WINDOWS")){
						caps.setCapability("os_version",GlobalUtil.getCommonSettings().getRemoteOS().split("_")[1]);;
					}
					if(GlobalUtil.getCommonSettings().getRemoteOS().toUpperCase().contains("MAC")){

						caps.setCapability("os","OS X");
						caps.setCapability("os_version",GlobalUtil.getCommonSettings().getRemoteOS().split("_")[1]);
					}
				}
				else if(browserName.equalsIgnoreCase(IE))
				{
					caps = DesiredCapabilities.internetExplorer();
					caps.setCapability("os","WINDOWS");
					if(GlobalUtil.getCommonSettings().getRemoteOS().toUpperCase().contains("WINDOWS")){
						caps.setCapability("os_version",GlobalUtil.getCommonSettings().getRemoteOS().split("_")[1]);;
					}
				}
				else if(browserName.equalsIgnoreCase(EDGE))
				{
					caps = DesiredCapabilities.edge();
					caps.setCapability("os","WINDOWS");
					caps.setCapability("version","14.0");
					caps.setCapability("os_version","10");;
				}

				else if(browserName.equalsIgnoreCase("Safari"))
				{
					caps = DesiredCapabilities.safari();
					if(GlobalUtil.getCommonSettings().getRemoteOS().toUpperCase().contains("WINDOWS")){
						caps.setCapability("os","OS X");
						caps.setCapability("os_version","SIERRA");
					}

					else{
						caps.setCapability("os","OS X");
						caps.setCapability("os_version",GlobalUtil.getCommonSettings().getRemoteOS().split("_")[1]);
					}
				}
				else{
					caps = DesiredCapabilities.firefox();
					caps.setCapability("os","WINDOWS");
					if(GlobalUtil.getCommonSettings().getRemoteOS().toUpperCase().contains("WINDOWS")){
						caps.setCapability("os_version",GlobalUtil.getCommonSettings().getRemoteOS().split("_")[1]);;
					}
					if(GlobalUtil.getCommonSettings().getRemoteOS().toUpperCase().contains("MAC")){
						caps.setCapability("os","OS X");
						caps.setCapability("os_version",GlobalUtil.getCommonSettings().getRemoteOS().split("_")[1]);
					}
				}

				if(GlobalUtil.getCommonSettings().getCloudProvider().equalsIgnoreCase("BrowserStack")){
					caps.setCapability("browserstack.debug", "true");
					caps.setCapability("build", GlobalUtil.getCommonSettings().getBuildNumber());
					URL = "https://" + GlobalUtil.getCommonSettings().getHostName() + ":" + GlobalUtil.getCommonSettings().getKey() + "@hub-cloud.browserstack.com/wd/hub";
				}
				try{
					browser  = new RemoteWebDriver(new URL(URL), caps);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				drivers.put(browserName, browser);


			}
			else{

				if (browserName.equalsIgnoreCase(CHROME)) {
					// Write code for chrome
					browser = drivers.get(browserName);
					if (browser == null) {
						//File chromeExecutable = new File(ConfigReader.getValue("ChromeDriverPath"));
						//System.setProperty("webdriver.chrome.driver", chromeExecutable.getAbsolutePath());
					   // WebDriverManager.chromedriver().driverVersion("114.0.5735.90").setup();
						WebDriverManager.chromedriver().setup();
					    browser = new ChromeDriver();
				 	} // End if
				} else if (browserName.equalsIgnoreCase(IE)) {
					// Write code for IE
					browser = drivers.get(browserName);
					if (browser == null) {
						WebDriverManager.iedriver().setup();
					    browser = new InternetExplorerDriver();
						/*
						 * File ieExecutable = new File(ConfigReader.getValue("IEDriverPath"));
						 * System.setProperty("webdriver.ie.driver", ieExecutable.getAbsolutePath());
						 * DesiredCapabilities capabilitiesIE = DesiredCapabilities.internetExplorer();
						 * capabilitiesIE.setCapability("ie.ensureCleanSession", true);
						 * capabilitiesIE.setCapability(InternetExplorerDriver.
						 * ENABLE_ELEMENT_CACHE_CLEANUP, true); browser = new
						 * InternetExplorerDriver(capabilitiesIE); drivers.put("IE", browser);
						 * checkLogin.put(browserName, "Y");
						 */
					}
				} 

				else {
					// Getting Firefox Browser
					browser = drivers.get("Firefox");
					if (browser == null) {
						
						WebDriverManager.firefoxdriver().setup();
					    browser = new FirefoxDriver();
					    
						/*
						 * File geckoExecutable = new File(ConfigReader.getValue("GeckoDriverPath"));
						 * System.out.println(geckoExecutable.getAbsolutePath());
						 * System.setProperty("webdriver.gecko.driver",geckoExecutable.getAbsolutePath()
						 * ); browser = new FirefoxDriver(); drivers.put("Firefox", browser)
						 */;
					}
				}
			}
			
			browser.manage().window().maximize();
			LogUtil.infoLog("TestStarted",GlobalUtil.getCommonSettings().getBrowser()+" : Browser Launched and " + "" +"Maximized.");
		}
		catch (Exception e) {
			LogUtil.errorLog(DriverUtil.class, "Browser not launched. Please check the configuration ", e);
			e.printStackTrace();
		}
		return browser;
	}

	/**
	 * will get browser type and version
	 * 
	 * @param browser
	 * @param cap
	 * @return
	 */
	public static String getBrowserAndVersion(WebDriver browser, DesiredCapabilities cap) { 
		String browserversion;
		String windows = "Windows";
		String browsername = cap.getBrowserName();
		// This block to find out IE Version number
		if ("IE".equalsIgnoreCase(browsername)) {
			String uAgent = (String) ((JavascriptExecutor) browser).executeScript("return navigator.userAgent;");
			LogUtil.infoLog(DriverUtil.class, uAgent);
			// uAgent return as "MSIE 8.0 Windows" for IE8
			if (uAgent.contains("MSIE") && uAgent.contains(windows)) {
				browserversion = uAgent.substring(uAgent.indexOf("MSIE") + 5, uAgent.indexOf(windows) - 2);
			} else if (uAgent.contains("Trident/7.0")) {
				browserversion = "11.0";
			} else {
				browserversion = "0.0";
			}
		} else {
			
			browserversion = cap.getVersion();
		}
		String browserversion1 = browserversion.substring(0, browserversion.indexOf('.'));
		return browsername + " " + browserversion1;
	}

	/**
	 * will get operating system information
	 * 
	 * @return
	 */

	/**
	 * close all browsersw
	 * 
	 * @return
	 */
	public static void closeAllDriver() {

		drivers.entrySet().forEach(key -> {
			key.getValue().quit();
			key.setValue(null);
		});

		LogUtil.infoLog(DriverUtil.class, "Closing Browsers");
	}

	public static String getImgRef(String imgFile) {
		return new DriverUtil().getRefImage(imgFile);
	}

	private String getRefImage(String imgFile) {
		String openCVImgsFolder = "OpenCVImages/";
		URL refImgUrl = getClass().getClassLoader().getResource(openCVImgsFolder + imgFile + ".png");
		File refImgFile;
		try {
			refImgFile = Paths.get(refImgUrl.toURI()).toFile();
			System.out.println("File Found : " + refImgFile.exists());
			return Base64.getEncoder().encodeToString(Files.readAllBytes(refImgFile.toPath()));
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
			return "";
		}
	}
}// End class
