package com.coveros.training.mtw.selenium.pom;

import com.coveros.training.mtw.selenium.SeleniumMobileHelper.Locator;

public final class ShoppingCartConfirmDialog extends TargetWebsitePageObject {

	@Override
	protected boolean checkInitialPageState() {
		return getSelenium().isTextMatchingElementWithExplicitWait(Locator.CSS, "h2.itemRtText.h-standardSpacingLeft",
				"added to cart");
	}

	public ProductDetailsPage clickContinueShopping() {
		getSelenium().tapElement(Locator.CSS, ".dismissModal-ATC");
		return factory.newPage(ProductDetailsPage.class);
	}

	public ProductDetailsPage closeDialog() {
		// click "X" to close dialog
		getSelenium().tapElement(Locator.CSS,
				".animate-slideDown > a:nth-child(3) > svg:nth-child(1) > use:nth-child(1)");
		return factory.newPage(ProductDetailsPage.class);
	}

	public ShoppingCartPage clickViewCartAndCheckOut() throws PageLoadException {
		getSelenium().waitForElementClickable(Locator.CSS, ".cart-ATC");

		getSelenium().tapElement(Locator.CSS, ".cart-ATC");
		return factory.newPage(ShoppingCartPage.class);
	}

	public ProductDetailsPage selectRelatedProduct(String productName) throws PageLoadException {
		return factory.newPage(ProductDetailsPage.class);
	}
}
