package com.coveros.training.mtw.selenium.pom;

import org.openqa.selenium.WebDriver;

import com.coveros.training.mtw.selenium.SeleniumMobileHelper;

/**
 * Factory used to create page objects for the Target website.
 * 
 * @author brian
 *
 */
public final class TargetWebsitePageObjectFactory {

	private SeleniumMobileHelper selenium;

	public static TargetWebsitePageObjectFactory newInstance(WebDriver driver) {
		return new TargetWebsitePageObjectFactory(new SeleniumMobileHelper(driver, "http://www.target.com"));
	}

	private TargetWebsitePageObjectFactory(SeleniumMobileHelper selenium) {
		this.selenium = selenium;
	}

	public EmptyCartPage newEmptyCartPage() throws PageLoadException {
		return EmptyCartPage.newInstance(selenium, this);
	}

	public TargetHomePage newTargetHomePage() {
		return TargetHomePage.newInstance(selenium, this);
	}

	public SearchResultsPage newSearchResultsPage(String searchTerm) throws PageLoadException {
		return SearchResultsPage.newInstance(selenium, this, searchTerm);

	}

	public ConfirmRemoveItemDialog newConfirmRemoveItemDialog(ShoppingCartPage parent) throws PageLoadException {
		return ConfirmRemoveItemDialog.newInstance(selenium, this, parent);
	}

	public ShoppingCartPage newShoppingCartPage() throws PageLoadException {
		return ShoppingCartPage.newInstance(selenium, this);
	}

	public ProductDetailsPage newProductDetailsPage(String productName) throws PageLoadException {
		return ProductDetailsPage.newInstance(selenium, this, productName);
	}

	public ShoppingCartConfirmDialog newShoppingCartConfirmDialog(ProductDetailsPage productDetailsPage, int quantity)
			throws PageLoadException {
		return ShoppingCartConfirmDialog.newInstance(selenium, this, productDetailsPage, quantity);
	}
}
