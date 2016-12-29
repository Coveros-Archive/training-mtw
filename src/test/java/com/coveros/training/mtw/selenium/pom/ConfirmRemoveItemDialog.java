package com.coveros.training.mtw.selenium.pom;

import org.openqa.selenium.By;

/**
 * Page Object representing the dialog that appears when an item is removed from
 * the shopping cart.
 * 
 * @author brian
 *
 */
public final class ConfirmRemoveItemDialog extends TargetWebsitePageObject {

	public ShoppingCartPage cancel() throws PageLoadException {
		return factory.newPage(ShoppingCartPage.class);
	}

	public ShoppingCartPage closeDialog() {
		return factory.newPage(ShoppingCartPage.class);
	}

	public EmptyCartPage clickRemoveButton() throws PageLoadException {
		driver.findElement(By.xpath("//div[2]/div/div[2]/button")).click();
		//getSelenium().tapElement(Locator.XPATH, "//div[2]/div/div[2]/button");
		return factory.newPage(EmptyCartPage.class);
	}
}
