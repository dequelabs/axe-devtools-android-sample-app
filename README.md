# axe-devtools-android-sample-app

This repo contains an inaccessible sample application to try out axeDevTools for Android.

To use this repository, clone it and replace the build.gradle (:app) BuildConfig property `AXE_DEVTOOLS_APIKEY` with your Axe Devtools API Key. If you do not have an API key, generate one on your [settings page.](https://axe.deque.com/settings)

```groovy
buildTypes {
    ...

    debug {
        buildConfigField "String", "AXE_DEVTOOLS_APIKEY", "\"YOUR_API_KEY\""
    }
}
```

Once you have a working API Key installed, you can run the app with manual testing FAB or run the `ExampleEndToEndAccessibilityTest` Espresso test manually, or through automated services such as SauceLabs.


## SauceLabs

If you have account with Saucelabs, you can run automated test with this app by using our prewritten SauceLabs configuration file `.sauce/config.yml` on this repository and follow the steps below:

1. Install saucectl command-line interface. [See Using the saucectl CLI](https://docs.saucelabs.com/dev/cli/saucectl/)

1. Add your Sauce Labs credentials to your .bash_profile or .zshenv. Be sure to load the changes by running source .filename. [See Associate your Credentials](https://docs.saucelabs.com/dev/cli/saucectl/#associate-your-credentials)

1. Update the prewritten `.sauce/config.yml` [See Configuring Your Espresso Tests](https://docs.saucelabs.com/mobile-apps/automated-testing/espresso-xcuitest/espresso/)

1. Run test on Saucelabs from terminal: `./gradlew app:assembleDebug && ./gradlew app:assembleAndroidTest && ./saucectl run`


## Known Issue

There is an issue running the app on JDK higher than 11.

The gradle plugin is updated with Android Studio Dolphin but if you are using older Android Studio, you can update the gradle plugin to make it work.


## Note:
```
- The application has limited functionality
- The application is intentionally inaccessible
```