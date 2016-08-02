package com.coveros.training.mtw.selenium.pom;

import com.coveros.training.mtw.selenium.SeleniumMobileHelper;
import com.coveros.training.mtw.selenium.SeleniumMobileHelper.Locator;

/**
 * Page Object representing the page displayed when the shopping cart is empty.
 * 
 * @author brian
 *
 */
public class EmptyCartPage extends TargetWebsiteSearchablePage {

	static EmptyCartPage newInstance(SeleniumMobileHelper selenium, TargetWebsitePageObjectFactory factory)
			throws PageLoadException {
		selenium.throwIfTextNotFoundInElement(Locator.CSS, "h1.title-text.alpha", "your cart is empty",
				"Timeout or unexpected text looking for empty cart message");
		return new EmptyCartPage(selenium, factory);
	}

	private EmptyCartPage(SeleniumMobileHelper selenium, TargetWebsitePageObjectFactory factory) {
		super(selenium, factory);
	}

	public String getEmptyCartMessageText() {
		return (getSelenium().getElementText(Locator.CSS, "h1.title-text.alpha"));
	}
}
