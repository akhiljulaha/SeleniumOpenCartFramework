package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {
	
	@BeforeClass
	public void regSetup() {
		registerPage = loginPage.navigateToRegisterPage();
	}
	public String getRandomEmailId() {
		return "testautomaton"+System.currentTimeMillis()+"@gmail.com";
//		return "testautomation"+ UUID.randomUUID()+"@gmail.com";
	} 
	
//****	
//	@DataProvider(name = "regExcelData")          //
//	public Object[][] getRegExcelTestData() {
//		Object regData[][] = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);   
//		return regData;
//	}
//	@Test(dataProvider = "regExcelData")
//	public void userRegisterTest(String firstName,String lastName, String telephone, String password, String subscribe) {
//		String actRegSuccMessg = registerPage.registeruser(firstName, lastName, getRandomEmailId(), telephone, password, subscribe);
//	Assert.assertEquals(actRegSuccMessg, AppConstants.USER_RESG_SUCCESS_MESSG);
//	}
//***
//#########	
		@DataProvider(name="regData")                     //2b
	public Object[][] getUserRegTestData() {                                              
		return new Object[][] {
			{"abhi","anand","1234567890","abhi098", "yes"},   // once 1st is register then we have to logout then only 2nd will work
			{"abhiii","anandiii","1234567899","abhi929", "no"},
			{"abhiuuuu","anand000","1234567882","abhi733", "yes"},
				
		};
	}
	@Test(dataProvider = "regData")       //  2a   passing with the help of dataprovider
	public void userRegisterTest(String firstName,String lastName, String telephone, String password, String subscribe) {
		 String actRegSuccMessg = registerPage.registeruser(firstName, lastName, getRandomEmailId(), telephone, password, subscribe);
		 Assert.assertEquals(actRegSuccMessg, AppConstants.USER_RESG_SUCCESS_MESSG);
	}
	
	
// ** below one if you not using the dataprovider concept along with the @test	       //1 directly passing the value 
//	@Test
//	public void userRegisterTest() {
//		 String actRegSuccMessg = registerPage.registeruser("abhi", "raj", getRandomEmailId(), "9871717171", "abhi@123", "Yes");
//		 Assert.assertEquals(actRegSuccMessg, AppConstants.USER_RESG_SUCCESS_MESSG);
//	}
}
