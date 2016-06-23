# Mobile Application Testing Example Code
Example selenium and appium test code for the training class "Testing Mobile Webapps in the Cloud with Selenium" (aka the Mobile Test Workshop aka the Mobile Lab)

## Setup Instructions
These instructions assume Eclipse as an IDE and that Maven is installed on the system.
  
1. Clone this repository
1. On the command line in the training-mtw directory execute `mvn eclipse:eclipse` to configure the eclipse project and download required dependencies
1. Open Eclipse and create a new Java Project

## Mobile Web Application Testing
To run the mobile web app testing example for Android you will need to install [http://appium.io/] (Appium) and [https://www.genymotion.com/] (Genymotion). Appium is open source. Genymotion requires an account but the free version is all that is needed to run the examples as written

To run the mobile web app testing example for iOS you will need a Mac with the latest version of XCode installed.

Mobile web application examples are
* `com.coveros.training.mtw.appium.AndroidChromeExample`
* `com.coveros.training.mtw.appium.IosSafariExample`

