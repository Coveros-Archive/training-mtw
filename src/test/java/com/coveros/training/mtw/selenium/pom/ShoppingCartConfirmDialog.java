package com.coveros.training.mtw.selenium.pom;

import com.coveros.training.mtw.selenium.SeleniumMobileHelper;
import com.coveros.training.mtw.selenium.SeleniumMobileHelper.Locator;

public final class ShoppingCartConfirmDialog extends TargetWebsitePageObject {

	private ProductDetailsPage productDetailsPage;

	static ShoppingCartConfirmDialog newInstance(SeleniumMobileHelper selenium, TargetWebsitePageObjectFactory factory,
			ProductDetailsPage productDetailsPage, int quantityAdded) throws PageLoadException {
		// selenium.throwIfTextNotFoundInElement(Locator.CSS,
		// "h2.itemRtText.h-standardSpacingLeft", "added to cart",
		// "ShoppingCartConfirmDialog did not load properly");
		if (!selenium.isTextMatchingElementWithExplicitWait(Locator.CSS, "h2.itemRtText.h-standardSpacingLeft",
				"added to cart")) {
			throw new PageLoadException("ShoppingCartConfirmDialog did not load properly");
		}
		return new ShoppingCartConfirmDialog(selenium, factory, productDetailsPage);
	}

	private ShoppingCartConfirmDialog(SeleniumMobileHelper selenium, TargetWebsitePageObjectFactory factory,
			ProductDetailsPage productDetailsPage) {
		super(selenium, factory);
		this.productDetailsPage = productDetailsPage;
	}

	public ProductDetailsPage clickContinueShopping() {
		getSelenium().tapElement(Locator.CSS, ".dismissModal-ATC");
		return productDetailsPage;
	}

	public ProductDetailsPage closeDialog() {
		// click "X" to close dialog
		getSelenium().tapElement(Locator.CSS, ".animate-slideDown > a:nth-child(3) > svg:nth-child(1) > use:nth-child(1)");
		return productDetailsPage;
	}

	public ShoppingCartPage clickViewCartAndCheckOut() throws PageLoadException {
		getSelenium().waitForElementClickable(Locator.CSS, ".cart-ATC");

		getSelenium().tapElement(Locator.CSS, ".cart-ATC");
		return getFactory().newShoppingCartPage();
	}

	public ProductDetailsPage selectRelatedProduct(String productName) throws PageLoadException {
		return getFactory().newProductDetailsPage(productName);
	}
}
