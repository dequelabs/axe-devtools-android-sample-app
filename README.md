# axe-devtools-android-sample-app

This repo contains an inaccessible sample application to try out axeDevTools for Android.

## Setup with AxeDevTools

To use this repository, clone it and set your environment variable `AXE_DEVTOOLS_APIKEY` with your Axe Devtools API Key. If you do not have an API key, generate one on your [settings page.](https://axe.deque.com/settings)  Once you have a working API Key installed, you can run the `ExampleEndToEndAccessibilityTest` Espresso test manually, or through automated services such as SauceLabs. 

## Setup with SauceLabs

- Install saucectl command-line interface. [See Using the saucectl CLI](https://docs.saucelabs.com/dev/cli/saucectl/)

- Add your Sauce Labs credentials to your .bash_profile or .zshenv. Be sure to load the changes by running source .filename. [See Associate your Credentials](https://docs.saucelabs.com/dev/cli/saucectl/#associate-your-credentials)

- Update the prewritten `.sauce/config.yml` [See Configuring Your Espresso Tests](https://docs.saucelabs.com/mobile-apps/automated-testing/espresso-xcuitest/espresso/)


Use this shell script to run Sample app end-to-end automated test on SauceLabs.  You will need to fill in Axe Devtools API key and SauceLabs keys.

```shell
# set environment
export AXE_DEVTOOLS_APIKEY=""
export SAUCE_USERNAME=""
export SAUCE_ACCESSS_KEY=""

# build debug and test app
./gradlew app:assembleDebug 
./gradlew app:assembleAndroidTest

# run test
saucectl run
```

## Known Issue

There is an issue running the app on JDK higher than 11.

The gradle plugin is updated with Android Studio Dolphin but if you are using older Android Studio, you can update the gradle plugin to make it work.

## Note:
```
- The application has limited functionality
- The application is intentionally inaccessible
```