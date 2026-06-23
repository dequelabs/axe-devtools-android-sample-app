# Android Sample App for axe DevTools Mobile by Deque

A sample application built solely to showcase axe DevTools Android automated espresso test implementation. It is non-functional and made inaccessibly by design.

## Helpful Links
- Library documentation and more information on automated or manual testing can be found at [docs.deque.com](https://docs.deque.com/devtools-mobile/).
- Learn more about [Deque and axe DevTools Mobile here](https://www.deque.com/).

------

## Getting Started
We currently support two ways users can implement our library

#### Gradle Plugin
Users looking to keep the amount of code needed to implement our library to a minimum should look at
using our Gradle Plugin. Using this allows us to take on the boilerplate for you. With a few lines of
code in your `build.gradle` file you can have a report generated for you that covers the accessibility
violations in your app. We prefer this implementation in our own use of this library, so please try it out!

#### Manual Dependencies
For those who would like more control over the dependencies and how they are implemented we also provide
our base library without implementing any code for you. Please note, we unfortunately cannot support 
Auto Scan for users who opt to go this route. 

### Setting Up The Plugin
1. Clone the repository
2. Set up a new project on [axe Developer Hub](https://axe.deque.com/axe-watcher) to set up a Project ID and API Key.
3. In `app/build.gradle` Add `id 'com.deque.android' version '1.+'` to your plugins block and add your API key and project ID to the axeDevTools block shown in an example below.

```groovy
axeDevTools {
    axeMobileApiKey = axe_api_key
    axeProjectId = axe_project_id
}
```
If you would like to customize the implementation of this further the following example shows all the configuration options we have available to you:
```groovy
axeDevTools {
    axeMobileApiKey = axe_api_key
    axeProjectId = axe_project_id
    axeAutoScanMode = true // to enable auto scan mode (default false)
    axeUploadResults = false // if you want to only look at results locally (default true)
    axeAccountUrl = "www.custom-url.com" // found in your project settings on axe Developer Hub (default https://axe.deque.com)
    axeHtmlReportPath = "/path/to/desired/folder" // customize where your accessibility reports are saved (defaults to your project's build/reports directory)
}
```

Once you add your credentials you are ready to start scanning the application using the espresso tests.

You can see accessibility testing in action through the `ExampleEndToEndAccessibilityTest` Espresso test or any other test in the `androidTest` folder.
The `androidTest` folder contains examples of `Jeptpack Compose`, `XML` and `UiAutomator`.
You can kick it off from Android Studio, or through an automated service such as Perfecto, Sauce Labs, etc.

_Note:_ 
- This project is set to use the Android Gradle plugin version 8.11.2. Running the project with newer, or older, JDK versions may require you to update the Gradle plugin version accordingly. Please refer to [Android's Updating the Gradle Plugin](https://developer.android.com/studio/releases/gradle-plugin#updating-plugin) documentation for more.


### Perfecto

If you have an account with Perfecto, you can run the automated tests of this application by using our prewritten Perfecto configuration file `configFile.json` that shards the tests across any two available devices. Follow the steps below to get started:

1. **Project's build.gradle**: Add  to **repositories** section. Add `classpath 'com.perfectomobile.instrumentedtest.gradleplugin:plugin:+'` to **dependencies** section. [See Configure the project for Perfecto section](https://help.perfecto.io/perfecto-help/content/perfecto/automation-testing/espresso.htm)

2. **app/build.gradle**: Add `id 'com.perfectomobile.instrumentedtest.gradleplugin'` to **plugins** section.

3. Update the prewritten `configFile.json` if needed. [See Android configuration parameters](https://help.perfecto.io/perfecto-help/content/perfecto/automation-testing/android_configuration_parameters_for_the_gradle_plugin.htm)

4. Run a test on Perfecto from the terminal: `./gradlew perfecto-android-inst -PconfigFileLocation=configFile.json -PcloudURL=demo.perfectomobile.com -PsecurityToken=$SECURITY_TOKEN`  where cloudURL should reflect your Perfecto cloud URL and securityToken should reflect your [Perfecto's security token](https://help.perfecto.io/perfecto-help/content/perfecto/automation-testing/generate_security_tokens.htm).

Reach out to [Perfecto Support](https://www.perforce.com/support?product=Perfecto) for any assistance.

### Sauce Labs

If you have an account with Sauce Labs, you can run the automated tests of this application by using our prewritten Sauce Labs configuration file `.sauce/config.yml`. Follow the steps below to get started:

1. Install the `saucectl` command line interface. [See Using the saucectl CLI](https://docs.saucelabs.com/dev/cli/saucectl/)

2. Add your Sauce Labs credentials to your `.bash_profile` or `.zshenv`. Be sure to load the changes by running `source .filename`. [See Associate your Credentials](https://docs.saucelabs.com/dev/cli/saucectl/#associate-your-credentials)

3. Update the prewritten `.sauce/config.yml` [See Configuring Your Espresso Tests](https://docs.saucelabs.com/mobile-apps/automated-testing/espresso-xcuitest/espresso/)

4. Run a test on Sauce Labs from the terminal: `./gradlew app:assembleDebug && ./gradlew app:assembleAndroidTest && ./saucectl run`



## Have a Question? Found a bug?

[Reach out to us](https://docs.deque.com/devtools-mobile/help), we'd love to help!
