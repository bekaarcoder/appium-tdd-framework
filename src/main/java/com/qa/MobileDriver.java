package com.qa;

import org.openqa.selenium.WebDriver;

public class MobileDriver {
	
	 private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

	    public static WebDriver getMobileDriver(){
	        return driver.get();
	    }

	    public static void setMobileDriver(WebDriver Driver){
	        driver.set(Driver);
	    }

}
