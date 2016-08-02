package com.coveros.training.mtw.selenium.pom;

import com.coveros.training.mtw.selenium.SeleniumMobileHelper;
import com.coveros.training.mtw.selenium.SeleniumMobileHelper.Locator;

/**
 * Page object representing the page that is used to display the results of a
 * product search.
 * 
 * @author brian
 *
 */
public class SearchResultsPage extends TargetWebsiteSearchablePage {

	static SearchResultsPage newInstance(SeleniumMobileHelper selenium, TargetWebsitePageObjectFactory factory,
			String searchTerm) throws PageLoadException {
		selenium.throwIfTextNotFoundInElement(Locator.XPATH, "//div[@id='slp-facet-wrap']/section/div/div/h1",
				"“" + searchTerm + "”", "");
		return new SearchResultsPage(selenium, factory);
	}

	private SearchResultsPage(SeleniumMobileHelper selenium, TargetWebsitePageObjectFactory factory) {
		super(selenium, factory);
	}

	public ProductDetailsPage selectProduct(String productName) throws PageLoadException {
		getSelenium().scrollToClick(Locator.PARTIAL_LINK, productName);
		return getFactory().newProductDetailsPage(productName);
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
