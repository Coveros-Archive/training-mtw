package com.coveros.training.mtw.nativeapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.coveros.training.SauceProperties;
import com.saucelabs.common.SauceOnDemandAuthentication;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

/**
 * Use a local Appium Server to test a native iOS Application. The app being
 * tested is the demo UICatalog app available from the
 * <a href="https://github.com/appium/ios-uicatalog">Appium Github site</a>
 * <p>
 * Download the latest version of the UICatalog.app file and place it in the
 * apps directory in this project.
 * 
 * See <a href="https://github.com/Coveros/training-mtw"> the Coveros MTW
 * Github</a> for more details on running this test.
 * 
 * @author brian
 *
 */
public class UICatalogIosNativeAppTest {

	private AppiumDriver<IOSElement> wd;
	/**
	 * Constructs a {@link SauceOnDemandAuthentication} instance using the
	 * supplied user name/access key. To use the authentication supplied by
	 * environment variables or from an external file, use the no-arg
	 * {@link SauceOnDemandAuthentication} constructor.
	 */
	public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(
			SauceProperties.getString(SauceProperties.USER_NAME),
			SauceProperties.getString(SauceProperties.ACCESS_KEY));

	@Test
	public void testSampleIOSApp() {

		wd.findElement(By.xpath("//UIAApplication[1]/UIAWindow[2]/UIATableView[1]/UIATableCell[1]")).click();
		// wd.findElement(By.name("Gray")).click();
		wd.findElement(By.xpath("//UIAApplication[1]/UIAWindow[2]/UIANavigationBar[1]/UIAButton[1]")).click();
		wd.findElement(By.xpath("//UIAApplication[1]/UIAWindow[2]/UIATableView[1]/UIATableCell[8]")).click();
		wd.findElement(By.xpath("//UIAApplication[1]/UIAWindow[2]/UIANavigationBar[1]/UIAButton[1]")).click();
		wd.findElement(By.xpath("//UIAApplication[1]/UIAWindow[2]/UIATableView[1]/UIATableCell[6]")).click();
		assertEquals("UIDatePicker",
				wd.findElement(
						By.xpath("//UIAApplication[1]/UIAWindow[2]/UIAToolbar[1]/UIASegmentedControl[1]/UIAButton[2]"))
				.getText());

	}

	@After
	public void tearDown() {
		wd.closeApp();
		wd.quit();
	}

	// wd = new IOSDriver<>(new URL("http://" + authentication.getUsername() +
	// ":" + authentication.getAccessKey()
	// + "@ondemand.saucelabs.com:80/wd/hub"), capabilities);
	@Before
	public void setup() {

		File classpathRoot = new File(System.getenv("HOME"));
		File appDir = new File("apps");
		File app = new File(appDir, "UICatalog.app");

		DesiredCapabilities capabilities = DesiredCapabilities.iphone();
		capabilities.setCapability("appium-version", "1.5.2");
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("platformVersion", "9.3");
		capabilities.setCapability("deviceName", "iPhone 6");
		capabilities.setCapability("app", app.getAbsolutePath());
		capabilities.setBrowserName("");

		try {
			wd = new IOSDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

			wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			wd.launchApp();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}

	}
}
