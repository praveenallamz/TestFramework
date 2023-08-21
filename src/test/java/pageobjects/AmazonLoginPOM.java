package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;

import step_definitions.RunCukesTest;
import utilities.GlobalUtil;
import utilities.HTMLReportUtil;
import utilities.KeywordUtil;
import utilities.LogUtil;

public class AmazonLoginPOM {
	
	private static final int DEFAULT_WAIT_SECONDS = 10;

	WebDriver driver;

	public AmazonLoginPOM(WebDriver driver){ 
             this.driver=driver; 
    }

//Using FindBy for locating elements
	//@FindBy(how = How.XPATH, using = "//input[@id='ap_email']")  
	@FindBy(how = How.XPATH, using = "//div[@id=\"authportal-main-section\"]//div[@class=\"a-box-inner a-padding-extra-large\"]//input[@id=\"ap_email\"]")
	WebElement emailTextBox;
	
	@FindBy(how = How.XPATH, using = "//input[@id='ap_password']")
	WebElement passwordTextBox;
	
	@FindBy(how = How.XPATH, using = "//input[@id='signInSubmit']")
	WebElement signinButton;
	
	@FindBy(how = How.XPATH, using = "//input[@id='continue']")
	WebElement continueButton;

	// Defining all the user actions (Methods) that can be performed in the Facebook
	// home page

	// This method is to set Email in the email text box
	public  void setEmail(String text, String logStep) throws InterruptedException {
		 Thread.sleep(1000);
		 // emailTextBox.sendKeys(text);
		//  Thread.sleep(1000);
		//WebElement xx= driver.findElement(By.xpath("//div[@id='authportal-main-section']//div[@class='a-box-inner a-padding-extra-large']//input[@id='ap_email']"));
		//xx.sendKeys("SampleEmail@gmail.com");
		// Thread.sleep(1500);
		 
		
		 WebDriverWait wait = new WebDriverWait(driver, 10);
	        
	        By emailInputSelector = By.xpath("//input[@id='ap_email']");

	        // Use ExpectedConditions.visibilityOfElementLocated with WebDriverWait
	        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInputSelector));
	        
	        emailInput.sendKeys("your-email@example.com");
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
	    // driver.findElement(By.cssSelector("input[id='ap_email']")).sendKeys("ExampleTestemaill@example.com");
	    // Thread.sleep(1500);
		// WebElement EmailInput = driver.findElement(By.xpath("//input[@id='ap_email']"));
		// EmailInput.sendKeys(text);
		 
		//WebDriverWait wait = new WebDriverWait(driver, 10);
		//WebElement EmailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='ap_email']")));
		 //EmailInput.sendKeys(text);
		 
		 
		 RunCukesTest.logger.log(LogStatus.PASS, HTMLReportUtil.passStringGreenColor(logStep));
	}

// This method is to set Password in the password text box
	public void setPassword(String strPassword, String string) {
		passwordTextBox.sendKeys(strPassword);
		
	}

// This method is to click on Login Button
	public void clickOnLoginButton() {
		signinButton.click();
	}
	
	// This method is to click on Login Button
		public void clickOncontinueButton( ) {
			continueButton.click();
			 //RunCukesTest.logger.log(LogStatus.PASS, HTMLReportUtil.passStringGreenColor(logStep));
		}
		
		
		public static boolean inputText(By locator, String data, String logStep) {
			KeywordUtil.lastAction = "Input Text: " + data + " - " + locator.toString();
			LogUtil.infoLog(KeywordUtil.class, KeywordUtil.lastAction);
			WebElement elm = waitForVisible(locator);
			if (elm == null) {
				return false;
			} else {
				elm.clear();
				elm.sendKeys(data);
				RunCukesTest.logger.log(LogStatus.PASS, HTMLReportUtil.passStringGreenColor(logStep));
				return true;
			}
		}
		
		public static WebElement waitForVisible(By locator) {
			try {
				WebDriverWait wait = new WebDriverWait(GlobalUtil.getDriver(), DEFAULT_WAIT_SECONDS);
				// wait.ignoring(ElementNotVisibleException.class);
				return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			} catch (Exception e) {
				return null;
			}
		} 

}
