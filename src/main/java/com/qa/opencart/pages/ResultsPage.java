package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ElementUtil;

public class ResultsPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	private By resultProduct = By.xpath("//div[contains(@class,'product-layout product-grid col-lg-3')]");

	public ResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}
	
	
	//Page Actions
	public String getResultPageTitle(String searchKey) {
		   return eleUtil.waitForTitleIsAndCapture(searchKey, 5);
		}
	
	public int getProductResultCount() {
		int resultCount = eleUtil.waitForElementsVisible(resultProduct, 10).size();
		System.out.println("product search result count ===> "+ resultCount);
		return resultCount;
	}
	
	public ProductInfoPage selectProduct(String productName) {
		By productNameLocator = By.linkText(productName);    // this is dynamic loactor that's why we not writing above with all private locators, even if you write there is no way to pass the productName value  
		eleUtil.doClick(productNameLocator);
		return new ProductInfoPage(driver);             
	}

	
}
