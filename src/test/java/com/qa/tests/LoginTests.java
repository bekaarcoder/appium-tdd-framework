package com.qa.tests;

import java.io.InputStream;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;
import com.qa.utils.TestUtils;

public class LoginTests extends BaseTest {
	
	LoginPage loginPage;
	ProductsPage productsPage;
	InputStream dataInput;
	JSONObject loginData;
	TestUtils utils = new TestUtils();
	
	@BeforeClass
	public void beforeClass() throws Exception {
		try {
			String dataFileName = "data/loginData.json";
			dataInput = getClass().getClassLoader().getResourceAsStream(dataFileName);
			JSONTokener tokener = new JSONTokener(dataInput);
			loginData = new JSONObject(tokener);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(dataInput != null) {
				dataInput.close();
			}
		}
		
	}
	
	@BeforeMethod
	public void beforeMethod(Method m) {
		loginPage = new LoginPage();
		utils.log().info("\n" + "***** Starting test: " + m.getName() + " *****" + "\n");
	}
	
	@Test
	public void invalidUsernameTest() {
		loginPage.enterUsername(loginData.getJSONObject("invalidUser").getString("username"));
		loginPage.enterPassword(loginData.getJSONObject("invalidUser").getString("password"));
		loginPage.pressLoginButton();
		String actualErrorText = loginPage.getErrorText();
		String expectedErrorText = getStrings().get("err_invalid_username_or_password");
		utils.log().info("Actual error text: " + actualErrorText + "\n" + "Expected error text: " + expectedErrorText);
		Assert.assertEquals(actualErrorText, expectedErrorText);
	}
	
	@Test
	public void invalidPasswordTest() {
		loginPage.enterUsername(loginData.getJSONObject("invalidPassword").getString("username"));
		loginPage.enterPassword(loginData.getJSONObject("invalidPassword").getString("password"));
		loginPage.pressLoginButton();
		String actualErrorText = loginPage.getErrorText();
		String expectedErrorText = getStrings().get("err_invalid_username_or_password");
		utils.log().info("Actual error text: " + actualErrorText + "\n" + "Expected error text: " + expectedErrorText);
		Assert.assertEquals(actualErrorText, expectedErrorText);
	}
	
	@Test
	public void validLoginTest() {
		loginPage.enterUsername(loginData.getJSONObject("validUser").getString("username"));
		loginPage.enterPassword(loginData.getJSONObject("validUser").getString("password"));
		productsPage = loginPage.pressLoginButton();
		String actualHeaderText = productsPage.getHeaderText() + "remove this";
		String expectedHeaderText = getStrings().get("product_header_title");
		utils.log().info("Actual header text: " + actualHeaderText + "\n" + "Expected header text: " + expectedHeaderText);
		Assert.assertEquals(actualHeaderText, expectedHeaderText);
	}

}
