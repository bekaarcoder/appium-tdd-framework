package com.qa.pages;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsPage extends BaseTest {

	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\\\"test-Cart drop zone\\\"]/android.view.ViewGroup/android.widget.TextView")
	private MobileElement productHeader;
	
	public String getHeaderText() {
		return getAttribute(productHeader, "text");
	}

}
