package com.coveros.training.mtw.selenium.pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.coveros.training.mtw.selenium.SeleniumMobileHelper;
import com.coveros.training.mtw.selenium.SeleniumMobileHelper.Locator;

/**
 * Page object class representing the product details page. This is the page
 * that is visible when a product is selected from search results or any pagee
 * where product summaries are found.
 * 
 * @author brian
 *
 */
public final class ProductDetailsPage extends TargetWebsiteSearchablePage {

	@Override
	protected boolean checkInitialPageState() {
//		WebElement addToCartButton = getSelenium().isElementPresentWithExplicitWait(Locator.CSS, ".sbc-add-to-cart");
//		WebElement addToRegistryButton = getSelenium().isElementPresentWithExplicitWait(Locator.CSS,
//				".sbc-add-to-registry");
//		WebElement picker = getSelenium().isElementPresentWithExplicitWait(Locator.ID, "sbc-quantity-picker");
//		if (addToCartButton == null || addToRegistryButton == null || picker == null) {
//			// throw new PageLoadException("Product Details Page failed to load
//			// properly for product: " + productName);
//		}
//		if (!getSelenium().isLocalBrowserTest()) {
//			// if
//			// (!getSelenium().isTextMatchingElementWithExplicitWait(Locator.CSS,
//			// "p.details--title", productName)) {
//			// //throw new PageLoadException("Product Details Page failed to
//			// load properly for product: " + productName);
//			// }
//		}
		return super.checkInitialPageState();
	}

	/**
	 * Add the specified quantity of the product to the shopping cart.s
	 * 
	 * @param quantity
	 *            the number of products to add to the cart
	 * @return the page object representing the cart confirmation dialog
	 * @throws PageLoadException
	 */
	public ShoppingCartConfirmDialog addQuantityToCart(int quantity) throws PageLoadException {
		WebElement el = getSelenium().isElementPresentWithExplicitWait(Locator.ID, "sbc-quantity-picker");
		new Select(el).selectByVisibleText(new Integer(quantity).toString());

		// selenium.selectFromVisibleChoices(Locator.ID, "sbc-quantity-picker",
		// new Integer(quantity).toString());
		// WebDriver driver = selenium.getDriver();
		// new Select(driver.findElement(By.id("sbc-quantity-picker")));
		// selenium.tapElement(Locator.XPATH,
		// "//div[@id='AddToCartAreaId']/div/div/button");
		getSelenium().tapElement(Locator.CSS, ".sbc-add-to-cart");

		return factory.newPage(ShoppingCartConfirmDialog.class);
	}

}
