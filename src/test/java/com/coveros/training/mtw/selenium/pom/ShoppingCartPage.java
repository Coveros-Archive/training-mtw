package com.coveros.training.mtw.selenium.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.coveros.training.mtw.selenium.SeleniumMobileHelper;
import com.coveros.training.mtw.selenium.SeleniumMobileHelper.Locator;

/**
 * Page Object representing the shopping cart page for the Target website
 * 
 * @author brian
 *
 */
public final class ShoppingCartPage extends TargetWebsiteSearchablePage {

	static ShoppingCartPage newInstance(SeleniumMobileHelper selenium, TargetWebsitePageObjectFactory factory)
			throws PageLoadException {
		// Make sure that at least the title text element has loaded properly
		// before returning the new page object
		// Note that we are not validating that the page is correct.
		WebElement titleTextElement = selenium.isElementPresentWithExplicitWait(Locator.CSS, ".title--text");
		if (titleTextElement == null) {
			throw new PageLoadException("The shopping cart page did not load properly");
		}

		return new ShoppingCartPage(selenium, factory);
	}

	/**
	 * Get the 
	 * @return
	 */
	public String getCartSummaryText() {
		WebElement titleTextElement = getSelenium().isElementPresentWithExplicitWait(Locator.CSS, ".title--text");
		if (titleTextElement != null) {
			return titleTextElement.getText();
		} else {
			return null;
		}
	}

	private ShoppingCartPage(SeleniumMobileHelper selenium, TargetWebsitePageObjectFactory factory) {
		super(selenium, factory);
	}

	public ConfirmRemoveItemDialog removeFirstItemFromShoppingCart() throws PageLoadException {
		return removeNthItemFromCart(1);
	}

	public ConfirmRemoveItemDialog removeItemFromCart(String itemName) throws PageLoadException {
		List<WebElement> matchingElements = getSelenium().getElementsWithAttributesMatching(Locator.CSS, ".btn",
				"aria-label", "remove " + itemName);
		if (matchingElements.size() > 0) {
			matchingElements.get(0).click();
		} else {
			throw new PageLoadException("Could not find appropriate remove button on page");
		}
		return getFactory().newConfirmRemoveItemDialog(this);
	}

	/**
	 * Determine the number of items in the cart for the specified product.
	 * 
	 * @param productName
	 *            the name of the product
	 * @return an integer >= 0; this
	 */
	public int getQuantityInCart(String productName) {
		WebDriver driver = getSelenium().getDriver();
		// There may be many items in the cart. This gets the all of the div
		// elements that represent cart items.
		List<WebElement> elements = new WebDriverWait(driver, 10)
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("cartItem")));

		// This will be the element that represents the cart item of interest,
		// if
		// present.
		WebElement productInfoDivElement = null;
		for (WebElement element : elements) {
			// Find the right element by looking for an image sub-element that
			// has as its alternative text the name of the item that we are
			// looking for.
			WebElement prodImageElement = element.findElement(By.className("cartItem--image"));
			if (prodImageElement != null) {
				String altAttribute = prodImageElement.getAttribute("alt");
				if (altAttribute != null && altAttribute.startsWith(productName)) {
					productInfoDivElement = element;
				}
			}
		}
		// If the element exists, get the quantity by inspecting the
		// corresponding selector element
		if (productInfoDivElement != null) {
			WebElement selectorQuantity = productInfoDivElement.findElement(By.cssSelector(".quantityDropdown--value"));
			if (selectorQuantity != null) {
				String text = selectorQuantity.getText();
				return new Integer(text);
			}
		}
		return 0;

	}

	public ConfirmRemoveItemDialog removeNthItemFromCart(int n) throws PageLoadException {
		getSelenium().tapElement(Locator.XPATH, "//div[" + (n + 1) + "]/div/button");
		return getFactory().newConfirmRemoveItemDialog(this);
	}
}
