# axe-devtools-android-sample-app

This repo contains an inaccessible sample application to try out axeDevTools for Android.

To use this repository, clone it and in the build.gradle (:app), fill in your API Key in the `AXE_DEVTOOLS_APIKEY` variable. If you do not have an API key, generate one on your [settings page.](https://axe.deque.com/settings)

```groovy
android {
    def AXE_DEVTOOLS_APIKEY = "YOUR_API_KEY_HERE"
    ...
}
```

Once you add the API Key, you can run the app. The app is ready to go for accessibility testing by tapping on the floating action button to kick off a scan at any point you'd like. <!-- Insert documentation link once new docs are updated -->.

You can also see accessibility testing in action through the  `ExampleEndToEndAccessibilityTest` Espresso. You can kick it off manually, or through an automated service such as Sauce Labs.


## SauceLabs

If you have an account with Sauce Labs, you can run the automated tests of this application by using our prewritten Sauce Labs configuration file `.sauce/config.yml`. Follow the steps below to get started:

1. Install saucectl command-line interface. [See Using the saucectl CLI](https://docs.saucelabs.com/dev/cli/saucectl/)

1. Add your Sauce Labs credentials to your .bash_profile or .zshenv. Be sure to load the changes by running source .filename. [See Associate your Credentials](https://docs.saucelabs.com/dev/cli/saucectl/#associate-your-credentials)

1. Update the prewritten `.sauce/config.yml` [See Configuring Your Espresso Tests](https://docs.saucelabs.com/mobile-apps/automated-testing/espresso-xcuitest/espresso/)

1. Run test on Sauce Labs from terminal: `./gradlew app:assembleDebug && ./gradlew app:assembleAndroidTest && ./saucectl run`


## Known Issue

There is an issue running the app on JDK higher than 11.

The gradle plugin is updated with Android Studio Dolphin. If you are using an older version of Android Studio, update the gradle plugin to make it work.


## Note
```
- The application has limited functionality
- The application is intentionally inaccessible
```