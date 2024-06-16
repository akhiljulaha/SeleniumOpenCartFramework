package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductPageInfoTest extends BaseTest{

	@BeforeClass
	public void accPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));  // prop ref. coming from the baseTest class
	}
	
	@Test
	public void productInfoTest() {
		resultsPage = accPage.doSerach("Macbook");
		productInfoPage = resultsPage.selectProduct("MacBook Pro");
		Map<String, String> productInfoMap = productInfoPage.getProductInfo();   // in the map info order will be random, not sequance or fixed way
		System.out.println(productInfoMap);
//{Brand=Apple, Availability=In Stock, extaxprice=$2,000.00, Product Code=Product 18, productname=MacBook Pro, Reward Points=800, productprice=$2,000.00} --HashMap
//{Brand=Apple, Product Code=Product 18, Reward Points=800, Availability=In Stock, productprice=$2,000.00, extaxprice=$2,000.00, productname=MacBook Pro} -- LinkedHashMap
//{Availability=In Stock, Brand=Apple, Product Code=Product 18, Reward Points=800, extaxprice=$2,000.00, productname=MacBook Pro, productprice=$2,000.00} -- TreeMap sorted form(Capital letter >> small letter > numeric key)
		softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");	                 // hard assertion Assert.assert
		softAssert.assertEquals(productInfoMap.get("Availability"), "In Stock");	
		softAssert.assertEquals(productInfoMap.get("productname"), "MacBook Pro");	
		softAssert.assertEquals(productInfoMap.get("productprice"), "$2,000.00");	
		softAssert.assertEquals(productInfoMap.get("Product Code"), "Product 18");	
		softAssert.assertAll();
	}

/**
* In the hard assert if test will fail then it not check rest of rest and in the soft assert if one test will 
* fail then it will check the rest of tests also 
* Assert method static in nature and soft assert methods are not static in nature that's why we creating object ref. calling them
* in the soft assert is it mandatory to write in the end .>>>>>   softAssert.assertAll(); > it will tell how many test will fail 
*soft assert is a class in the testNG, so in the BaseTest we created the object and using the referance here
*/
		
	
}
