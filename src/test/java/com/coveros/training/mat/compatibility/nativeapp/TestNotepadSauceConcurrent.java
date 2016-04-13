package com.coveros.training.mat.compatibility.nativeapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.coveros.training.SauceProperties;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

import io.appium.java_client.android.AndroidDriver;

/**
 * Demonstrates how to write a JUnit test that runs native app tests against
 * Sauce Labs using multiple devices and Android versions in parallel.
 * <p/>
 * The test also includes the {@link SauceOnDemandTestWatcher} which will invoke
 * the Sauce REST API to mark the test as passed or failed.
 *
 * @author Brian Hicks
 */
@RunWith(ConcurrentParameterized.class)
public class TestNotepadSauceConcurrent implements SauceOnDemandSessionIdProvider {

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
	@Rule
	public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

	/**
	 * Represents the browser to be used as part of the test run.
	 */
	private String device;
	/**
	 * Represents the operating system to be used as part of the test run.
	 */
	private String os;
	/**
	 * Represents the version of the O to be used as part of the test run.
	 */
	private String version;
	/**
	 * Instance variable which contains the Sauce Job Id.
	 */
	private String sessionId;

	private String testName;

	/**
	 * Sets the test name so it can be used to construct the Sauce Web Driver
	 * with the proper test name including the class and the test method.
	 */
	@Rule
	public TestWatcher testWatcher = new TestWatcher() {
		@Override
		protected void starting(final Description description) {
			String methodName = description.getMethodName();
			String className = description.getClassName();
			className = className.substring(className.lastIndexOf('.') + 1);
			testName = " " + className + " " + methodName;
		}
	};
	/**
	 * The {@link WebDriver} instance which is used to perform browser
	 * interactions with.
	 */
	private AndroidDriver<WebElement> driver;

	private final String noteTitle = "Adding Note Text";

	/**
	 * Constructs a new instance of the test. The constructor requires three
	 * string parameters, which represent the operating system, version and
	 * browser to be used when launching a Sauce VM. The order of the parameters
	 * should be the same as that of the elements within the
	 * {@link #browsersStrings()} method.
	 * 
	 * @param os
	 * @param version
	 * @param device
	 */
	public TestNotepadSauceConcurrent(String os, String version, String device) {
		super();
		this.os = os;
		this.version = version;
		this.device = device;
	}

	/**
	 * @return a LinkedList containing String arrays representing the browser
	 *         combinations the test should be run against. The values in the
	 *         String array are used as part of the invocation of the test
	 *         constructor
	 */
	@ConcurrentParameterized.Parameters(name = "{0} {1} on {2}")
	public static LinkedList<String[]> browsersStrings() {
		LinkedList<String[]> browsers = new LinkedList<>();
		browsers.add(new String[] { "Android", "4.4", "Samsung Galaxy S4 Emulator" });
		browsers.add(new String[] { "Android", "4.4", "Samsung Galaxy S3 Emulator" });
		browsers.add(new String[] { "Android", "4.3", "Samsung Galaxy S4 Emulator" });
		browsers.add(new String[] { "Android", "4.3", "Samsung Galaxy S3 Emulator" });
		browsers.add(new String[] { "Android", "4.2", "Samsung Galaxy Tab 3 Emulator" });
		return browsers;
	}

	/**
	 * Constructs a new {@link RemoteWebDriver} instance which is configured to
	 * use the capabilities defined by the {@link #device}, {@link #version} and
	 * {@link #os} instance variables, and which is configured to run against
	 * ondemand.saucelabs.com, using the username and access key populated by
	 * the {@link #authentication} instance.
	 *
	 * @throws Exception
	 *             if an error occurs during the creation of the
	 *             {@link RemoteWebDriver} instance.
	 */
	@Before
	public void setUp() throws Exception {
		DesiredCapabilities caps = DesiredCapabilities.android();
		caps.setCapability("appiumVersion", "1.4.10");
		caps.setCapability("deviceName", device);
		caps.setCapability("deviceOrientation", "portrait");
		caps.setCapability("browserName", "");
		caps.setCapability("platformVersion", version);
		caps.setCapability("platformName", os);
		
		// Reference your APK file here
		caps.setCapability("app", "sauce-storage:YourApp.apk");
		caps.setCapability("name", testName);

		driver = new AndroidDriver<>(new URL("http://" + authentication.getUsername() + ":"
				+ authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"), caps);
		sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
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

	/**
	 * Closes the {@link WebDriver} session.
	 *
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		driver.quit();
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

	/**
	 *
	 * @return the value of the Sauce Job id.
	 */
	@Override
	public String getSessionId() {
		return sessionId;
	}
}