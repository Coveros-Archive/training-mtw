package com.coveros.training.mtw.cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

/**
 * Define the Given, When, Then for the Shopping Cart feature.
 * 
 * Shutting down the device after test is complete. Also after errors.
 * 
 * @author brian
 *
 */
public class ShoppingCartStepDefinitions extends MobileWebCucumberTest {

	private WebDriver driver;

	private static final int TIMEOUT = 10;

	private String itemName;

	@Given("^I open target.com on an (.*) running (.*)$")
	public void openSite(String device, String platformVersion) throws Throwable {
		try {
			System.out.println("Opening the browser " + device);
			URL url = new URL("http://127.0.0.1:4723/wd/hub");
			DesiredCapabilities capabilities = new DesiredCapabilities();

			// Not a good mechanism for our single machine setup but it will
			// work if
			// we define each set of tests in terms of Android device or iOS
			// devices
			if (device.startsWith("iPhone")) {
				capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.3");
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device);
				this.driver = new IOSDriver<>(url, capabilities);
			} else if (device.startsWith("Android")) {
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0");
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device);
				capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
				this.driver = new AndroidDriver<>(url, capabilities);
			} else {
				fail("Unrecognized device " + device);
				return;
			}
			driver.get("http://www.target.com/");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			System.out.println("Finished opening the browser");
		} catch (Exception e) {
			error(e, true);
		}
	}

	@Given("^I search for (.*)$")
	public void performSearch(String itemType) throws Throwable {
		try {
			driver.findElement(By.id("searchLabel")).click();
			driver.findElement(By.id("search")).clear();
			driver.findElement(By.id("search")).sendKeys(itemType);
			driver.findElement(By.xpath("(//button[@id='searchReset'])[2]")).click();
			for (int second = 0;; second++) {
				if (second >= TIMEOUT)
					fail("timeout");
				try {
					if (("“" + itemType + "”").equals(
							driver.findElement(By.xpath("//div[@id='slp-facet-wrap']/section/div/div/h1")).getText()))
						break;
				} catch (Exception e) {
					error(e, false);
				}
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			// Fail on error because the element could not be found or was null
			// and the test cannot continue
			error(e, true);
		}
	}

	@When("^I add (\\d+) of (.*) to my shopping cart$")
	public void addItemsToCart(int count, String itemName) throws Throwable {
		this.itemName = itemName;
		try {
			driver.findElement(By.partialLinkText(itemName)).click();
			for (int second = 0;; second++) {
				if (second >= TIMEOUT)
					fail("timeout");
				try {
					WebElement el = driver.findElement(By.cssSelector("p.details--title"));
					if (el == null) {
						el = driver.findElement(By.cssSelector("h2.title-product > span"));
					}
					String elementText = el.getText();

					if ((elementText != null) && (elementText.startsWith(itemName))) {
						break;
					}
				} catch (Exception e) {
				}
				Thread.sleep(1000);
			}

			new Select(driver.findElement(By.id("sbc-quantity-picker")))
					.selectByVisibleText(new Integer(count).toString());
			driver.findElement(By.xpath("//div[@id='AddToCartAreaId']/div/div/button")).click();
			for (int second = 0;; second++) {
				if (second >= TIMEOUT)
					fail("timeout");
				try {
					if ((count + " added to cart").equals(
							driver.findElement(By.cssSelector("h2.itemRtText.h-standardSpacingLeft")).getText()))
						break;
				} catch (Exception e) {
				}
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			error(e, true);
		}

	}

	@Then("^I find the right number of items in my cart$")
	public void verifyCart() throws Throwable {
		try {
			driver.findElement(By.xpath("//button[@type='button']")).click();
			for (int second = 0;; second++) {
				if (second >= TIMEOUT)
					fail("timeout");
				try {
					// if ("iPad Pro".equals(this.browser)) {
					// // Potential bug here. Estimated tax is added to the
					// total
					// // on phone-sized devices, but not when viewed on iPad
					// Pro.
					// if ("cart total: $799.98"
					// .equals(driver.findElement(By.xpath("//div[@id='cart-page']/div/div/h1")).getText()))
					// {
					// break;
					// }
					// } else {
					// // Potential bug here. Estimated tax is added on
					// phone-size
					// // devices.
					// if ("cart total: $831.98"
					// .equals(driver.findElement(By.xpath("//section[@id='cart-page']/div/div/h1")).getText()))
					// {
					//
					// break;
					// }
					if (driver.findElement(By.xpath("//section[@id='cart-page']/div/div/h1")).getText()
							.startsWith("cart total")) {

						break;
					}
					// }
				} catch (Exception e) {
					e.printStackTrace();
				}
				Thread.sleep(1000);
			}

			assertTrue(driver.findElement(By.partialLinkText(itemName)).getText().startsWith(itemName));
			String priceString = driver.findElement(By.cssSelector("span.cartItem--price")).getText();
			// assertTrue (new Integer(priceString) > 0);
			// assertEquals("Bose 251 Environmental Outdoor Speaker System -
			// Black
			// (24653)", driver
			// .findElement(By.linkText("Bose 251 Environmental Outdoor Speaker
			// System - Black (24653)")).getText());
			// assertEquals("$799.98",
			// driver.findElement(By.cssSelector("span.cartItem--price")).getText());
			driver.findElement(By.xpath("//div/div[2]/div/button")).click();
			for (int second = 0;; second++) {
				if (second >= TIMEOUT)
					fail("timeout");
				try {
					if ("remove this item from your cart?".equals(
							driver.findElement(By.xpath("//div[@id='basicModal']/div[2]/div/div/h2")).getText())) {
						break;
					}

				} catch (Exception e) {
				}
				Thread.sleep(1000);
			}

			driver.findElement(By.xpath("//div[2]/div/div[2]/button")).click();
			for (int second = 0;; second++) {
				if (second >= TIMEOUT)
					fail("timeout");
				try {
					if ("your cart is empty"
							.equals(driver.findElement(By.cssSelector("h1.title-text.alpha")).getText()))
						break;
				} catch (Exception e) {
				}
				Thread.sleep(1000);
			}

			assertEquals("your cart is empty", driver.findElement(By.cssSelector("h1.title-text.alpha")).getText());
			driver.close();
			driver.quit();
			// for (int second = 0;; second++) {
			// if (second >= 60)
			// fail("timeout");
			// try {
			// if
			// (driver.findElement(By.xpath("//div[@id='addtocart']/div/div/div[2]/h3")).getText()
			// .matches("^cart summary[\\s\\S]*$"))
			// break;
			// } catch (Exception e) {
			// }
			// Thread.sleep(1000);
			// }
			// assertEquals("1 item added to cart",
			// driver.findElement(By.xpath("//div[@id='addtocart']/div/div/div/div/h2")).getText());
			// assertEquals(itemName,
			// driver.findElement(By.xpath("//div[2]/ul/li/div[2]/a")).getText());
			// try {
			// assertEquals(Integer.toString(count),
			// driver.findElement(By.id("cartUpdatedQty_cartItem0001")).getAttribute("value"));
			// } catch (Error e) {
			// verificationErrors.append(e.toString());
			// } finally{
			// driver.close();
			// }
		} catch (Exception e) {
			error(e, true);
		}
	}

	@Override
	public WebDriver getDriver() {
		return this.driver;
	}

}
