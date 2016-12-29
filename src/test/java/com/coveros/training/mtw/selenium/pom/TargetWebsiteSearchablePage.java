package com.coveros.training.mtw.selenium.pom;

import com.coveros.training.mtw.selenium.SeleniumMobileHelper;
import com.coveros.training.mtw.selenium.SeleniumMobileHelper.Locator;

/**
 * Superclass for page objects representing all main website pages. These are
 * the pages that have the standard search bar at the top of the page.
 * 
 * @author brian
 *
 */
public abstract class TargetWebsiteSearchablePage extends TargetWebsitePageObject {

	public String getPageTitle() {
		return getSelenium().getPageTitle();
	}

	/**
	 * Enter text in the search field
	 * 
	 * @param productCategory
	 *            the text to enter in the search field
	 * 
	 * @return a page object representing the search results page containing
	 *         product items that match the search term
	 * @throws PageLoadException
	 *             if the proper page does not load
	 */
	public SearchResultsPage searchFor(String productCategory) throws PageLoadException {
		getSelenium().tapElement(Locator.ID, "searchLabel");
		getSelenium().clearField(Locator.ID, "search");
		getSelenium().typeTextInto(Locator.ID, "search", productCategory);
		getSelenium().tapElement(Locator.XPATH, "(//button[@id='searchReset'])[2]");
		return factory.newPage(SearchResultsPage.class);
	}

	/**
	 * Click on the shopping cart icon at the top of the page
	 * 
	 * @return a page object representing the shopping cart page
	 * 
	 * @throws PageLoadException
	 *             if the proper page does not load
	 */
	public ShoppingCartPage goToShoppingCart() throws PageLoadException {
		return factory.newPage(ShoppingCartPage.class);
	}
}
