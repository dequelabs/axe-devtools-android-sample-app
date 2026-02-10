# Appium JavaScript Test Suite

This is a comprehensive Appium test suite written in JavaScript for the AxeDevTools Android Sample App.

## Prerequisites

Before running the tests, ensure you have the following installed:

- **Node.js** (v18.0.0 or higher)
- **npm** (v9.0.0 or higher)
- **Java Development Kit (JDK)** (version 11 or higher)
- **Android SDK** with platform tools
- **Appium** (v2.x)
- **Android Emulator** or a physical Android device

## Setup

### 1. Install Dependencies

Navigate to the `appium-tests` directory and run:

```bash
npm run setup
```

Or manually:

```bash
npm install
mkdir -p screenshots
```

### 2. Install Appium and Drivers

If you haven't installed Appium globally:

```bash
npm install -g appium
```

Install the Axe UiAutomator2 driver:

```bash
appium driver install --source=npm appium-uiautomator2-driver
```

### 3. Build the Android App

Before running tests, build the debug APK:

```bash
cd ..
./gradlew assembleDebug
```

The APK will be located at: `app/build/outputs/apk/debug/app-debug.apk`

### 4. Start Android Emulator or Connect Device

**Option A: Using Android Emulator**
```bash
emulator -avd <your_avd_name>
```

**Option B: Using Physical Device**
```bash
adb devices  # Verify device is connected
```

### 5. Update Configuration

Edit `config/wdio.conf.js` and update the following if needed:

- `appium:platformVersion` - Match your device/emulator Android version
- `appium:deviceName` - Your device/emulator name
- `appium:app` - Path to your APK (default is set correctly)

## Running Tests

### Start Appium Server

In a separate terminal, start the Appium server:

```bash
npm run appium
```

Or if installed globally:

```bash
appium
```

### Run All Tests

```bash
npm test
```

### Run Specific Test Suites

```bash
# Navigation tests
npm run test:navigation

# Menu screen tests
npm run test:menu

# Catalog screen tests
npm run test:catalog

# Cart screen tests
npm run test:cart

# Carousel screen tests
npm run test:carousel

# End-to-End tests
npm run test:e2e
```

### Run Individual Test Files

```bash
npx wdio run ./config/wdio.conf.js --spec ./test/01-navigation.spec.js
```

## Test Structure

```
appium-tests/
├── config/
│   └── wdio.conf.js          # WebdriverIO configuration
├── helpers/
│   ├── selectors.js          # Element selectors for the app
│   └── utils.js              # Utility functions
├── test/
│   ├── 01-navigation.spec.js # Navigation tests
│   ├── 02-menu.spec.js       # Menu screen tests
│   ├── 03-catalog.spec.js    # Catalog screen tests
│   ├── 04-cart.spec.js       # Cart screen tests
│   ├── 05-carousel.spec.js   # Carousel screen tests
│   └── 06-e2e.spec.js        # End-to-end tests
├── screenshots/              # Test screenshots (created during execution)
├── package.json
└── README.md
```

## Test Suites Overview

### 1. Navigation Tests (`01-navigation.spec.js`)
- Bottom navigation bar visibility
- Tab switching functionality
- State persistence during navigation
- Rapid tab switching

### 2. Menu Tests (`02-menu.spec.js`)
- Customer profile display
- Promo code information
- Menu items interaction
- Scroll functionality
- Screen rotation handling

### 3. Catalog Tests (`03-catalog.spec.js`)
- Product display
- Search functionality
- Product filtering
- Add to cart operations
- Scroll through products

### 4. Cart Tests (`04-cart.spec.js`)
- Empty cart state
- Cart items display
- Item removal
- Checkout functionality
- Cart updates

### 5. Carousel Tests (`05-carousel.spec.js`)
- Image carousel display
- Swipe gestures
- Navigation buttons
- Position indicators
- Multiple image browsing

### 6. End-to-End Tests (`06-e2e.spec.js`)
- Complete user journey
- App lifecycle events
- Multiple screen rotations
- Shopping flow simulation
- State persistence

## Screenshots

Screenshots are automatically saved to the `screenshots/` directory during test execution. Each test that takes a screenshot will save it with a descriptive name and timestamp.

## Customization

### Adding New Selectors

Edit `helpers/selectors.js` to add new element selectors:

```javascript
module.exports = {
    newScreen: {
        element: 'android=new UiSelector().text("Element Text")'
    }
};
```

### Adding New Utility Functions

Edit `helpers/utils.js` to add new helper functions:

```javascript
async function newUtilityFunction() {
    // Your code here
}

module.exports = {
    newUtilityFunction
};
```

### Creating New Test Files

Create a new test file in the `test/` directory following the existing pattern:

```javascript
const { expect } = require('chai');
const selectors = require('../helpers/selectors');
const utils = require('../helpers/utils');

describe('New Test Suite', () => {
    before(async () => {
        await utils.waitForAppReady();
    });

    it('should perform test', async () => {
        // Test code
    });
});
```

## Troubleshooting

### Appium Server Not Starting
```bash
# Kill any existing Appium processes
pkill -f appium

# Start Appium with verbose logging
appium --log-level debug
```

### Device Not Found
```bash
# Check connected devices
adb devices

# Restart ADB
adb kill-server
adb start-server
```

### Element Not Found
- Update selectors in `helpers/selectors.js`
- Use Appium Inspector to identify correct element locators
- Increase wait timeouts in `config/wdio.conf.js`

### Tests Failing
- Ensure the APK is built and path is correct
- Verify device/emulator is running
- Check Android version matches configuration
- Review screenshots in `screenshots/` directory

## Best Practices

1. **Wait for Elements**: Always wait for elements before interacting
2. **Use Descriptive Names**: Name tests clearly to describe what they do
3. **Keep Tests Independent**: Each test should be able to run independently
4. **Use Screenshots**: Take screenshots for debugging and documentation
5. **Handle Errors**: Use try-catch blocks for optional interactions
6. **Clean Up**: Reset app state between tests when necessary

## CI/CD Integration

### GitHub Actions Example

```yaml
name: Appium Tests

on: [push, pull_request]

jobs:
  test:
    runs-on: macos-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-node@v3
        with:
          node-version: '18'
      - name: Setup Android SDK
        uses: android-actions/setup-android@v2
      - name: Install dependencies
        run: |
          cd appium-tests
          npm install
      - name: Build APK
        run: ./gradlew assembleDebug
      - name: Run tests
        run: |
          cd appium-tests
          npm test
```

## Contributing

When adding new tests:
1. Follow the existing test structure
2. Add appropriate selectors to `helpers/selectors.js`
3. Update this README if adding new test suites
4. Ensure tests pass before committing

## Resources

- [Appium Documentation](http://appium.io/docs/en/latest/)
- [WebdriverIO Documentation](https://webdriver.io/docs/gettingstarted)
- [Axe UiAutomator2 Driver](https://github.com/appium/appium-uiautomator2-driver)
- [Android UI Testing](https://developer.android.com/training/testing/ui-testing)

## License

MIT
