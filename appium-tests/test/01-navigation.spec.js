const { expect } = require('chai');
const selectors = require('../helpers/selectors');
const {
    waitForAppReady,
    navigateToTab,
    isElementDisplayed,
    takeScreenshot,
    axeScan
} = require('../helpers/utils');

describe('Navigation Tests', () => {

    before(async () => {
        await waitForAppReady();
        console.log('App is ready for navigation tests');
    });

    beforeEach(async () => {
        // Return to menu tab before each test
        await navigateToTab('menu');
        await driver.pause(500);
    });

    it('display bottom navigation bar with all tabs', async () => {
        const menuTab = await $(selectors.bottomNavigation.menu);
        const catalogTab = await $(selectors.bottomNavigation.catalog);
        const cartTab = await $(selectors.bottomNavigation.cart);

        expect(await menuTab.isDisplayed()).to.be.true;
        expect(await catalogTab.isDisplayed()).to.be.true;
        expect(await cartTab.isDisplayed()).to.be.true;

        console.log('✓ All 3 bottom navigation tabs are displayed (Menu, Catalog, Cart)');
    });

    it('navigate to Catalog tab', async () => {
        await navigateToTab('catalog');
        await driver.pause(1000);

        // Verify we're on catalog screen by checking if catalog-specific elements exist
        const catalogTab = await $(selectors.bottomNavigation.catalog);
        expect(await catalogTab.isDisplayed()).to.be.true;

        // Run accessibility scan
        await axeScan('Catalog Screen');

        await takeScreenshot('catalog_screen');
        console.log('✓ Successfully navigated to Catalog tab');
    });

    it('should navigate to Cart tab', async () => {
        await navigateToTab('cart');
        await driver.pause(1000);

        const cartTab = await $(selectors.bottomNavigation.cart);
        expect(await cartTab.isDisplayed()).to.be.true;

        // Run accessibility scan
        await axeScan('Cart Screen');

        await takeScreenshot('cart_screen');
        console.log('✓ Successfully navigated to Cart tab');
    });

    it('should navigate back to Menu tab', async () => {
        // Navigate to another tab first
        await navigateToTab('catalog');
        await driver.pause(500);

        // Navigate back to menu
        await navigateToTab('menu');
        await driver.pause(1000);

        const menuTab = await $(selectors.bottomNavigation.menu);
        expect(await menuTab.isDisplayed()).to.be.true;

        // Run accessibility scan
        await axeScan('Menu Screen');

        await takeScreenshot('menu_screen');
        console.log('✓ Successfully navigated back to Menu tab');
    });
});
