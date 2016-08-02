package com.coveros.training.mtw.sauce;

import java.util.LinkedList;

import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.coveros.training.SauceProperties;
import com.coveros.training.mtw.selenium.TargetShoppingCartTest;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

/**
 * Demonstrates how to write a JUnit test that runs tests against Sauce Labs
 * using multiple browsers in parallel.
 * <p/>
 * The test also includes the {@link SauceOnDemandTestWatcher} which will invoke
 * the Sauce REST API to mark the test as passed or failed.
 *
 * @author Ross Rowe
 */
@RunWith(ConcurrentParameterized.class)
public class MobileWebSauceParameterized extends TargetShoppingCartTest implements SauceOnDemandSessionIdProvider {

	/**
	 * Constructs a {@link SauceOnDemandAuthentication} instance using the
	 * supplied user name/access key. To use the authentication supplied by
	 * environment variables or from an external file, use the no-arg
	 * {@link SauceOnDemandAuthentication} constructor.
	 */
	public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(
			SauceProperties.getString(SauceProperties.USER_NAME),
			SauceProperties.getString(SauceProperties.ACCESS_KEY));

	/**
	 * JUnit Rule which will mark the Sauce Job as passed/failed when the test
	 * succeeds or fails.
	 */
	@Rule
	public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

	@Rule
	public TestName testName = new TestName();
	/**
	 * Represents the browser to be used as part of the test run.
	 */
	private String browser;
	/**
	 * Represents the operating system to be used as part of the test run.
	 */
	private String device;
	/**
	 * Represents the version of the browser to be used as part of the test run.
	 */
	private String osVersion;

	private String platformName;

	/**
	 * Instance variable which contains the Sauce Job Id.
	 */
	private String sessionId;

	/**
	 * Constructs a new instance of the test. The constructor requires three
	 * string parameters, which represent the operating system, version and
	 * browser to be used when launching a Sauce VM. The order of the parameters
	 * should be the same as that of the elements within the
	 * {@link #browsersStrings()} method.
	 * 
	 * @param device
	 * @param osVersion
	 * @param platformName
	 */
	public MobileWebSauceParameterized(String device, String osVersion, String platformName, String browser) {
		super();
		this.device = device;
		this.osVersion = osVersion;
		this.browser = browser;
		this.platformName = platformName;
	}

	/**
	 * @return a LinkedList containing String arrays representing the browser
	 *         combinations the test should be run against. The values in the
	 *         String array are used as part of the invocation of the test
	 *         constructor
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ConcurrentParameterized.Parameters
	public static LinkedList browsersStrings() {
		LinkedList browsers = new LinkedList();
		browsers.add(new String[] { "iPhone 6 Plus", "9.3", "iOS", "Safari" });
		browsers.add(new String[] { "iPhone 5s", "8.1", "iOS", "Safari" });
		browsers.add(new String[] { "Samsung Galaxy Nexus Emulator", "4.4", "Android", "Browser" });

		return browsers;
	}

	/**
	 *
	 * @return the value of the Sauce Job id.
	 */
	@Override
	public String getSessionId() {
		return sessionId;
	}

	@Override
	protected PlatformType getPlatformType() {
		return PlatformType.SAUCE;
	}

	@Override
	protected DesiredCapabilities getCapabilities() {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("appiumVersion", "1.5.3");
		caps.setCapability("deviceName", device);
		caps.setCapability("deviceOrientation", "portrait");
		caps.setCapability("platformVersion", this.osVersion);
		caps.setCapability("platformName", this.platformName);
		caps.setCapability("browserName", this.browser);
		caps.setCapability("name",
				testName.getMethodName() + " [" + browser + " on "+ platformName + " " + osVersion + "]");

		return caps;

	}
}