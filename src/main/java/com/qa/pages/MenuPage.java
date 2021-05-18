package com.qa.pages;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MenuPage extends BaseTest {
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView")
	private MobileElement menuIcon;
	
	@AndroidFindBy(accessibility = "test-LOGOUT")
	private MobileElement logoutLink;
	
	public MenuPage pressMenuIcon() {
		click(menuIcon);
		return this;
	}
	
	public LoginPage pressLogoutLink() {
		click(logoutLink);
		return new LoginPage();
	}

}
