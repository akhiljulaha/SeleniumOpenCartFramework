package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.ResultsPage;

public class BaseTest {
	/**
	 * make it public then only child class inherit the properties of the parent OR use protected 
	 * class protected > Access by only those classes which are the child of the
	 * BaseTest, does't matter they are in the same package or outside the package
	 */
	protected WebDriver driver;
	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected DriverFactory df;
	protected Properties prop;
	protected ResultsPage resultsPage;
	protected ProductInfoPage productInfoPage;
	protected SoftAssert softAssert;
	protected RegisterPage registerPage;
	
	@Parameters({"browser"})        // 1
	@BeforeTest
	public void setup(String browserName) {    //1
//	public void setup() {
		df = new DriverFactory();
		prop = df.initProp();
		if(browserName!=null) {               //1
			prop.setProperty("browser", browserName);  // we can not see the changes in th confiq sheet, because it will change at the run time 
			
		}	
//		
		driver = df.initDriver(prop);       // if using the 3 option in the driverfactor, for returning no need tlDriver because it will return driver copy that'why storing in the driver
		softAssert = new SoftAssert(); 
		loginPage = new LoginPage(driver);
/**
 * even of you running other pages test cases except the login page in that case also need to create a object of the 
 * login class because in other pages first you have to login with do login method that coming under the login class 
 * and the same do login method will return the driver to other pages/clases	
 */

	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
