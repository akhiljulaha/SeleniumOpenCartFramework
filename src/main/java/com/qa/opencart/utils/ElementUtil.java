package com.qa.opencart.utils; 

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.frameworkexception.FrameException;

public class ElementUtil {

	private WebDriver driver; 
	private JavaScriptUtil jsUtil;
	private final int DEFAULT_TIME_OUT = 5;

	public ElementUtil(WebDriver driver) { 
		this.driver = driver; 
		jsUtil = new JavaScriptUtil(this.driver);
	}

	public void doSendKeys(By locator, String value) {
		if (value == null) {
			System.out.println("null value are not alowed");
			throw new FrameException("VALUECANNOTNULL");
		}
		doClear(locator);
		getElement(locator).sendKeys(value);
	}

	public void doClick(By locator) {             // overload
		getElement(locator).click();
	}
	
	public void doClick(By locator, int timeOut) {
		checkElementClickable(locator, timeOut).click();
	}

//public WebElement getElement(By locator) {                
//	return driver.findElement(locator);        
//}
	
	public WebElement getElement(By locator, int timeOut) {
		WebElement element = waitForElementVisble(locator, timeOut);
		if(Boolean.parseBoolean(DriverFactory.highlightElement)) {    
			jsUtil.flash(element);
		}
		return element;
	}
	/*
	 * why we overloading the getElement???
	 * Upper method will use if you want to pass your own timeOut
	 * below method will use if you don't want to pass any timeOut, here we are handing if element will be avialable 
	 * after 5 sec, if element is not found for the first time then only we will wait for the next 5 sec  
	 *  Best Example for method overloading
	 *  In the below method if ele is not find then default wait will apply
	 */
	
	
	public WebElement getElement(By locator) {
		WebElement element = null;
		try {
			element = driver.findElement(locator);
			System.out.println("Element is found : " + locator);
		} catch (NoSuchElementException e) { // import selenium not java
			System.out.println("Element is not found using this locator : " + locator);
			element = waitForElementVisble(locator, DEFAULT_TIME_OUT);     //  deafult time is always 5 sec, set as final variable 
		}
		// before returning we will heiglight
		
		if(Boolean.parseBoolean(DriverFactory.highlightElement)) {    // converting to boolean			// we made a static highlightElement variable	
			jsUtil.flash(element);
		}
		
		return element;
	}

	public void doClear(By locator) {
		getElement(locator).clear();
	}

	public String doGetElemntText(By locator) {
		return getElement(locator).getText();
	}

	public boolean CheckElementIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public String doGetAttributeValue(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	public int getElementCount(By locator) {
		return getElements(locator).size();
	}

	// giving List of Attribute value on the basis of attribute name you supply
	public List<String> getElementAttributeValue(By locator, String attrName) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleAttrList = new ArrayList<String>();
		for (WebElement e : eleList) {
			String attrValue = e.getAttribute(attrName);
//			System.out.println(attrValue);
			eleAttrList.add(attrValue);
		}
		return eleAttrList;
	}

	// giving list of text (GetElementSection)
	public List<String> getElementsTextList(By locator) {
		List<WebElement> elementsLinksList = getElements(locator);
		List<String> elesTextList = new ArrayList<String>();
		for (WebElement e : elementsLinksList) {
			String text = e.getText();
			elesTextList.add(text);
		}
		return elesTextList;
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}
// This method is used when you want to perform click but for specific section (GetElementSection)
	public void clickElementFromPageSection(By locator, String eleText) {
		List<WebElement> eleList = getElements(locator);
		for (WebElement e : eleList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(eleText)) {
				e.click();
				break;
			}
		}
	}

	public void search(String searchKey, By searchLocator, String suggName, By suggestions)
			throws InterruptedException {
//		driver.findElement(searchLocator).sendKeys(searchKey);
		doSendKeys(searchLocator, searchKey);
		Thread.sleep(3000);

//		List<WebElement> suggList = driver.findElements(suggestions); 
		List<WebElement> suggList = getElements(suggestions);

		System.out.println("Total Suggestion : " + suggList.size());
		if (suggList.size() > 0) {
			for (WebElement e : suggList) {
				String text = e.getText();
				if (text.length() > 0) { // It will help to avoid blank text
					System.out.println(text);
					if (text.contains(suggName)) {
						e.click();
						break;
					}
				} else {
					System.out.println("blank values-----No suggestions");
					break;
				}
			}
		} else {
			System.out.println("No Search Suggestion Found");
		}
	}

	// this method will check locator is available or not
	public boolean IsElementDisplayed(By locator) {
		List<WebElement> eleList = getElements(locator);
		if (eleList.size() > 0) {
			System.out.println(locator + "element is present on the page ");
			return true;
		} else {
			return false;
		}
	}

	// *************************** Drop Down Utils ***************************
	public void doSelectDropDownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectDropDownByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}

	public void doSelectDropDownByValueAttribute(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}
/////////////  Drop-Down Without Select Methods  //////////// 
	public int getDropDownValueCount(By locator) {
		return getAllDropDownOptions(locator).size();
	}

	public List<String> getAllDropDownOptions(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		List<String> optionsValueList = new ArrayList<String>();
		System.out.println("Total country : " + optionsList.size());
		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			optionsValueList.add(text);
		}
		return optionsValueList;
	}

	public boolean doSelectDropDownvalue(By locator, String dropDownValue) { // help to found exact value
		boolean flag = false;
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		System.out.println("Total values : " + optionsList.size());
		
		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.equals(dropDownValue)) {
				flag = true;
				e.click();
				break;
			} else {  // if yur write else or not it does not matter
			} // If you write else, every time it will print if value not found ,
		}
		if(flag==false) {
			System.out.println(dropDownValue + " is not present in the Drop Down "+ locator);
		}
		return flag;
	}
//////////////  Do Select Value From Drop-Down Without Select    ///////////
	public boolean DoSelectValueFromDropDownWithoutSelect(By locator, String value) {
		boolean flag = false;
//		List<WebElement> optionsList = driver.findElements(locator);
		List<WebElement> optionsList = getElements(locator);
		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.equals(value)) {
				flag = true;
				e.click();
			}
		}
		if (flag == false) {
			System.out.println(value + " is not present in the drop down " + locator);
		}
		return flag;
	}

	// ************************ Actions class Utils ***********************************
	
	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}
	
		public void doActionsClick(By locator) {          // overload
			Actions act = new Actions(driver);
			act.click(getElement(locator)).build().perform();
	}
		
		public void doActionsClick(By locator, int timeOut) {
			Actions act = new Actions(driver);
			act.click(checkElementClickable(locator, timeOut)).build().perform();;
	}
	
	public void doDragAndDrop(By sourceLocator, By targetLocator) {
		Actions act = new Actions(driver);
		act.dragAndDrop(getElement(sourceLocator), getElement(targetLocator)).build().perform();
	}
	
	public void doContextClick(By locator) {
		Actions act = new Actions(driver);
		act.contextClick(getElement(locator)).build().perform();
	}
	
	public void doMoveToElement(By locator) {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(locator)).build().perform();
	}
	
	

	public void handleTwoLevelMenu(By parentMenu, By childMenu) throws InterruptedException { // not passing value,// passing both locator

//		WebElement ParentMenuElement = getElement(parentMenu);
//		Actions act = new Actions(driver);
//		act.moveToElement(ParentMenuElement).build().perform();
		doMoveToElement(parentMenu);
		Thread.sleep(2000);
//		driver.findElement(childMenu).click();
		doClick(childMenu);
	}

	public void handleTwoLevelMenu(By parentMenu, String childMenuLinkText) throws InterruptedException { // here we have to pass value
																																																		
//		WebElement parentMenuElement = getElement(parentMenu);
//		Actions act = new Actions(driver);
//		act.moveToElement(parentMenuElement).build().perform();
		doMoveToElement(parentMenu);
		Thread.sleep(2000);
//		driver.findElement(By.linkText(childMenuLinkText)).click();
		doClick(By.linkText(childMenuLinkText));
	}

///////// It will work where 4 menu items are there //////////
	public void multiLevelMenuChildMenuHandle(By parentMenuLocator, String level2LinkText, String level3LinkText,
			String level4LinkText) throws InterruptedException {
		WebElement level1 = getElement(parentMenuLocator);

		Actions act = new Actions(driver);
		act.moveToElement(level1).build().perform();
		Thread.sleep(2000);
		
//		WebElement level2 = driver.findElement(By.linkText(level2LinkText));
		WebElement level2 = getElement(By.linkText(level2LinkText));
		act.moveToElement(level2).build().perform();
		Thread.sleep(2000);

//		WebElement level3 = driver.findElement(By.linkText(level3LinkText));
		WebElement level3 = getElement(By.linkText(level3LinkText));
		act.moveToElement(level3).build().perform();
		Thread.sleep(2000);

//		driver.findElement(By.linkText(level4LinkText)).click();
		doClick(By.linkText(level4LinkText));
	}
	
	// ************************* Wait Utils **********************************
	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 *	This does not necessarily mean that the element is visible on the page
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(timeOut));    // No need to create seperate method for this 
		return wait1.until(ExpectedConditions.presenceOfElementLocated(locator));       //It will check the ele iside the DOM only
	}
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible on the page.
	 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
	 * Default timeout = 500 ms
	 * @param locator
	 * @param timeOut
	 * @return 
	 */
	public WebElement waitForElementVisble(By locator, int timeOut) {                      //It will check the ele inside the DOM and visible on the page
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		 WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		 if(Boolean.parseBoolean(DriverFactory.highlightElement)) {    
				jsUtil.flash(element);
			}
		return element;
	}
	
	// default timeout = intervalTime(We can pass our own intervalTime)
	public WebElement waitForElementVisble(By locator, int timeOut, int intervalTime) {       // overload             
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(intervalTime));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	// default timeout = intervalTime 
	/**
	 * An expectation for checking that all elements present on the web page that match the locator are visible.
    Visibility means that the elements are not only displayed but also have a heightand width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 * In the footer if the any of the element is not visisble then it will give the exception
	 */
	
	public  List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	
	/**   // better than visibilityOfAllElementsLocatedBy
	 * An expectation for checking that there is at least one element present on a web page. it will not check the DOM
	 * if 1 ele is present on the page then it will give complete list of the footer
	 * Ex>Out of 35 footer if any of the ele is visible on the page and then it will give the complete list of the footer
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsPresense(By locator, int timeOut) {         // It will not check the DOM      
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeOut
	 */
	public void clickElementWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
       wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * it will check element is clickable or not 
	 * @param locator
	 * @param timeOut
	 */
	public WebElement checkElementClickable(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	    return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
// ***************************** wait JS Alerts Util *******************************
	
	public Alert waitForAlertjsPopUpWithFluentWait(int timeOut, int pollingTime) {    // adding fluent wait in the JS Pop-up
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)                  
                .withTimeout(Duration.ofSeconds(timeOut))           
                .ignoring(NoAlertPresentException.class)         
                .pollingEvery(Duration.ofSeconds(pollingTime))        
                .withMessage("--------Time out is done...alert is not found .... ");  
		
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	
	public Alert waitForAlertJsPopUp(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());		
		}
	
	public String alertJSGetText(int timeOut) {
		return waitForAlertJsPopUp(timeOut).getText();
	}
	
	public void alertAccept(int timeOut) {
		 waitForAlertJsPopUp(timeOut).accept();
	}
	
	public void alertDismiss(int timeOut) {
		 waitForAlertJsPopUp(timeOut).dismiss();
	}
	
	public void EnterAlertValue(int timeOut, String value) {
		 waitForAlertJsPopUp(timeOut).sendKeys(value);
	}
///////////////////// Below 2 for Capturing the title //////////////////
	public String waitForTitleIsAndCapture(String titleFraction, int timeOut) {    // work like contains
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if(wait.until(ExpectedConditions.titleContains(titleFraction))) {           
			String title = driver.getTitle();
			return title;
		}
		else {
			System.out.println("Title is not present within the given timeout  : "+timeOut);
			return null;           
		}
	}
	
	
	public String waitForFullTitleAndCapture(String titleVal, int timeOut) {        // here you have to write complete title 
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if(wait.until(ExpectedConditions.titleIs(titleVal))) {           
			String title = driver.getTitle();
			return title;
		}
		else {
			System.out.println("Title is not present within the given timeout  : "+timeOut);
			return null;           
		}
	}
	
	public String waitForURLContainsAndCapture(String urlFraction, int timeOut) {        // here no need to pass complete URL 
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));            //4
		if(wait.until(ExpectedConditions.urlContains(urlFraction))) {           
			String url = driver.getCurrentUrl();
			return url;
		}
		else {
			System.out.println("URL is not present within the given timeout  : "+timeOut);
			return null;           
		}
	}
	
	public String waitForURLAndCapture(String urlValue, int timeOut) {        // here you have to pass complete URL
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));          //5
		if(wait.until(ExpectedConditions.urlToBe(urlValue))) {           
			String url = driver.getCurrentUrl();
			return url;
		}
		else {
			System.out.println("URL is not present within the given timeout  : "+timeOut);
			return null;           
		}
	}
	
	public Boolean waitForTotalWindows(int totalWindowsToBe, int timeOut) {	         // Use this method when you have to check multiple windows(if you not sure about windows count then dont't used this utility	)
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));             
		return wait.until(ExpectedConditions.numberOfWindowsToBe(totalWindowsToBe));
	}
	
	//  //////////////////////    Frames with wait   ////////////
	
	public void waitForFrameAndWitchToItWithFluentWait(int timeOut, int pollingTime, String idOrName) {  
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)                  
                .withTimeout(Duration.ofSeconds(timeOut))           
                .ignoring(NoSuchFrameException.class)         
                .pollingEvery(Duration.ofSeconds(pollingTime))        
                .withMessage("--------Time out is done...frame is not found .... ");  
		
		 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
	}
	
	public void waitForFrameAndSwitchToItByIDOrName(int timeOut, String idOrName) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName)); 
	}
	
	public void waitForFrameAndSwitchToItByIndex(int timeOut, int frameIndex) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex)); 
	}
	
	public void waitForFrameAndSwitchToItByFrameElement(int timeOut, WebElement frameElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement)); 
	}
	
	public void waitForFrameAndSwitchToItByFrameLocator(int timeOut, By frameLocator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator)); 
	}
	
	/////// fluent wait 
	public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)                  //4
                .withTimeout(Duration.ofSeconds(timeOut))           
                .ignoring(NoSuchElementException.class)         
                .ignoring(ElementNotInteractableException.class)
                .pollingEvery(Duration.ofSeconds(pollingTime))        
                .withMessage("--------Time out is done...element is not found .... ");  
		
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public WebElement waitForElementPresenceWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)             
                .withTimeout(Duration.ofSeconds(timeOut))           
                .ignoring(NoSuchElementException.class)         
                .ignoring(ElementNotInteractableException.class)
                .pollingEvery(Duration.ofSeconds(pollingTime))        
                .withMessage("--------Time out is done...element is not found .... ");  
		
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	// Custom Wait without using Selenium
	public WebElement retryingElement(By locator, int timeOut) {
		WebElement element = null;
		int attempts = 0;
		
		while(attempts < timeOut) {
			try {
			element = getElement(locator);
			System.out.println("element is found....."+ locator + " in attempt : " + attempts);
			break;
			}catch (NoSuchElementException e) {
				System.out.println("element is not found...."+ locator + "in attempt : "+ attempts);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			attempts++;
		}
		
		if(element == null) {
			System.out.println("element is not found...tried for " + timeOut + " secs " + "with the interval of 500 milliseconds");
		}
		return element;
	}
	
	public WebElement retryingElement(By locator, int timeOut, int pollingTime) {
		WebElement element = null;
		int attempts = 0;
		
		while(attempts < timeOut) {
			try {
			element = getElement(locator);
			System.out.println("element is found....."+ locator + "in attempt : "+ attempts);
			break;
			} catch (NoSuchElementException e) {
				System.out.println("element is not found...."+ locator + "in attempt : "+ attempts);
				try {
					Thread.sleep(pollingTime);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			attempts++;
		}
		
		if(element == null) {
			System.out.println("element is not found...tried for " + timeOut + " secs " + "with the interval of 500 milliseconds");
		}
		return element;
	}
	////////// Below 2 methods will check page is fully loaded or not(along with images and frames)   //////////////
	public boolean isPageLoaded() {                                  //2  custom wait with the help of Eclipicity
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == \'complete\'"))
				.toString();       
		return Boolean.parseBoolean(flag);   //
	}
	
	
	
	public void waitForPageLoad(int timeOut) {                  //1  custom wait with the help of JS
		
		long endTime = System.currentTimeMillis() + timeOut;
		while (System.currentTimeMillis() < endTime) {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			String pageState = jsExecutor.executeScript("return document.readyState").toString();
			if (pageState.equals("complete")) {
				System.out.println("PAGE DOM is fully loaded now....");
				break;
			}
			else {
				System.out.println("PAGE IS not loaded....."+ pageState);
			}
		}
	}

	
	
}

// should not static in the utility class