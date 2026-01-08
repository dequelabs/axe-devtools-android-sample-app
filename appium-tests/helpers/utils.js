/**
 * Utility functions for Appium tests
 */

const selectors = require('./selectors');

/**
 * Wait for an element to be displayed
 * @param {string} selector - The element selector
 * @param {number} timeout - Timeout in milliseconds (default: 10000)
 */
async function waitForElement(selector, timeout = 10000) {
    const element = await $(selector);
    await element.waitForDisplayed({ timeout });
    return element;
}

/**
 * Click on an element with wait
 * @param {string} selector - The element selector
 */
async function clickElement(selector) {
    const element = await waitForElement(selector);
    await element.click();
}

/**
 * Get text from an element
 * @param {string} selector - The element selector
 * @returns {Promise<string>} The element text
 */
async function getElementText(selector) {
    const element = await waitForElement(selector);
    return await element.getText();
}

/**
 * Check if element exists
 * @param {string} selector - The element selector
 * @returns {Promise<boolean>} True if element exists
 */
async function isElementDisplayed(selector) {
    try {
        const element = await $(selector);
        return await element.isDisplayed();
    } catch (error) {
        return false;
    }
}

/**
 * Scroll to element
 * @param {string} selector - The element selector
 */
async function scrollToElement(selector) {
    const element = await $(selector);
    await driver.execute('mobile: scrollTo', { element: element.elementId });
}

/**
 * Swipe on screen using modern mobile: swipeGesture command
 * @param {string} direction - Direction: 'up', 'down', 'left', 'right'
 */
async function swipe(direction) {
    const { width, height } = await driver.getWindowSize();

    // Define swipe parameters based on direction
    // Parameters: left, top, width, height define the swipe area rectangle
    // direction and percent define the swipe within that area
    let params;

    switch (direction) {
        case 'up':
            params = {
                left: Math.floor(width * 0.1),
                top: Math.floor(height * 0.2),
                width: Math.floor(width * 0.8),
                height: Math.floor(height * 0.6),
                direction: 'up',
                percent: 0.75
            };
            break;
        case 'down':
            params = {
                left: Math.floor(width * 0.1),
                top: Math.floor(height * 0.2),
                width: Math.floor(width * 0.8),
                height: Math.floor(height * 0.6),
                direction: 'down',
                percent: 0.75
            };
            break;
        case 'left':
            params = {
                left: Math.floor(width * 0.2),
                top: Math.floor(height * 0.1),
                width: Math.floor(width * 0.6),
                height: Math.floor(height * 0.8),
                direction: 'left',
                percent: 0.75
            };
            break;
        case 'right':
            params = {
                left: Math.floor(width * 0.2),
                top: Math.floor(height * 0.1),
                width: Math.floor(width * 0.6),
                height: Math.floor(height * 0.8),
                direction: 'right',
                percent: 0.75
            };
            break;
        default:
            throw new Error(`Invalid swipe direction: ${direction}`);
    }

    // Use the modern mobile: swipeGesture command
    await driver.execute('mobile: swipeGesture', params);
}

/**
 * Navigate to a tab using bottom navigation
 * @param {string} tab - Tab name: 'catalog', 'cart', 'carousel', 'menu'
 */
async function navigateToTab(tab) {
    const tabSelector = selectors.bottomNavigation[tab];
    if (!tabSelector) {
        throw new Error(`Invalid tab name: ${tab}`);
    }
    await clickElement(tabSelector);
    await driver.pause(1000); // Wait for navigation animation
}

/**
 * Take a screenshot with a custom name
 * @param {string} name - Screenshot name
 */
async function takeScreenshot(name) {
    const timestamp = new Date().toISOString().replace(/:/g, '-');
    const filename = `${name}_${timestamp}.png`;
    await driver.saveScreenshot(`./screenshots/${filename}`);
    console.log(`Screenshot saved: ${filename}`);
}

/**
 * Wait for app to be ready
 */
async function waitForAppReady() {
    await driver.pause(2000);
    // Wait for bottom navigation to be visible
    await waitForElement(selectors.bottomNavigation.menu, 15000);
}

/**
 * Get all elements matching selector
 * @param {string} selector - The element selector
 * @returns {Promise<Array>} Array of elements
 */
async function getElements(selector) {
    return await $$(selector);
}

/**
 * Perform long press on element using modern mobile: longClickGesture command
 * @param {string} selector - The element selector
 * @param {number} duration - Press duration in milliseconds (default: 2000)
 */
async function longPress(selector, duration = 2000) {
    const element = await waitForElement(selector);
    const location = await element.getLocation();
    const size = await element.getSize();

    // Use the modern mobile: longClickGesture command
    await driver.execute('mobile: longClickGesture', {
        x: location.x + size.width / 2,
        y: location.y + size.height / 2,
        duration: duration
    });
}

/**
 * Rotate screen orientation
 * @param {string} orientation - Orientation: 'PORTRAIT' or 'LANDSCAPE'
 */
async function rotateScreen(orientation) {
    try {
        // Try using modern Appium 2.0 approach
        const orientationValue = orientation.toUpperCase() === 'LANDSCAPE' ? 'LANDSCAPE' : 'PORTRAIT';
        await driver.setOrientation(orientationValue);
    } catch (error) {
        console.log(`Note: setOrientation may be deprecated but still functional: ${error.message}`);
        // If it fails, we can optionally implement alternative methods here
        // For now, just let it pass as some drivers still support it
    }
}

/**
 * Perform Axe accessibility scan on the current screen and upload to QA Dashboard
 * @param {string} screenName - Name of the screen being scanned (for logging)
 * @returns {Promise<Object>} Axe scan results
 */
async function axeScan(screenName = 'current screen') {
    try {
        console.log(`Running Axe accessibility scan on ${screenName}...`);

        // Execute axe scan using the Axe DevTools driver
        const results = await driver.execute('axe:scan');

        const violationCount = results.violations ? results.violations.length : 0;
        const passCount = results.passes ? results.passes.length : 0;

        console.log(`Axe scan completed for ${screenName}:`);
        console.log(`  - Violations: ${violationCount}`);
        console.log(`  - Passes: ${passCount}`);

        if (violationCount > 0) {
            console.log(`  ⚠️  Found ${violationCount} accessibility violations`);
            results.violations.forEach((violation, index) => {
                console.log(`    ${index + 1}. ${violation.id}: ${violation.description}`);
            });
        } else {
            console.log(`  ✓ No accessibility violations found`);
        }

        // Upload results to Axe DevTools QA Dashboard
        try {
            console.log(`Uploading scan results to Axe DevTools Dashboard...`);
            await driver.execute('axe:uploadResults', results, screenName);
            console.log(`  ✓ Results uploaded successfully`);
        } catch (uploadError) {
            console.log(`  ⚠️  Failed to upload results: ${uploadError.message}`);
        }

        return results;
    } catch (error) {
        console.log(`Warning: Could not perform axe scan on ${screenName}: ${error.message}`);
        return null;
    }
}

module.exports = {
    waitForElement,
    clickElement,
    getElementText,
    isElementDisplayed,
    scrollToElement,
    swipe,
    navigateToTab,
    takeScreenshot,
    waitForAppReady,
    getElements,
    longPress,
    rotateScreen,
    axeScan
};
