# Android Sample App for axe DevTools Mobile by Deque

A sample application built solely to showcase axe DevTools Android automated espresso test implementation. It is non-functional and made inaccessibly by design.

Get started with a [free trial today](https://axe.dequelabs.com/signup?product=axe-devtools-mobile&redirect_uri=https://axe.dequelabs.com/axe-devtools-mobile/get-started).

## Helpful Links
- Library documentation can be found at [docs.deque.com](https://docs.deque.com/devtools-mobile/).
- Learn more about [Deque and axe DevTools Mobile here](https://www.deque.com/).

------

## Getting Started:

1. Clone the repository
2. Grab an API key from the [settings page](https://axe.deque.com/settings)
3. In `app/build.gradle`, add your API Key in the `AXE_DEVTOOLS_APIKEY` variable

```groovy
android {
    def AXE_DEVTOOLS_APIKEY = "YOUR_API_KEY_HERE"
    
}
```

Once you add the API Key you are ready to start scanning the application using the espresso tests.

You can see accessibility testing in action through the `ExampleEndToEndAccessibilityTest` Espresso test or any other test in the `androidTest` folder.
The `androidTest` folder contains examples of `Jeptpack Compose`, `XML` and `UiAutomator`.
You can kick it off from Android Studio, or through an automated service such as Sauce Labs.

_Note:_ 
- This project is set to use the Android Gradle plugin version 7.3.1. Running the project with newer, or older, JDK versions may require you to update the Gradle plugin version accordingly. Please refer to [Android's Updating the Gradle Plugin]((https://developer.android.com/studio/releases/gradle-plugin#updating-plugin)) documentation for more.

### Sauce Labs

If you have an account with Sauce Labs, you can run the automated tests of this application by using our prewritten Sauce Labs configuration file `.sauce/config.yml`. Follow the steps below to get started:

1. Install the `saucectl` command line interface. [See Using the saucectl CLI](https://docs.saucelabs.com/dev/cli/saucectl/)

2. Add your Sauce Labs credentials to your `.bash_profile` or `.zshenv`. Be sure to load the changes by running `source .filename`. [See Associate your Credentials](https://docs.saucelabs.com/dev/cli/saucectl/#associate-your-credentials)

3. Update the prewritten `.sauce/config.yml` [See Configuring Your Espresso Tests](https://docs.saucelabs.com/mobile-apps/automated-testing/espresso-xcuitest/espresso/)

4. Run a test on Sauce Labs from the terminal: `./gradlew app:assembleDebug && ./gradlew app:assembleAndroidTest && ./saucectl run`
   
## Have a Question? Found a bug?

[Reach out to us](https://docs.deque.com/devtools-mobile/2023.8.16/en/help), we'd love to help!
