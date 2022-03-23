# axe-devtools-android-sample-app

This repo contains an inaccessible fluff application for the purpose of trying out AxeDevTools Android.

In order to use this repository, clone it and replace the build.gradle (:app) BuildConfig property `AXE_DEVTOOLS_APIKEY` with your Axe Devtools API Key. If you do not have an API key, generate one on your [settings page.](https://axe.deque.com/settings)

```groovy
    buildTypes {
        ...
    
        debug {
            buildConfigField "String", "AXE_DEVTOOLS_APIKEY", "\"YOUR_API_KEY\""
            buildConfigField "java.util.concurrent.atomic.AtomicBoolean", "IS_TESTING", "new java.util.concurrent.atomic.AtomicBoolean(false)"
        }
    }
```

Once you have a working API Key installed, you can run the `ExampleEndToEndAccessibilityTest` Espresso test manually, or through automated services such as SauceLabs. This repository has a prewritten `.sauce/config.yml`, so if you have [saucectl](https://github.com/saucelabs/saucectl) installed, run `saucectl run` and you will see the Sample app end-to-end test on your SauceLabs account! 

```
Please note:
- The application has limited functionality
- The application is intentionally inaccessible
```