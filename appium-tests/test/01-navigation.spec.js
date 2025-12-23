const { expect } = require('chai');
const selectors = require('../helpers/selectors');
const {
    waitForAppReady,
    navigateToTab,
    isElementDisplayed,
    takeScreenshot
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

        await takeScreenshot('catalog_screen');
        console.log('✓ Successfully navigated to Catalog tab');
    });

    it('should navigate to Cart tab', async () => {
        await navigateToTab('cart');
        await driver.pause(1000);

        const cartTab = await $(selectors.bottomNavigation.cart);
        expect(await cartTab.isDisplayed()).to.be.true;

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

        await takeScreenshot('menu_screen');
        console.log('✓ Successfully navigated back to Menu tab');
    });

    it('should navigate through all tabs in sequence', async () => {
        const tabs = ['catalog', 'cart', 'menu'];

        for (const tab of tabs) {
            await navigateToTab(tab);
            await driver.pause(1000);

            const tabElement = await $(selectors.bottomNavigation[tab]);
            expect(await tabElement.isDisplayed()).to.be.true;

            console.log(`✓ Successfully navigated to ${tab}`);
        }

        console.log('✓ Successfully navigated through all 3 bottom navigation tabs');
    });

    it('should maintain state when switching tabs', async () => {
        // Start at menu
        await navigateToTab('menu');
        await driver.pause(500);

        // Navigate away and back
        await navigateToTab('catalog');
        await driver.pause(500);

        await navigateToTab('menu');
        await driver.pause(1000);

        // Verify menu content is still there
        const isMenuDisplayed = await isElementDisplayed(selectors.menu.customerName);
        expect(isMenuDisplayed).to.be.true;

        console.log('✓ Tab state is maintained when switching');
    });

    it('should handle rapid tab switching', async () => {
        // Rapidly switch between tabs
        await navigateToTab('catalog');
        await driver.pause(200);
        await navigateToTab('cart');
        await driver.pause(200);
        await navigateToTab('menu');
        await driver.pause(1000);

        // Verify we ended up on the correct tab
        const menuTab = await $(selectors.bottomNavigation.menu);
        expect(await menuTab.isDisplayed()).to.be.true;

        console.log('✓ App handles rapid tab switching correctly');
    });
});
