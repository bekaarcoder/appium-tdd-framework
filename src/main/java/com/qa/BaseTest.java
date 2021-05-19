package com.qa;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseTest {

	protected static AppiumDriver<MobileElement> driver;
	protected static Properties props;
	protected static HashMap<String, String> strings = new HashMap<String, String>();
	InputStream inputStream;
	InputStream stringsInput;
	TestUtils utils;

	public BaseTest() {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@Parameters({ "platformName", "platformVersion", "deviceName" })
	@BeforeTest
	public void setup(String platformName, String platformVersion, String deviceName) throws Exception {
		try {
			props = new Properties();

			String xmlFileName = "static/strings.xml";
			stringsInput = getClass().getClassLoader().getResourceAsStream(xmlFileName);
			utils = new TestUtils();
			strings = utils.parseStringXML(stringsInput);

			String propFileName = "config.properties";
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			props.load(inputStream);

			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, props.getProperty("androidAutomationName"));
			caps.setCapability("appPackage", props.getProperty("androidAppPackage"));
			caps.setCapability("appActivity", props.getProperty("androidAppActivity"));
			URL appURL = getClass().getClassLoader().getResource(props.getProperty("androidAppLocation"));
//			caps.setCapability("app", "C:\\Users\\shash\\Downloads\\Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
			caps.setCapability("app", appURL);

			URL url = new URL(props.getProperty("appiumURL"));

			driver = new AndroidDriver<MobileElement>(url, caps);
			MobileDriver.setDriver(driver);
			String sessionId = driver.getSessionId().toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (stringsInput != null) {
				stringsInput.close();
			}
		}
	}

	public void waitForVisibility(MobileElement e) {
		WebDriverWait wait = new WebDriverWait(driver, TestUtils.WAIT);
		wait.until(ExpectedConditions.visibilityOf(e));
	}

	public void click(MobileElement e) {
		waitForVisibility(e);
		e.click();
	}

	public void sendKeys(MobileElement e, String txt) {
		waitForVisibility(e);
		e.sendKeys(txt);
	}

	public String getAttribute(MobileElement e, String attribute) {
		waitForVisibility(e);
		return e.getAttribute(attribute);
	}

	public String getText(MobileElement e) {
		waitForVisibility(e);
		return e.getAttribute("text");
	}

	public void scrollToElement() {
		((AndroidDriver<MobileElement>) MobileDriver.getDriver()).findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
						+ "new UiSelector().description(\"test-Price\"));");
	}

	public void closeApp() {
		((InteractsWithApps) driver).closeApp();
	}

	public void launchApp() {
		((InteractsWithApps) driver).launchApp();
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
