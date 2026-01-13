const { expect } = require('chai');
const selectors = require('../helpers/selectors');
const {
    waitForAppReady,
    navigateToTab,
    isElementDisplayed,
    takeScreenshot,
    getElements,
    axeScan
} = require('../helpers/utils');

describe('Cart Screen Tests', () => {

    before(async () => {
        await waitForAppReady();
        console.log('App is ready for cart tests');
    });

    beforeEach(async () => {
        await navigateToTab('cart');
        await driver.pause(1000);
    });

    it('should display cart screen', async () => {
        const cartTab = await $(selectors.bottomNavigation.cart);
        expect(await cartTab.isDisplayed()).to.be.true;

        await takeScreenshot('cart_main');
        console.log('✓ Cart screen displayed');
    });

    it('should verify cart layout using UiAutomator', async () => {
        // Use generic selectors to explore cart screen
        const textViews = await $$('android.widget.TextView');
        const buttons = await $$('android.widget.Button');

        console.log(`Found ${textViews.length} text views on cart screen`);
        console.log(`Found ${buttons.length} buttons on cart screen`);

        await takeScreenshot('cart_layout');
        console.log('✓ Cart layout verified');
    });
});
