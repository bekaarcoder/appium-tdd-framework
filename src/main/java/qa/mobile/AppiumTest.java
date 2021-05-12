package qa.mobile;

import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.remote.MobileCapabilityType;

import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class AppiumTest {
	
	AppiumDriver driver;
	
	By usernameField = MobileBy.AccessibilityId("test-Username");
	By passwordField = MobileBy.AccessibilityId("test-Password");
	By loginBtn = MobileBy.AccessibilityId("test-LOGIN");
	By errorText = MobileBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView");
	By productHeader = MobileBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView");

	@BeforeClass
	public void beforeClass() throws MalformedURLException {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.0");
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_3");
		caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
		caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
		caps.setCapability("appPackage", "com.swaglabsmobileapp");
		caps.setCapability("appActivity", "com.swaglabsmobileapp.SplashActivity");
//		caps.setCapability("app", "C:\\Users\\shash\\Downloads\\Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
		
		URL url = new URL("http://127.0.0.1:4723/wd/hub");
		
		driver = new AppiumDriver(url, caps);
		String sessionId = driver.getSessionId().toString();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test
	public void invalidUsernameTest() {
		driver.findElement(usernameField).sendKeys("invalidusername");
		driver.findElement(passwordField).sendKeys("secret_sauce");
		driver.findElement(loginBtn).click();
		String actualErrorText = driver.findElement(errorText).getText();
		String expectedErrorText = "Username and password do not match any user in this service.";
		Assert.assertEquals(actualErrorText, expectedErrorText);
	}
	
	@Test
	public void invalidPasswordTest() {
		driver.findElement(usernameField).sendKeys("standard_user");
		driver.findElement(passwordField).sendKeys("invalidpassword");
		driver.findElement(loginBtn).click();
		String actualErrorText = driver.findElement(errorText).getText();
		String expectedErrorText = "Username and password do not match any user in this service.";
		Assert.assertEquals(actualErrorText, expectedErrorText);
	}
	
	@Test
	public void validLoginTest() {
		driver.findElement(usernameField).sendKeys("standard_user");
		driver.findElement(passwordField).sendKeys("secret_sauce");
		driver.findElement(loginBtn).click();
		String actualHeaderText = driver.findElement(productHeader).getText();
		String expectedHeaderText = "PRODUCTS";
		Assert.assertEquals(actualHeaderText, expectedHeaderText);
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
