package com.coveros.training.mtw.selenium.pom;

import com.coveros.training.mtw.selenium.SeleniumMobileHelper;
import com.coveros.training.mtw.selenium.SeleniumMobileHelper.Locator;

/**
 * Page Object representing the dialog that appears when an item is removed from
 * the shopping cart.
 * 
 * @author brian
 *
 */
public final class ConfirmRemoveItemDialog extends TargetWebsitePageObject {

	private ShoppingCartPage cartPage;

	static ConfirmRemoveItemDialog newInstance(SeleniumMobileHelper selenium, TargetWebsitePageObjectFactory factory,
			ShoppingCartPage cartPage) throws PageLoadException {
		selenium.throwIfTextNotFoundInElement(Locator.XPATH, "//div[@id='basicModal']/div[2]/div/div/h2",
				"remove this item from your cart?", "Confirm Remove Item Dialog did not appear properly");
		return new ConfirmRemoveItemDialog(selenium, factory, cartPage);
	}

	private ConfirmRemoveItemDialog(SeleniumMobileHelper selenium, TargetWebsitePageObjectFactory factory,
			ShoppingCartPage cartPage) {
		super(selenium, factory);
		this.cartPage = cartPage;
	}

	public ShoppingCartPage cancel() {
		return cartPage;
	}

	public ShoppingCartPage closeDialog() {
		return this.cartPage;
	}

	public EmptyCartPage clickRemoveButton() throws PageLoadException {
		getSelenium().tapElement(Locator.XPATH, "//div[2]/div/div[2]/button");
		return getFactory().newEmptyCartPage();
	}
}
