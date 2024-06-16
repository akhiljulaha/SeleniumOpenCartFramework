package com.qa.opencart.factory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qa.opencart.frameworkexception.FrameException;

public class DriverFactory {
	WebDriver driver;
	OptionsManager optionsManager;
	public static String highlightElement;      //2
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();      //3 created threadlocal object, static because don't want multile thread copy

	public WebDriver initDriver(Properties prop) { // in this prop having all properties
		String browserName = prop.getProperty("browser").trim();
		System.out.println("browser name is : " + browserName);
		
		highlightElement = prop.getProperty("highlight"); // 2
		
		optionsManager = new OptionsManager(prop);    //1
		
		switch (browserName.toLowerCase()) {
		case "chrome":
//			driver = new ChromeDriver();
//			driver = new ChromeDriver(optionsManager.getChromeOptions());   //1
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));   //3
			break;
		case "edge":
//			driver = new EdgeDriver(optionsManager.getEdgeOptions());     //1      
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));     //3
			break;
		case "firefox":
//			driver = new FirefoxDriver(optionsManager.getFirefoxOptions());   //1
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));   //3
			break;

		default:
			System.out.println("Please pass the right browser...." + browserName);
			throw new FrameException("NOBROWSERFOUNDEXCEPTION");
		}
		getDriver().manage().deleteAllCookies();                  //3
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
//		driver.manage().deleteAllCookies();
//		driver.manage().window().maximize();
//		driver.get(prop.getProperty("url"));
//		return driver;
		
	}
	// return the thread local copy of the driver
	public synchronized static WebDriver getDriver() { //synchronized > not mandatory to write, it will grantee
	   return tlDriver.get();
	}

	public Properties initProp() {
		// mvn clean install -Denv
		Properties prop = new Properties();
		FileInputStream ip = null;

		String envName = System.getProperty("env");   //this will set the env, system will work internally, just need to pass the env throught the maven build...
		System.out.println("Running test cases on env: " + envName);
		try {
			if (envName == null) {
				System.out.println("no env is given ... hence running it on QA env....");
				ip = new FileInputStream("./src/main/resources/confiq/qa.confiq.properties");
			} else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/main/resources/confiq/qa.confiq.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/main/resources/confiq/dev.confiq.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/main/resources/confiq/stage.confiq.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/main/resources/confiq/uat.confiq.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/main/resources/confiq/confiq.properties");
					break;
				default:
					System.out.println("plz pass the right env name..." + envName);
					throw new FrameException("NOVALIDENVGIVEN");
				}
			}
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	/**
	 * take screenshot
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);

		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}

}
////////////////////////
//public Properties initProp() {
//	Properties prop = new Properties();  // design to intract with .propeties file
//	try {
//		FileInputStream ip = new FileInputStream("./src/main/resources/confiq/confiq.properties");
//	    try {
//			prop.load(ip);   // while loading the property any exception will come then 2 nd try catch will handle 
//			} catch (IOException e) {
//			e.printStackTrace();
//		}
//	} catch (FileNotFoundException e) {
//		e.printStackTrace();
//	}
//	return prop;
//}
