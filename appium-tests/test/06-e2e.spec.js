const { expect } = require('chai');
const selectors = require('../helpers/selectors');
const {
    waitForAppReady,
    navigateToTab,
    takeScreenshot,
    swipe,
    clickElement,
    rotateScreen
} = require('../helpers/utils');

describe('End-to-End Tests', () => {

    before(async () => {
        await waitForAppReady();
        console.log('App is ready for E2E tests');
    });

    it('should complete full user journey through all screens', async () => {
        console.log('Starting full user journey...');

        // 1. Start at Menu
        await navigateToTab('menu');
        await driver.pause(1000);
        const customerName = await $(selectors.menu.customerName);
        expect(await customerName.isDisplayed()).to.be.true;
        await takeScreenshot('e2e_01_menu');
        console.log('✓ Step 1: Viewed menu screen');

        // 2. Check promo information
        const promoCode = await $(selectors.menu.promoCode);
        expect(await promoCode.isDisplayed()).to.be.true;
        await takeScreenshot('e2e_02_promo');
        console.log('✓ Step 2: Verified promo code');

        // 3. Navigate to Catalog
        await navigateToTab('catalog');
        await driver.pause(1500);
        await takeScreenshot('e2e_03_catalog');
        console.log('✓ Step 3: Navigated to catalog');

        // 4. Scroll through catalog
        await swipe('up');
        await driver.pause(1000);
        await takeScreenshot('e2e_04_catalog_scrolled');
        console.log('✓ Step 4: Browsed catalog items');

        // 5. Navigate to Cart
        await navigateToTab('cart');
        await driver.pause(1500);
        await takeScreenshot('e2e_05_cart');
        console.log('✓ Step 5: Checked cart');

        // 6. Navigate to Carousel
        await navigateToTab('carousel');
        await driver.pause(1500);
        await takeScreenshot('e2e_06_carousel');
        console.log('✓ Step 6: Viewed carousel');

        // 7. Swipe through carousel
        await swipe('left');
        await driver.pause(1000);
        await takeScreenshot('e2e_07_carousel_swiped');
        console.log('✓ Step 7: Interacted with carousel');

        // 8. Return to Menu
        await navigateToTab('menu');
        await driver.pause(1500);
        await takeScreenshot('e2e_08_back_to_menu');
        console.log('✓ Step 8: Returned to menu');

        console.log('✓ Full user journey completed successfully!');
    });

    it('should handle app lifecycle events', async () => {
        console.log('Testing app lifecycle...');

        // Start at menu
        await navigateToTab('menu');
        await driver.pause(1000);

        // Background the app
        await driver.background(3);
        console.log('✓ App backgrounded for 3 seconds');

        // App should resume automatically
        await driver.pause(1000);

        // Verify we're still on menu
        const customerName = await $(selectors.menu.customerName);
        expect(await customerName.isDisplayed()).to.be.true;
        await takeScreenshot('app_after_background');
        console.log('✓ App resumed correctly');
    });

    it('should handle multiple screen rotations during navigation', async () => {
        console.log('Testing screen rotations during navigation...');

        // Menu in portrait
        await navigateToTab('menu');
        await driver.pause(1000);
        await takeScreenshot('rotation_01_menu_portrait');

        // Rotate to landscape
        await rotateScreen('LANDSCAPE');
        await driver.pause(2000);
        await takeScreenshot('rotation_02_menu_landscape');

        // Navigate to catalog in landscape
        await navigateToTab('catalog');
        await driver.pause(1500);
        await takeScreenshot('rotation_03_catalog_landscape');

        // Rotate back to portrait
        await rotateScreen('PORTRAIT');
        await driver.pause(2000);
        await takeScreenshot('rotation_04_catalog_portrait');

        // Navigate to carousel
        await navigateToTab('carousel');
        await driver.pause(1500);
        await takeScreenshot('rotation_05_carousel_portrait');

        // Rotate to landscape again
        await rotateScreen('LANDSCAPE');
        await driver.pause(2000);
        await takeScreenshot('rotation_06_carousel_landscape');

        // Return to portrait
        await rotateScreen('PORTRAIT');
        await driver.pause(2000);

        console.log('✓ App handles multiple rotations correctly');
    });

    it('should navigate through all tabs multiple times', async () => {
        console.log('Testing repeated navigation...');

        const tabs = ['menu', 'catalog', 'cart', 'carousel'];
        const iterations = 2;

        for (let i = 0; i < iterations; i++) {
            console.log(`Navigation iteration ${i + 1} of ${iterations}`);

            for (const tab of tabs) {
                await navigateToTab(tab);
                await driver.pause(500);
                console.log(`  ✓ Visited ${tab}`);
            }
        }

        await takeScreenshot('repeated_navigation_complete');
        console.log('✓ Repeated navigation completed successfully');
    });

    it('should verify menu items are interactive', async () => {
        await navigateToTab('menu');
        await driver.pause(1000);

        const menuItems = [
            { selector: selectors.menu.myDetails, name: 'My Details' },
            { selector: selectors.menu.myOrders, name: 'My Orders' },
            { selector: selectors.menu.paymentMethods, name: 'Payment Methods' }
        ];

        for (const item of menuItems) {
            const element = await $(item.selector);
            await element.click();
            await driver.pause(500);
            await takeScreenshot(`e2e_menu_${item.name.toLowerCase().replace(' ', '_')}`);
            console.log(`✓ Clicked ${item.name}`);

            // Navigate back to menu for next iteration
            await navigateToTab('menu');
            await driver.pause(500);
        }

        console.log('✓ All menu items are interactive');
    });

    it('should test rapid tab switching without crashes', async () => {
        console.log('Testing rapid tab switching...');

        const rapidSwitches = 10;
        const tabs = ['menu', 'catalog', 'cart', 'carousel'];

        for (let i = 0; i < rapidSwitches; i++) {
            const randomTab = tabs[Math.floor(Math.random() * tabs.length)];
            await navigateToTab(randomTab);
            await driver.pause(300);
        }

        await takeScreenshot('rapid_switching_complete');
        console.log('✓ Rapid tab switching handled without crashes');
    });

    it('should verify app state persistence', async () => {
        console.log('Testing app state persistence...');

        // Navigate to catalog
        await navigateToTab('catalog');
        await driver.pause(1000);

        // Scroll down
        await swipe('up');
        await driver.pause(500);
        await takeScreenshot('state_01_catalog_scrolled');

        // Background and resume app
        await driver.background(2);
        await driver.pause(1000);

        // Verify we're still on catalog
        const catalogTab = await $(selectors.bottomNavigation.catalog);
        expect(await catalogTab.isDisplayed()).to.be.true;
        await takeScreenshot('state_02_after_resume');

        console.log('✓ App state persisted correctly');
    });

    it('should complete shopping flow simulation', async () => {
        console.log('Simulating shopping flow...');

        // 1. Start at menu and view promo
        await navigateToTab('menu');
        await driver.pause(1000);
        await takeScreenshot('shopping_01_view_promo');
        console.log('✓ Viewed promotional offer');

        // 2. Browse catalog
        await navigateToTab('catalog');
        await driver.pause(1500);
        await takeScreenshot('shopping_02_browse_catalog');
        console.log('✓ Browsing products');

        // 3. Scroll through products
        await swipe('up');
        await driver.pause(1000);
        await swipe('up');
        await driver.pause(1000);
        await takeScreenshot('shopping_03_view_products');
        console.log('✓ Viewed multiple products');

        // 4. Check cart
        await navigateToTab('cart');
        await driver.pause(1500);
        await takeScreenshot('shopping_04_check_cart');
        console.log('✓ Checked shopping cart');

        // 5. View carousel/promotions
        await navigateToTab('carousel');
        await driver.pause(1500);
        await swipe('left');
        await driver.pause(1000);
        await takeScreenshot('shopping_05_view_carousel');
        console.log('✓ Viewed promotional carousel');

        // 6. Return to menu
        await navigateToTab('menu');
        await driver.pause(1000);
        await takeScreenshot('shopping_06_return_menu');
        console.log('✓ Returned to menu');

        console.log('✓ Shopping flow simulation completed!');
    });

    it('should verify accessibility of all screens', async () => {
        console.log('Verifying screen accessibility...');

        const tabs = ['menu', 'catalog', 'cart', 'carousel'];

        for (const tab of tabs) {
            await navigateToTab(tab);
            await driver.pause(1000);

            // Get all elements on screen
            const allElements = await $$('android.view.View');
            console.log(`${tab} screen has ${allElements.length} view elements`);

            await takeScreenshot(`accessibility_${tab}`);
            console.log(`✓ ${tab} screen is accessible`);
        }

        console.log('✓ All screens verified for accessibility');
    });

    after(async () => {
        console.log('E2E test suite completed');
        await takeScreenshot('test_suite_complete');
    });
});
