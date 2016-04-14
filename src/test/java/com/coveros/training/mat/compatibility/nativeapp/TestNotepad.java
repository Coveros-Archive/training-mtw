package com.coveros.training.mat.compatibility.nativeapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public final class TestNotepad {

	private File classpathRoot = new File("root\\path\\to\\directory");
	private File appDir = new File(classpathRoot, "directory\\path\\to\\apks");
	private File app = new File(appDir, "apks\\path\\to\\app.apk");

	private static AndroidDriver<WebElement> driver;

	private final String noteTitle = "Adding Note Text";

	/**
	 * This method initializes and creates the web driver with desired
	 * capabilities. The @BeforeClass annotation tells JUnit to call this prior
	 * to the first initialization of this class, ensuring that the same driver
	 * session is used for each test.
	 * 
	 * @throws MalformedURLException
	 */
	@Before
	public final void setUpDriver() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Name of mobile web browser to automate. Should be an empty string
		// if
		// automating an app instead.
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");

		// which mobile OS to use: Android, iOS or FirefoxOS
		capabilities.setCapability("platformName", "Android");

		// Mobile OS version � in this case 4.4 since my device is running
		// Android 4.4.2
		capabilities.setCapability(CapabilityType.VERSION, "4.4");

		// device name � since this is an actual device name is found using
		// ADB
		capabilities.setCapability("deviceName", "model:Samsung_Galaxy_S5___4_4_4___API_19___1080x1920");

		// the absolute local path to the APK
		capabilities.setCapability("app", app.getAbsolutePath());

		// Java package of the tested Android app
		capabilities.setCapability("appPackage", "ru.andrey.notepad");

		// activity name for the Android activity you want to run from your
		// package. This need to be preceded by a . (example: .MainActivity)
		capabilities.setCapability("appActivity", ".MainActivity");

		// constructor to initialize driver object
		driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		// driver.closeApp();
	}

	@After
	public final void shutDown() {
		driver.quit();
	}

	@Test
	public void testAddNote() throws Exception {
		try {
			if (createNote(noteTitle)) {
				List<WebElement> noteTitles = driver.findElements(By.id("ru.andrey.notepad:id/title"));
				// Assert that there is only one note
				assertEquals(1, noteTitles.size());

				WebElement noteTitleTextView = noteTitles.get(0);
				// Assert that the one note has the expected title.
				assertEquals(noteTitle, noteTitleTextView.getText());
			}

		} catch (NoSuchElementException nse) {
			fail("Unable to find element: " + nse.getMessage());
		}
	}

	private boolean createNote(String title) {
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
			return false;
		} else {
			addNoteBtn.click();
			WebElement noteText = driver.findElement(By.id("ru.andrey.notepad:id/editText1"));
			noteText.sendKeys(title);

			WebElement optionsBtn = driver.findElementByClassName("android.widget.ImageButton");

			optionsBtn.click();
			WebElement homeTextView = driver.findElement(By.id("android:id/title"));
			if (homeTextView != null) {
				homeTextView.click();
				return true;
			} else {
				fail("Unable to find Home TextView in Options menu");
				return false;
			}

		}
	}

	@Test
	public void testRemoveNote() {
		if (createNote(noteTitle)) {
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
		} else {
			fail("Could not create a note so unable to test removal of notes");
		}
	}
}
