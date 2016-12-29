package com.coveros.training.mtw.selenium.pom;

import com.coveros.training.mtw.selenium.SeleniumMobileHelper.Locator;

/**
 * Page Object representing the page displayed when the shopping cart is empty.
 * 
 * @author brian
 *
 */
public class EmptyCartPage extends TargetWebsiteSearchablePage {

	@Override
	protected boolean checkInitialPageState() {
//		try {
//			getSelenium().throwIfTextNotFoundInElement(Locator.CSS, "h1.title-text.alpha", "your cart is empty",
//					"Timeout or unexpected text looking for empty cart message");
//		} catch (PageLoadException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return super.checkInitialPageState();
	}

	public String getEmptyCartMessageText() {
		return (getSelenium().getElementText(Locator.CSS, "h1.title-text.alpha"));
	}
}
