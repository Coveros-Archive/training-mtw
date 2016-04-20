package com.coveros.training.mtw.cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Define the Given, When, Then for the Shopping Cart feature.
 * 
 * @author brian
 *
 */
public class ShoppingCartStepDefinitions {

	private WebDriver driver;
	private int count;
	private String itemName;
	private StringBuffer verificationErrors = new StringBuffer();

	@Given("^I open target.com in (.*)$")
	public void openSite(String browser) throws Throwable {
		System.out.println("Opening the browser " + browser);
		if ("firefox".equals(browser) || "Firefox".equals(browser)) {
		driver = new FirefoxDriver();
		} else {
			fail("Unrecognized browser " + browser);
			return;
		}
		driver.get("http://www.target.com/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println("Finished opening the browser");
	}

	@Given("^I search for (.*)$")
	public void performSearch(String itemType) throws Throwable {
		System.out.println("Perform search for " + itemType);
		driver.findElement(By.id("searchTerm")).clear();
		driver.findElement(By.id("searchTerm")).sendKeys(itemType);
		driver.findElement(By.id("goSearch")).click();
		System.out.println("Finished searching for " + itemType);		
	}

	@When("^I add (\\d+) of (.*) to my shopping cart$")
	public void addItemsToCart(int count, String itemName) throws Throwable {
		System.out.println("Adding " + count + " of " + itemName + " to cart");
		// Write code here that turns the phrase above into concrete actions
		//driver.findElement(By.xpath("(//a[contains(text(),'" + itemName + "')])[2]")).click();
		driver.findElement(By.linkText(itemName)).click();
		this.itemName = driver.findElement(By.cssSelector("span.fn")).getText();
		this.count = count;
		for (int i = 1; i < count - 1; i++) {
			driver.findElement(By.cssSelector("button.plus")).click();
		}
		driver.findElement(By.cssSelector("button.plus")).click();
		driver.findElement(By.id("addToCart")).click();
	}

	@Then("^I find the right number of items in my cart$")
	public void verifyCart() throws Throwable {
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (driver.findElement(By.xpath("//div[@id='addtocart']/div/div/div[2]/h3")).getText()
						.matches("^cart summary[\\s\\S]*$"))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}
		assertEquals("1  item added to cart",
				driver.findElement(By.xpath("//div[@id='addtocart']/div/div/div/div/h2")).getText());
		assertEquals(itemName, driver.findElement(By.xpath("//div[2]/ul/li/div[2]/a")).getText());
		try {
			assertEquals(Integer.toString(count),
					driver.findElement(By.id("cartUpdatedQty_cartItem0001")).getAttribute("value"));
		} catch (Error e) {
			verificationErrors.append(e.toString());
		} finally{
			driver.close();
		}
	}

}
