# Android Sample App for axe DevTools Mobile by Deque

A sample application built solely to showcase axe DevTools Android automated espresso test implementation. It is non-functional and made inaccessibly by design.

## Helpful Links
- Library documentation and more information on automated or manual testing can be found at [docs.deque.com](https://docs.deque.com/devtools-mobile/).
- Learn more about [Deque and axe DevTools Mobile here](https://www.deque.com/).

------

## Getting Started:

1. Clone the repository
2. Set up a new project on [axe Developer Hub](https://axe.deque.com/axe-watcher) to set up a Project ID and API Key.
      - Or Grab an API key from the [settings page](https://axe.deque.com/settings)
3. In `app/build.gradle`, add your API Key in the `AXE_DEVTOOLS_APIKEY` variable
4. If you set up a project add your Project ID in the `app/build.gradle` under the `AXE_DEVTOOLS_PROJECT_ID` variable

```groovy
android {
    def AXE_DEVTOOLS_APIKEY = "YOUR_API_KEY_HERE"
    def AXE_DEVTOOLS_PROJECT_ID = "AXE_PROJECT_ID_HERE"
}
```

Once you add your variables you are ready to start scanning the application using the espresso tests. If you need addditio

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
