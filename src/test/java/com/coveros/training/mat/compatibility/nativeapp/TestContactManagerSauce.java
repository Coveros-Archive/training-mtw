package com.coveros.training.mat.compatibility.nativeapp;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.fail;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.ErrorHandler;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.coveros.training.SauceProperties;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

import io.appium.java_client.android.AndroidDriver;

public class TestContactManagerSauce {

	private File classpathRoot = new File("root\\path\\to\\directory");
	private File appDir = new File(classpathRoot, "directory\\path\\to\\apks");
	private File app = new File(appDir, "apks\\path\\to\\YourApp.apk");

	// private AndroidDriver<WebElement> driver;

	/**
	 * Constructs a {@link SauceOnDemandAuthentication} instance using the
	 * supplied user name/access key. To use the authentication supplied by
	 * environment variables or from an external file, use the no-arg
	 * {@link SauceOnDemandAuthentication} constructor.
	 */
	public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(SauceProperties.getString("SAUCE_USER_NAME"), //$NON-NLS-1$
			SauceProperties.getString("SAUCE_PRIVATE_KEY"));
	/**
	 * JUnit Rule which will mark the Sauce Job as passed/failed when the test
	 * succeeds or fails.
	 */
	// @Rule
	// public SauceOnDemandTestWatcher resultReportingTestWatcher = new
	// SauceOnDemandTestWatcher(this, authentication);

	/**
	 * Represents the browser to be used as part of the test run.
	 */
	private String browser;
	/**
	 * Represents the operating system to be used as part of the test run.
	 */
	private String os;
	/**
	 * Represents the version of the browser to be used as part of the test run.
	 */
	private String version;
	/**
	 * Instance variable which contains the Sauce Job Id.
	 */
	private String sessionId;

	/**
	 * The {@link WebDriver} instance which is used to perform browser
	 * interactions with.
	 */
	private AndroidDriver<WebElement> driver;

	@Before
	public final void setUp() throws MalformedURLException {

		DesiredCapabilities caps = DesiredCapabilities.android();
		caps.setCapability("appiumVersion", "1.4.10");
		caps.setCapability("deviceName", "Samsung Galaxy S4 Emulator");
		caps.setCapability("deviceOrientation", "portrait");
		caps.setCapability("browserName", "");
		caps.setCapability("platformVersion", "4.4");
		caps.setCapability("platformName", "Android");
		// Use name of your APK file here
		caps.setCapability("app", "sauce-storage:YourApp.apk");
		caps.setCapability("name", "Test Contact Manager");
		// constructor to initialize driver object
		// driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),
		// capabilities);

		this.driver = new AndroidDriver<WebElement>(new URL("http://" + authentication.getUsername() + ":"
				+ authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"), caps);
		this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
	}

	// Example of an Appium-based functional test; locators are app-specific;
	// change to match appropriate app.
	@Test
	public void addContact() throws Exception {
		try {
			WebElement addContactButton = driver.findElement(By.name("Add Contact"));
			addContactButton.click();
			driver.findElement(By.id("com.example.android.contactmanager:id/contactNameEditText"))
					.sendKeys("Some Name");
			// driver.findElementById("com.example.android.contactmanager:id/contactNameEditText").sendKeys("Some
			// Name");
			List<WebElement> textFieldsList = driver.findElements(By.className("android.widget.EditText"));
			// textFieldsList.get(0).sendKeys("Some Name");
			textFieldsList.get(2).sendKeys("Some@example.com");
			driver.findElement(By.name("Save")).click();

			WebElement checkboxEl = driver.findElementByClassName("android.widget.CheckBox");
			if (checkboxEl == null) {
				checkboxEl = driver.findElementById("com.example.android.contactmanager:id/showInvisible");
			}
			if (checkboxEl != null) {
				checkboxEl.click();
				List<WebElement> contactEntries = driver
						.findElements(By.id("com.example.android.contactmanager:id/contactEntryText"));
				assertEquals(1, contactEntries.size());
				assertEquals("Some Name", contactEntries.get(0).getText());
			} else {
				fail("Unable to find checkbox element");
			}
		} catch (NoSuchElementException nse) {
			fail("Unable to find element: " + nse.getMessage());
		} finally {
			driver.quit();
		}
		// driver.close();
		// insert assertions here
	}
}
