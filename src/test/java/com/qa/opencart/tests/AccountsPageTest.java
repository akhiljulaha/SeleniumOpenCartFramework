package com.qa.opencart.tests;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;

public class AccountsPageTest extends BaseTest {
//no need to create a object of the AccountsPage, call the doLogin method in the login class. 
// this will return the object of the Account page class  and then storing the account page ref.

	@BeforeClass
	public void accPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));  // prop ref. coming from the baseTest class
	}// loginPage ref. variable inferit from the baseTest class and accountPage
		// ref.also maintaing on the baseTest class

	@Test
	public void accPageTitleTest() {
		String actTitle = accPage.getAccPageTitle();
		Assert.assertEquals(actTitle, AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);
	}

	@Test
	public void idLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}

	@Test
	public void accPageHeadersCountTest() {
		List<String> actAccHeadersList = accPage.getAccountPageHeaderList();
		Assert.assertEquals(actAccHeadersList.size(), 4);
	}

	@Test
	public void accPageHeadersTest() {
		List<String> actAccHeadersList = accPage.getAccountPageHeaderList();
//		List<String> expAccHeadersList = Arrays.asList("My Account", "My Orders", "My Affiliate Account", "Newsletter"); // using here app contants concept, no need of this line																													// this
		Assert.assertEquals(actAccHeadersList, AppConstants.EXP_ACCOUNTS_HEADERS_LIST);
	}

}
