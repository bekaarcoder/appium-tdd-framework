package com.qa.tests;

import java.io.InputStream;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.MenuPage;
import com.qa.pages.ProductDetailsPage;
import com.qa.pages.ProductsPage;

public class ProductTests extends BaseTest {
	
	LoginPage loginPage;
	ProductsPage productsPage;
	ProductDetailsPage productDetailsPage;
	MenuPage menuPage;
	InputStream dataInput;
	JSONObject loginData;
	
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
		closeApp();
		launchApp();
	}
	
	@BeforeMethod
	public void beforeMethod(Method m) {
		loginPage = new LoginPage();
		System.out.println("\n" + "***** Starting test: " + m.getName() + " *****" + "\n");
		
		productsPage = loginPage.login(loginData.getJSONObject("validUser").getString("username"), loginData.getJSONObject("validUser").getString("password"));
	}
	
	@Test
	public void validateProductOnProductsPage() {
		SoftAssert softAssert = new SoftAssert();
		
		String productTitle = productsPage.getProductTitle();
		softAssert.assertEquals(productTitle, strings.get("product_title"));
		
		String productPrice = productsPage.getProductPrice();
		softAssert.assertEquals(productPrice, strings.get("product_price"));
		
		softAssert.assertAll();
	}
	
	@Test
	public void validateProductOnProductDetailsPage() {
		SoftAssert softAssert = new SoftAssert();
		
		productDetailsPage = productsPage.clickProductTitle();
		
		String productTitle = productDetailsPage.getProductTitle();
		softAssert.assertEquals(productTitle, strings.get("product_title"));
		
		String productDescription = productDetailsPage.getProductDescription();
		softAssert.assertEquals(productDescription, strings.get("product_description"));
		
		productDetailsPage.scrollToProductPrice();
		String productPrice = productDetailsPage.getProductPrice();
		softAssert.assertEquals(productPrice, strings.get("product_price"));
		
		productsPage = productDetailsPage.clickBackButton();
		
		softAssert.assertAll();
	}
	
	@AfterMethod
	public void afterMethod() {
		menuPage = new MenuPage();
		menuPage.pressMenuIcon();
		loginPage = menuPage.pressLogoutLink();
	}

}
