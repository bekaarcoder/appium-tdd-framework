package com.qa.tests;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;

public class LoginTests extends BaseTest {
	
	LoginPage loginPage;
	ProductsPage productsPage;
	
	@BeforeMethod
	public void beforeMethod(Method m) {
		loginPage = new LoginPage();
		System.out.println("\n" + "***** Starting test: " + m.getName() + " *****" + "\n");
	}
	
	@Test
	public void invalidUsernameTest() {
		loginPage.enterUsername("invalidusername");
		loginPage.enterPassword("secret_sauce");
		loginPage.pressLoginButton();
		String actualErrorText = loginPage.getErrorText();
		String expectedErrorText = "Username and password do not match any user in this service.";
		System.out.println("Actual error text: " + actualErrorText + "\n" + "Expected error text: " + expectedErrorText);
		Assert.assertEquals(actualErrorText, expectedErrorText);
	}
	
	@Test
	public void invalidPasswordTest() {
		loginPage.enterUsername("standard_user");
		loginPage.enterPassword("invalidpassword");
		loginPage.pressLoginButton();
		String actualErrorText = loginPage.getErrorText();
		String expectedErrorText = "Username and password do not match any user in this service.";
		System.out.println("Actual error text: " + actualErrorText + "\n" + "Expected error text: " + expectedErrorText);
		Assert.assertEquals(actualErrorText, expectedErrorText);
	}
	
	@Test
	public void validLoginTest() {
		loginPage.enterUsername("standard_user");
		loginPage.enterPassword("secret_sauce");
		productsPage = loginPage.pressLoginButton();
		String actualHeaderText = productsPage.getHeaderText();
		String expectedHeaderText = "PRODUCTS";
		System.out.println("Actual header text: " + actualHeaderText + "\n" + "Expected header text: " + expectedHeaderText);
		Assert.assertEquals(actualHeaderText, expectedHeaderText);
	}

}
