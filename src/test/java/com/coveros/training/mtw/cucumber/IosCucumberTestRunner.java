package com.coveros.training.mtw.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/cucumber/resources/com/coveros/training/mtw/cucumber/shoppingcartios.feature" })
/**
 * Simple JUnit Cucumber test runner. Runs features found in the directory
 * specified by the cucumber options
 * 
 * @author brian
 *
 */
public class IosCucumberTestRunner {

}
