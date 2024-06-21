package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1 const. of the page class
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}

	// 2 private By locators:
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginButton = By.xpath("//input[@value='Login']");
	private By forgotPWdLink = By.linkText("Forgotten Password");
	private By footerLinks = By.xpath("//footer//a");
	private By loginErrorMessg = By.xpath("//div[@class='alert alert-danger alert-dismissible']");
	private By registerLink = By.linkText("Register"); // this locator for register page test case

	// 3. Public Page Actions/Method -> what exactly you want to do on the specific
	// page
	@Step("getting login page title44444444444444444444444444")
	public String getLoginPageTitle() {
		return eleUtil.waitForTitleIsAndCapture(AppConstants.LOGIN_PAGE_TITLE_VALUE, AppConstants.SHORT_DEFAULT_WAIT);
	}

	@Step("getting login page url")
	public String getLoginPageURL() {
		return eleUtil.waitForURLContainsAndCapture(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE,
				AppConstants.SHORT_DEFAULT_WAIT);
	}
	
	@Step("checking forgot pwd link exist on the login page")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.CheckElementIsDisplayed(forgotPWdLink);
	}

	@Step("getting footer link")
	public List<String> getFooterLinksList() {
		List<WebElement> footerLinksList = eleUtil.waitForElementsVisible(footerLinks,
				AppConstants.MEDIUM_DEFAULT_WAIT);
		List<String> footerTextList = new ArrayList<String>();
		for (WebElement e : footerLinksList) {
			String text = e.getText();
			footerTextList.add(text);
		}
		return footerTextList;
	}

	@Step("login with username {0} and password{1}")
	public AccountsPage doLogin(String userName, String pwd) {
		System.out.println("Correct creds are : " + userName + ":" + pwd);
		eleUtil.waitForElementVisble(emailId, AppConstants.MEDIUM_DEFAULT_WAIT); // need to clear the data is imp
		eleUtil.doSendKeys(emailId, userName);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginButton);
		return new AccountsPage(driver); // **-a Here passing the driver is imp, so only we can pass this driver to the
											// account class
	}

	/**
	 * Page Chaining Model> whenever you clicking any button and it will navigating
	 * to other page, it's method responsiblity to return the next landing page
	 * class object
	 **/

	@Step("login with wrong username {0} and password{1}")
	public boolean doLoginWithWrongCredentials(String userName, String pwd) {
		System.out.println("Wrong creds are : " + userName + ":" + pwd);
		eleUtil.waitForElementVisble(emailId, AppConstants.MEDIUM_DEFAULT_WAIT);
		eleUtil.doSendKeys(emailId, userName); // need to clear the data is imp here
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginButton);
		String errorMessg = eleUtil.doGetElemntText(loginErrorMessg);
		System.out.println(errorMessg);
		if (errorMessg.contains(AppConstants.LOGIN_ERROR_MESSAGE)) {
			return true;
		}
		return false;

	}
	public RegisterPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}
}
