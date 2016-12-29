package com.coveros.training.mtw.cucumber;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.coveros.test.selenium.pom.PageObjectFactory;
import com.coveros.training.SauceProperties;
import com.coveros.training.mtw.selenium.pom.ProductDetailsPage;
import com.coveros.training.mtw.selenium.pom.SearchResultsPage;
import com.coveros.training.mtw.selenium.pom.ShoppingCartConfirmDialog;
import com.coveros.training.mtw.selenium.pom.ShoppingCartPage;
import com.coveros.training.mtw.selenium.pom.TargetHomePage;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.MarionetteDriverManager;

/**
 * Define the Given, When, Then for the Shopping Cart feature.
 * 
 * Shutting down the device after test is complete. Also after errors.
 * 
 * @author brian
 *
 */
public class ShoppingCartStepDefinitions extends MobileWebCucumberTest {

	static {
		MarionetteDriverManager.getInstance().setup();
	}
	private WebDriver driver;

	private static final int TIMEOUT = 10;

	private String itemName;

	private String productType;

	private PageObjectFactory factory;

	// public ShoppingCartStepDefinitions(SharedWebDriver driver) {
	// System.out.println("Shared Web Driver = " + driver);
	// }

	@Given("^I open (.*) on an? (.*) running (.*)$")
	public void openSite(String site, String device, String platformVersion) throws Throwable {
		try {
			System.out.println("Opening the browser " + device);
			URL url = new URL("http://127.0.0.1:4723/wd/hub");
			DesiredCapabilities capabilities = new DesiredCapabilities();

			// Not a good mechanism for our single machine setup but it will
			// work if we define each set of tests in terms of Android device or
			// iOS devices
			if (device.startsWith("iPhone")) {
				capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device);
				this.driver = new IOSDriver<>(url, capabilities);
			} else if (device.startsWith("Android")) {
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device);
				capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
				this.driver = new AndroidDriver<>(url, capabilities);
			} else if (device.equalsIgnoreCase("browser")) {
				if (platformVersion.equalsIgnoreCase("Firefox")) {
					String os = SauceProperties.getString(SauceProperties.OS);
					String geckodriver = "geckodriver";
					if (os.equals("windows")) {
						geckodriver += ".exe";
					}
					System.setProperty("webdriver.gecko.driver",
							"src/test/resources/geckodriver/" + os + "/" + geckodriver);
					capabilities = DesiredCapabilities.firefox();
					capabilities.setCapability("marionette", true);
					driver = new MarionetteDriver(capabilities);
					driver.manage().deleteAllCookies();
					driver.manage().window().setSize(new Dimension(375, 1000));
				} else if (platformVersion.equalsIgnoreCase("Chrome")) {
					String os = SauceProperties.getString(SauceProperties.OS);
					String driverName = "chromedriver";
					if (os.equals("windows")) {
						driverName += ".exe";
					}
					System.setProperty("webdriver.chrome.driver",
							"src/test/resources/" + driverName + "/" + os + "/" + driverName);
					driver = new ChromeDriver(DesiredCapabilities.chrome());
					driver.manage().deleteAllCookies();
					driver.manage().window().setSize(new Dimension(375, 1000));
				} else {
					fail("Unsupported browser: " + platformVersion);
				}
			} else {
				fail("Unrecognized device " + device);
				return;
			}

			factory = PageObjectFactory.newInstance(driver, "http://target.com");
			System.out.println("Finished opening the browser. Now running tests...");
		} catch (Exception e) {
			error(e, true);
		}
	}

	@Given("^I search for (.*)$")
	public void performSearch(String itemType) throws Throwable {
		try {
			SearchResultsPage searchResultsPage = factory.newPage(TargetHomePage.class).searchFor(itemType);
			this.productType = itemType;
		} catch (Exception e) {
			error(e, true);
		}
	}

	@When("^I add (\\d+) of (.*) to my shopping cart$")
	public void addItemsToCart(int count, String itemName) throws Throwable {
		try {
			SearchResultsPage searchResults = factory.newPage(SearchResultsPage.class);
			ProductDetailsPage productDetails = searchResults.selectProduct(itemName);
			ShoppingCartConfirmDialog confirmDialog = productDetails.addQuantityToCart(count);
			ShoppingCartPage cartPage = confirmDialog.clickViewCartAndCheckOut();
			this.itemName = itemName;
		} catch (Exception e) {
			error(e, true);
		}
	}

	@Then("^I find (\\d+) items in my cart$")
	public void verifyCart(int count) throws Throwable {
		try {
			ShoppingCartPage cartPage = factory.newPage(ShoppingCartPage.class);
			assertEquals(count, cartPage.getQuantityInCart(itemName));
			finishTest();
		} catch (Exception e) {
			error(e, true);
		}
	}

	@Override
	public WebDriver getDriver() {
		return this.driver;
	}

	@Before
	public final void beforeScenario(Scenario scenario) {
		System.out.println("Starting scenario " + scenario.getId());
	}

	@After
	public final void afterScenario(Scenario scenario) {
		System.out.println("Finishing scenario " + scenario.getId());
		if (getDriver() != null) {
			getDriver().close();
			getDriver().quit();
		} else {
			System.out.println("Tried to quit but driver was null");
		}
	}
}
