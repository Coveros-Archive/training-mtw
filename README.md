# Mobile Application Testing Example Code
Example selenium and appium test code for the training class "Testing Mobile Webapps in the Cloud with Selenium" (aka the Mobile Test Automation Workshop aka the Mobile Lab)

## Setup Instructions
These instructions assume Eclipse as an IDE and that Maven is installed on the system.
  
1. Clone this repository
1. On the command line in the training-mtw directory execute `mvn eclipse:eclipse` to configure the eclipse project and download required dependencies
1. Open Eclipse and create a new Java Project

## Mobile Web Application Testing
Mobile web application examples are
* `com.coveros.training.mtw.mobileweb.AndroidChromeExample` (Android)
* `com.coveros.training.mtw.mobileweb.IosSafariExample` (iOS)

To run the mobile web app testing examples you will need to install [Appium](http://appium.io/]). Appium is open source.

To run the mobile web app testing example for Android you will need to also install [Genymotion](https://www.genymotion.com/). Genymotion requires an account but the free version is all that is needed to run the examples as written

To run the mobile web app testing example for iOS you will need a Mac with the latest version of XCode installed in order to get the iOS Simulator. No third party emulation product is required.

## Native App Testing
Native application testing examples are
* `com.coveros.training.mtw.nativeapp.UICatalogIosNativeApp` (iOS)

To run the native app testing examples you will need to install [Appium](http://appium.io/]). Appium is open source.

To run the iOS native app testing example you will need a Mac running the latest version of XCode installed in order to get the iOS Simulator. No third party emulation product is required. The app under test is available from the [Appium GitHub Repository](https://github.com/appium/ios-uicatalog). Place it in the `apps` folder in this project in order to run the test.

