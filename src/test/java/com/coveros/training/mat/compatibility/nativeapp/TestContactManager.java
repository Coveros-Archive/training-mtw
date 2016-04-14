package com.coveros.training.mat.compatibility.nativeapp;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class TestContactManager {

	private File classpathRoot = new File("root\\path\\to\\directory");
	private File appDir = new File(classpathRoot, "directory\\path\\to\\apks");
	private File app = new File(appDir, "apks\\path\\to\\app.apk");

	private AndroidDriver<WebElement> driver;

	@Before
	public final void setUp() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Name of mobile web browser to automate. Should be an empty string if
		// automating an app instead.
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");

		// which mobile OS to use: Android, iOS or FirefoxOS
		capabilities.setCapability("platformName", "Android");

		// Mobile OS version � in this case 4.4 since my device is running
		// Android 4.4.2
		capabilities.setCapability(CapabilityType.VERSION, "4.4");

		// device name � since this is an actual device name is found using ADB
		capabilities.setCapability("deviceName", "model:Samsung_Galaxy_S5___4_4_4___API_19___1080x1920");

		// the absolute local path to the APK
		capabilities.setCapability("app", app.getAbsolutePath());

		// Java package of the tested Android app
		capabilities.setCapability("appPackage", "com.example.android.contactmanager");

		// activity name for the Android activity you want to run from your
		// package. This need to be preceded by a . (example: .MainActivity)
		capabilities.setCapability("appActivity", ".ContactManager");

		// constructor to initialize driver object
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	}

	@Test
	public void addContact() throws Exception {
		WebElement addContactButton = driver.findElement(By.name("Add Contact"));
		addContactButton.click();
		driver.findElementById("com.example.android.contactmanager:id/contactNameEditText").sendKeys("Some Name");
		List<WebElement> textFieldsList = driver.findElementsByClassName("android.widget.EditText");
		// textFieldsList.get(0).sendKeys("Some Name");
		textFieldsList.get(2).sendKeys("Some@example.com");
		driver.findElementByName("Save").click();

		driver.findElementById("com.example.android.contactmanager:id/showInvisible").click();
		List<WebElement> contactEntries = driver
				.findElementsById("com.example.android.contactmanager:id/contactEntryText");
		assertEquals("Some Name", contactEntries.get(0).getText());
		// driver.close();
		// insert assertions here
	}
}
