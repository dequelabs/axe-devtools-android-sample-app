# axe-devtools-android-sample-app

This repo contains an inaccessible sample application to try out axeDevTools for Android.

To use this repository, clone and set an environment variable named _`AXE_DEVTOOLS_APIKEY`_ with your Axe Devtools API Key. If you do not have an API key, generate one on your [settings page.](https://axe.deque.com/settings)

If you don't have an account, sign up for a [free axe DevTools Mobile 14-day trial!][11]

&nbsp;

Once you have a working API Key installed, you can run the [_`ExampleEndToEndAccessibilityTest`_][31] Espresso test manually, or through automated services such as Sauce Labs.

Just install [saucectl][20] and set your _`SAUCE_USERNAME`_ and _`SAUCE_ACCESS_KEY`_ as environment variables. The default configuration is already defined in [.sauce/config.yaml][30], so everything is now set to run _`saucectl run`_.

If you don't have an account, sign up for a [free Sauce Labs 28-day trial!][21]


[10]: https://axe.deque.com/settings
[11]: https://axe.deque.com/axe-devtools-mobile/get-started

[20]: https://github.com/saucelabs/saucectl
[21]: https://saucelabs.com/sign-up

[30]: .sauce/config.yml
[31]: app/src/androidTest/java/com/deque/mobile/axedevtoolssampleapp/ExampleEndToEndAccessibilityTest.kt

&nbsp;
&nbsp;

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



&nbsp;
&nbsp;

```
Please note:
- The application has limited functionality
- The application is intentionally inaccessible
```