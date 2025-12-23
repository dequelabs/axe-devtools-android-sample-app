const path = require('path');

exports.config = {
    runner: 'local',
    port: 4723,

    specs: [
        path.join(__dirname, '../test/**/*.spec.js')
    ],

    exclude: [],

    maxInstances: 1,

    capabilities: [{
        platformName: 'Android',
        'appium:platformVersion': '16', // Android API 36 = Android 16
        'appium:deviceName': 'Android Emulator',
        'appium:automationName': 'AxeUiAutomator2',
        'appium:app': path.join(__dirname, '../../app/build/outputs/apk/debug/app-debug.apk'),
        'appium:appPackage': 'com.deque.mobile.axedevtoolssampleapp',
        'appium:appActivity': '.MainActivity',
        'appium:noReset': false,
        'appium:fullReset': false,
        'appium:newCommandTimeout': 240,
        'appium:uiautomator2ServerInstallTimeout': 60000
    }],

    logLevel: 'info',

    bail: 0,

    baseUrl: 'http://localhost',

    waitforTimeout: 10000,

    connectionRetryTimeout: 120000,

    connectionRetryCount: 3,

    services: ['appium'],

    framework: 'mocha',

    reporters: ['spec'],

    mochaOpts: {
        ui: 'bdd',
        timeout: 60000
    },

    autoCompileOpts: {
        autoCompile: false
    },

    // Hooks
    before: function (capabilities, specs) {
        console.log('Starting test suite...');
    },

    after: function (result, capabilities, specs) {
        console.log('Test suite completed');
    },

    beforeTest: function (test, context) {
        console.log(`Starting test: ${test.title}`);
    },

    afterTest: function (test, context, { error, result, duration, passed, retries }) {
        if (error) {
            console.log(`Test failed: ${test.title}`);
        } else {
            console.log(`Test passed: ${test.title}`);
        }
    }
};
