package com.qa;

import org.openqa.selenium.WebDriver;

public class MobileDriver {
	
	 private static WebDriver driver;

	    public static WebDriver getDriver(){
	        return driver;
	    }

	    public static void setDriver(WebDriver Driver){
	        driver = Driver;
	    }

}
