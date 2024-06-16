package com.qa.opencart.utils;

import java.util.Arrays;
import java.util.List;

public class AppConstants {

	public static final String LOGIN_PAGE_TITLE_VALUE = "Account Login";
	public static final String ACCOUNTS_PAGE_TITLE_VALUE = "My Account";
	public static final String LOGIN_PAGE_URL_FRACTION_VALUE = "route=account/login";
	public static final int SHORT_DEFAULT_WAIT = 5;
	public static final int MEDIUM_DEFAULT_WAIT = 10;
	public static final int LONG_DEFAULT_WAIT = 20;

	public static final List<String> EXP_ACCOUNTS_HEADERS_LIST = 
			Arrays.asList("My Account", "My Orders", "My Affiliate Account", "Newsletter");
	
	public static final CharSequence LOGIN_ERROR_MESSAGE = "Warning: No match for E-Mail Address and/or Password.";
	public static final String USER_RESG_SUCCESS_MESSG = "Your Account Has Been Created!";
//	//**************** Sheet Names ************************//	
	public static final String REGISTER_SHEET_NAME = "register";
}
/*
what is differance between constant data and confiquration data
*constant data will never change throught your application
*confiquration data is in whick browser you want to run , userName and password, want to run in the headless mode or incognito
*/