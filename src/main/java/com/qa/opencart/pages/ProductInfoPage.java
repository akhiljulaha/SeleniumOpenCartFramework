package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.plaf.metal.MetalTextFieldUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	// 1. Private By Locators
	private By productHeader = By.tagName("h1");
	private By images = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	
	private Map<String, String> productInfoMap = new HashMap<String, String>();

	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}
	
	public String getProductHeaderName() {
		String header = eleUtil.doGetElemntText(productHeader);
		System.out.println(header);
		return header;
	}
	
	public int getProductImagesCount() {
		return eleUtil.waitForElementsVisible(images, AppConstants.MEDIUM_DEFAULT_WAIT).size();
	}
	
	public Map<String, String> getProductInfo() {
		productInfoMap = new HashMap<String, String>();   // It will not maintain the order 
//  	productInfoMap = new LinkedHashMap<String, String>();  // it will maintain the order
//		productInfoMap = new TreeMap<String, String>();  // it will give the data in the sorted form 
		getProductMetaData();
		getProductPriceData();
		productInfoMap.put("productname", getProductHeaderName());   //if there is no key then you can pass your own key 
		return productInfoMap; //here we have a price data ,meta data amd product name 
	}
	
//	Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: In Stock
// below 2 methods we are putting inside the 'getProductInfo' method and they not returning anything so we set as private methods, no need to expose
	private void getProductMetaData() {
		List<WebElement> metaList = eleUtil.getElements(productMetaData);
		for (WebElement e : metaList) {
			String metaText = e.getText();
			String metaInfo[] = metaText.split(":");
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
			productInfoMap.put(key, value);
		}
	}
	
//	$2,000.00
//	Ex Tax: $2,000.00
	private void getProductPriceData() {
		List<WebElement> priceList = eleUtil.getElements(productPriceData);
		String price = priceList.get(0).getText();    // list is order base so we can use index
		String exTaxPrice = priceList.get(1).getText(); 
		String exTaxPriceValue = exTaxPrice.split(":")[1].trim(); //$2,000.00
		productInfoMap.put("productprice", price);   // here we are giving over own key 
		productInfoMap.put("extaxprice", exTaxPriceValue);
	}

	
}
