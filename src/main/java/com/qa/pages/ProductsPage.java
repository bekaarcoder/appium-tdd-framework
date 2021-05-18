package com.qa.pages;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsPage extends BaseTest {

	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView")
	private MobileElement productHeader;
	
	@AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]")
	private MobileElement productTitle;
	
	@AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Price\"])[1]")
	private MobileElement productPrice;
	
	
	public String getHeaderText() {
		return getAttribute(productHeader, "text");
	}
	
	public String getProductTitle() {
		return getText(productTitle);
	}
	
	public String getProductPrice() {
		return getText(productPrice);
	}
	
	public ProductDetailsPage clickProductTitle() {
		click(productTitle);
		return new ProductDetailsPage();
	}

}
