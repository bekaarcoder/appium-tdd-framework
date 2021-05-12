package com.qa.pages;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends BaseTest {

	@AndroidFindBy(accessibility = "test-Username")
	private MobileElement usernameField;
	@AndroidFindBy(accessibility = "test-Password")
	private MobileElement passwordField;
	@AndroidFindBy(accessibility = "test-LOGIN")
	private MobileElement loginBtn;
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
	private MobileElement errorText;

	public LoginPage enterUsername(String username) {
		sendKeys(usernameField, username);
		return this;
	}

	public LoginPage enterPassword(String password) {
		sendKeys(passwordField, password);
		return this;
	}

	public ProductsPage pressLoginButton() {
		click(loginBtn);
		return new ProductsPage();
	}
	
	public String getErrorText() {
		return getAttribute(errorText, "text");
	}

}
