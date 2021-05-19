package com.qa.pages;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductDetailsPage extends BaseTest {
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]")
	private MobileElement productTitle;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")
	private MobileElement productDescription;
	
	@AndroidFindBy(accessibility = "test-Price")
	private MobileElement productPrice;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-BACK TO PRODUCTS\"]/android.widget.TextView")
	private MobileElement backButton;
	
	public String getProductTitle() {
		return getText(productTitle);
	}
	
	public String getProductDescription() {
		return getText(productDescription);
	}
	
	public String getProductPrice() {
		return getText(productPrice);
	}
	
	public ProductDetailsPage scrollToProductPrice() {
		scrollToElement();
		return this;
	}
	
	public ProductsPage clickBackButton() {
		click(backButton);
		return new ProductsPage();
	}

}
