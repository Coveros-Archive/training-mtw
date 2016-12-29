package com.coveros.training.mtw.selenium.pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.coveros.training.mtw.selenium.SeleniumMobileHelper.Locator;

/**
 * Page object representing the page that is used to display the results of a
 * product search.
 * 
 * @author brian
 *
 */
public class SearchResultsPage extends TargetWebsiteSearchablePage {

	@FindBy(xpath = "//div[@id='slp-facet-wrap']/section/div/div/h1")
	private WebElement searchTitle;

	public ProductDetailsPage selectProduct(String productName) throws PageLoadException {
		getSelenium().scrollToClick(Locator.PARTIAL_LINK, productName);
		return factory.newPage(ProductDetailsPage.class);
	}

	/**
	 * 
	 * @param title
	 * @return
	 */
	public boolean hasSearchTitle(String title) {
		return getSelenium().isTextInElementWithExplicitWait(Locator.XPATH,
				"//div[@id='slp-facet-wrap']/section/div/div/h1", title);
	}
}
