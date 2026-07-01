# Android Sample App for axe DevTools Mobile by Deque

A sample application built solely to showcase axe DevTools Android automated espresso test implementation. It is non-functional and made inaccessible by design.

## Helpful Links
- Library documentation and more information on automated or manual testing can be found at [docs.deque.com](https://docs.deque.com/devtools-mobile/).
- Learn more about [Deque and axe DevTools Mobile here](https://www.deque.com/).

------

## Getting Started
We currently support two ways to scan your app for accessibility issues, both delivered through our Gradle Plugin.

#### Auto Scan
Users looking to keep the amount of code needed to implement our library to a minimum should look at
Auto Scan. Enabling it lets our Gradle Plugin take on the boilerplate for you. With a few lines of
configuration in your `build.gradle` file, a report is generated for you that covers the accessibility
violations in your app, with no test code required. We prefer this implementation in our own use of this library, so please try it out!

#### Targeted Scan
For those who would like more control over when and what gets scanned, you can run targeted scans by
calling `axe.scan()` directly in your tests. This lets you scan a specific screen or state exactly when
you want to.

### Setting Up Auto Scan
1. Clone the repository.
2. Set up a new project on [axe Developer Hub](https://axe.deque.com/axe-watcher) to set up a Project ID and API Key.
3. In `app/build.gradle`, add `id 'com.deque.android' version '1.+'` to your plugins block, then add your API key and project ID to the axeDevTools block and set `axeAutoScanMode = true` as shown in the example below.

```groovy
axeDevTools {
    axeMobileApiKey = axe_apikey
    axeProjectId = axe_project_id
    axeAutoScanMode = true
}
```
If you would like to customize the implementation of this further the following example shows all the configuration options we have available to you:
```groovy
axeDevTools {
    axeMobileApiKey = axe_apikey
    axeProjectId = axe_project_id
    axeAutoScanMode = true // to enable auto scan mode (default false)
    axeUploadResults = false // if you want to only look at results locally (default true)
    axeAccountUrl = "https://www.custom-url.com" // found in your project settings on axe Developer Hub (default https://axe.deque.com)
    axeHtmlReportPath = "/path/to/desired/folder" // customize where your accessibility reports are saved (defaults to your project's build/reports directory)
}
```
### Setting Up Targeted Scan
Targeted scans use the same Gradle Plugin, but with Auto Scan turned off so you control when scans run. Call `axe.scan()` in your tests wherever you want to capture results — see `InstrumentationRegistryExampleTest` and `AxeTestClass` for a working example.

1. Clone the repository.
2. Set up a new project on [axe Developer Hub](https://axe.deque.com/axe-watcher) to set up a Project ID and API Key.
3. In `app/build.gradle`, add `id 'com.deque.android' version '1.+'` to your plugins block and add your API key and project ID to the axeDevTools block (leave `axeAutoScanMode` omitted or set to `false`):

   ```groovy
   axeDevTools {
       axeMobileApiKey = axe_apikey
       axeProjectId = axe_project_id
   }
   ```
4. Call `axe.scan()` in your test code where you want to run a scan, as shown in `AxeTestClass`.

> **Prefer to manage the dependency yourself?** Instead of applying the Gradle Plugin, you can add our base library directly with `androidTestImplementation 'com.deque.android:axe-devtools-android:current-release'` in your `dependencies` block. Note that Auto Scan is not available when using this approach.

## Running the Tests

Once you have set up either Auto Scan or a Targeted Scan, you are ready to start scanning the application using the Espresso tests.

You can see accessibility testing in action through the `AutoScanDemoTest` and `InstrumentationRegistryExampleTest` tests, or any other test in the `androidTest` folder.
These include examples using `Jetpack Compose` and `UiAutomator`.
You can kick it off from Android Studio, or through an automated service such as Perfecto, Sauce Labs, etc.

_Note:_ 
- This project is set to use the Android Gradle plugin version 8.10.1. Running the project with newer, or older, JDK versions may require you to update the Gradle plugin version accordingly. Please refer to [Android's Updating the Gradle Plugin](https://developer.android.com/studio/releases/gradle-plugin#updating-plugin) documentation for more.


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
