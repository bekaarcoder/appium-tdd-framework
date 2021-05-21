package com.qa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
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
import io.appium.java_client.screenrecording.CanRecordScreen;
import okhttp3.internal.platform.Platform;

public class BaseTest {

	protected static ThreadLocal<AppiumDriver<MobileElement>> driver = new ThreadLocal<AppiumDriver<MobileElement>>();
	protected static ThreadLocal<Properties> props = new ThreadLocal<Properties>();
	protected static ThreadLocal<HashMap<String, String>> strings = new ThreadLocal<HashMap<String, String>>();
	protected static ThreadLocal<String> dateTime = new ThreadLocal<String>();
	TestUtils utils;
	
	public AppiumDriver getDriver() {
		return driver.get();
	}
	
	public void setDriver(AppiumDriver Driver) {
		driver.set(Driver);
	}

	public String getDateTime() {
		return dateTime.get();
	}
	
	public void setDateTime(String DateTime) {
		dateTime.set(DateTime);
	}
	
	public Properties getProperties() {
		return props.get();
	}
	
	public void setProperties(Properties Props) {
		props.set(Props);;
	}
	
	public HashMap<String, String> getStrings() {
		return strings.get();
	}
	
	public void setStrings(HashMap<String, String> Strings) {
		strings.set(Strings);;
	}

	public BaseTest() {
		PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
	}

	@Parameters({ "platformName", "platformVersion", "deviceName" })
	@BeforeTest
	public void setup(String platformName, String platformVersion, String deviceName) throws Exception {
		utils = new TestUtils();
		setDateTime(utils.getDateTime());
		InputStream inputStream = null;
		InputStream stringsInput = null;
		Properties props = new Properties();
		AppiumDriver<MobileElement> driver;
		try {
			String xmlFileName = "static/strings.xml";
			stringsInput = getClass().getClassLoader().getResourceAsStream(xmlFileName);
			setStrings(utils.parseStringXML(stringsInput));

			String propFileName = "config.properties";
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			props.load(inputStream);
			setProperties(props);

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
			MobileDriver.setMobileDriver(driver);;
			String sessionId = driver.getSessionId().toString();
			setDriver(driver);
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

	@BeforeMethod
	public void beforeMethod() {
		// Start screen recording
		((CanRecordScreen) getDriver()).startRecordingScreen();
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		// Stop screen recording
		String media = ((CanRecordScreen) getDriver()).stopRecordingScreen();
		
		// Check if test is failing
		// This check is implemented to store the recorded video only when the test is failing
		// getStatus() return Integer value based on the test status. 2 is for FAIL
		if(result.getStatus() == 2) {
			Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
			String dir = "videos" + File.separator + params.get("platformName") + "_" + params.get("platformVersion") + "_"
					+ params.get("deviceName") + File.separator + dateTime + File.separator
					+ result.getTestClass().getRealClass().getSimpleName();
			
			File videoDir = new File(dir);
			
			if(!videoDir.exists()) {
				videoDir.mkdirs();
			}
			
			try {
				FileOutputStream stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
				stream.write(Base64.decodeBase64(media));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void waitForVisibility(MobileElement e) {
		WebDriverWait wait = new WebDriverWait(getDriver(), TestUtils.WAIT);
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
		((AndroidDriver<MobileElement>) MobileDriver.getMobileDriver())
				.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
						+ "new UiSelector().description(\"test-Price\"));");
	}

	public void closeApp() {
		((InteractsWithApps) getDriver()).closeApp();
	}

	public void launchApp() {
		((InteractsWithApps) getDriver()).launchApp();
	}

	@AfterTest
	public void tearDown() {
		getDriver().quit();
	}

}
