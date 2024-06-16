package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.dataproviders.ProductDataProvider;

public class SearchTest extends BaseTest {

	@BeforeClass
	public void serachSetup() { // loginPage ref. coming from the parent class(baseTest)
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
// **************	
//	@DataProvider                             // Ex > C1 ,R5
//	public Object[][] getProductSearchKeyData() {               // No need to maintain Excel sheet for this 
//		return new Object[][] {
//			
//			{"Macbook"},                  // 3R 1C
//			{"iMac"},           
//			{"Samsung"}         
//		};
//	}
//************
	@Test(dataProvider = "productDataWithSearchKey", dataProviderClass = ProductDataProvider.class)  // dataProvider= getProductSearchKeyData
	public void searchProductResultscountTest(String serachKey) { // need to maintain a 1 parameter
		resultsPage = accPage.doSerach(serachKey); // result page ref. coming from the parent class/basetest
		Assert.assertTrue(resultsPage.getProductResultCount() > 0);
	}

	@Test(dataProvider = "productDataWithSearchKey", dataProviderClass = ProductDataProvider.class)
	public void searchPageTitleTest(String serachKey) {
		resultsPage = accPage.doSerach(serachKey);
		String actSearchTitle = resultsPage.getResultPageTitle(serachKey);
		System.out.println("Search Page Title : " + actSearchTitle);
		Assert.assertEquals(actSearchTitle, "Search - " + serachKey);
	}

	@Test(dataProvider = "productDataWithName", dataProviderClass = ProductDataProvider.class)
	public void selectProductTest(String serachKey, String productName) {
		resultsPage = accPage.doSerach(serachKey);
		productInfoPage = resultsPage.selectProduct(productName); // productInfoPage ref. coming from the parent class
																	// base class
		String actproductHeaderName = productInfoPage.getProductHeaderName();
		System.out.println("Actual Product Name : " + actproductHeaderName);
		Assert.assertEquals(actproductHeaderName, productName);
	}

	@Test(dataProvider = "productDataWithImage", dataProviderClass = ProductDataProvider.class)
	public void ProductImagesTest(String searchKey, String productName, int expImagesCount) {
		resultsPage = accPage.doSerach(searchKey);
		productInfoPage = resultsPage.selectProduct(productName);
		int actProductImagesCount = productInfoPage.getProductImagesCount();
		System.out.println("Actual Product Images Count : " + actProductImagesCount);
		Assert.assertEquals(actProductImagesCount, expImagesCount);
	}

}
