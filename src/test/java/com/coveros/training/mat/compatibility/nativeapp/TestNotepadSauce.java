package com.coveros.training.mat.compatibility.nativeapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.coveros.training.SauceProperties;
import com.saucelabs.common.SauceOnDemandAuthentication;

import io.appium.java_client.android.AndroidDriver;

public final class TestNotepadSauce {

	private File classpathRoot = new File("root\\path\\to\\directory");
	private File appDir = new File(classpathRoot, "directory\\path\\to\\apks");
	private File app = new File(appDir, "apks\\path\\to\\app.apk");

	private static AndroidDriver<WebElement> driver;
	private static String sessionId;

	/**
	 * Constructs a {@link SauceOnDemandAuthentication} instance using the
	 * supplied user name/access key. To use the authentication supplied by
	 * environment variables or from an external file, use the no-arg
	 * {@link SauceOnDemandAuthentication} constructor.
	 */
	public static SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(
			SauceProperties.getString(SauceProperties.USER_NAME), SauceProperties.getString(SauceProperties.ACCESS_KEY));
	
	@BeforeClass
	public static final void setUpDriver() throws MalformedURLException {

		DesiredCapabilities caps = DesiredCapabilities.android();
		caps.setCapability("appiumVersion", "1.4.10");
		caps.setCapability("deviceName", "Samsung Galaxy S4 Emulator");
		caps.setCapability("deviceOrientation", "portrait");
		caps.setCapability("browserName", "");
		caps.setCapability("platformVersion", "4.4");
		caps.setCapability("platformName", "Android");
		caps.setCapability("app", "sauce-storage:Notepad.apk");
		caps.setCapability("name", "Test Notepad");
		// constructor to initialize driver object
		// driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),
		// capabilities);

		driver = new AndroidDriver<WebElement>(new URL("http://" + authentication.getUsername() + ":"
				+ authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"), caps);
		sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
	}

	@AfterClass
	public static void shutDown() {
		driver.quit();
	}

	@Test
	public void testAddNote() throws Exception {
		try {
			final String noteTitle = "Adding Note Text";
			WebElement bigPlusBtn = driver.findElement(By.id("ru.andrey.notepad:id/button1"));
			bigPlusBtn.click();

			List<WebElement> textViews = driver.findElements(By.className("android.widget.TextView"));
			WebElement addNoteBtn = textViews.get(0);

			if (!"Note".equals(addNoteBtn.getText())) {
				addNoteBtn = null;
				for (WebElement el : textViews) {
					if ("Note".equals(el.getText())) {
						addNoteBtn = el;
						break;
					}
				}
			}
			if (addNoteBtn == null) {
				fail("Unable to find New Note TextView");
			} else {
				addNoteBtn.click();
				WebElement noteText = driver.findElement(By.id("ru.andrey.notepad:id/editText1"));
				noteText.sendKeys(noteTitle);

				WebElement optionsBtn = driver.findElementByClassName("android.widget.ImageButton");

				/**
				 * Why can't I get random attributes? Is it because there is no
				 * resource ID?
				 */
				// if ("More
				// options".equals(optionsBtn.getAttribute("content-desc"))) {
				// System.out.println("Found \"content-desc\" attribute");
				// }
				optionsBtn.click();
				WebElement homeTextView = driver.findElement(By.id("android:id/title"));
				if (homeTextView != null) {
					homeTextView.click();
					List<WebElement> noteTitles = driver.findElements(By.id("ru.andrey.notepad:id/title"));
					// Assert that there is only one note
					assertEquals(1, noteTitles.size());

					WebElement noteTitleTextView = noteTitles.get(0);
					// Assert that the one note has the expected title.
					assertEquals(noteTitle, noteTitleTextView.getText());

				} else {
					fail("Unable to find Home TextView in Options menu");
				}

			}
		} catch (NoSuchElementException nse) {
			fail("Unable to find element: " + nse.getMessage());
		}
	}

	@Test
	public void testRemoveNote() {

		List<WebElement> noteTitles = driver.findElements(By.id("ru.andrey.notepad:id/title"));
		assertEquals(1, noteTitles.size());

		WebElement trashBtn = driver.findElementById("ru.andrey.notepad:id/imageView2");
		trashBtn.click();

		WebElement okBtn = driver.findElementById("android:id/button1");
		if ("OK".equals(okBtn.getText())) {
			okBtn.click();
		}

		noteTitles = driver.findElements(By.id("ru.andrey.notepad:id/title"));
		// Assert that there are no notes left
		assertEquals(0, noteTitles.size());
	}
}
