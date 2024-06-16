package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1 const of the page class

	public AccountsPage(WebDriver driver) { // **-b getting driver through the LoginPage class(doLogin method)
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);

	}

	// 2 private By loactors
	private By logout = By.linkText("Logout");
	private By myAccount = By.linkText("My Account");
	private By accHeaders = By.xpath("//div[@id='content']/h2");
	private By search = By.name("search");
	private By searchIcon = By.xpath("//div[@id='search']//button");

	// Page actions
	public String getAccPageTitle() {
		return eleUtil.waitForTitleIsAndCapture(AppConstants.ACCOUNTS_PAGE_TITLE_VALUE, AppConstants.SHORT_DEFAULT_WAIT);
	}

	public boolean isLogoutLinkExist() {
		return eleUtil.CheckElementIsDisplayed(logout);
	}

	public boolean isMyAccountLinkExist() {
		return eleUtil.CheckElementIsDisplayed(myAccount);
	}

	public List<String> getAccountPageHeaderList() {
		List<WebElement> headersList = eleUtil.waitForElementsVisible(accHeaders, AppConstants.MEDIUM_DEFAULT_WAIT);
		List<String> headersValList = new ArrayList<String>();
		for (WebElement e : headersList) {
			String text = e.getText();
			headersValList.add(text);
		}
		return headersValList;
	}

	public ResultsPage doSerach(String serachTerm) {
		eleUtil.waitForElementVisble(search, AppConstants.MEDIUM_DEFAULT_WAIT);
		eleUtil.doSendKeys(search, serachTerm); // here before entering the value in the search field first need to clear
		eleUtil.doClick(searchIcon);           
		return new ResultsPage(driver); // **-c same driver giving to the Results page
	}

}
