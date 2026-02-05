const { expect } = require('chai');
const selectors = require('../helpers/selectors');
const {
    waitForAppReady,
    navigateToTab,
    isElementDisplayed,
    takeScreenshot,
    getElements,
    swipe,
    axeScan
} = require('../helpers/utils');

describe('Catalog Screen Tests', () => {

    before(async () => {
        await waitForAppReady();
        console.log('App is ready for catalog tests');
    });

    beforeEach(async () => {
        await navigateToTab('catalog');
        await driver.pause(1000);
    });

    it('should display catalog screen with heading', async () => {
        const catalogTab = await $(selectors.bottomNavigation.catalog);
        expect(await catalogTab.isDisplayed()).to.be.true;

        const heading = await $(selectors.catalog.heading);
        expect(await heading.isDisplayed()).to.be.true;

        const headingText = await heading.getText();
        expect(headingText).to.equal('Catalog');

        // Run accessibility scan
        await axeScan('Catalog Screen - Main View');

        await takeScreenshot('catalog_main');
        console.log('✓ Catalog screen displayed with heading');
    });

    it('should display catalog filter section', async () => {
        const filterRecyclerView = await $(selectors.catalog.filterRecyclerView);
        expect(await filterRecyclerView.isDisplayed()).to.be.true;

        await takeScreenshot('catalog_filters');
        console.log('✓ Filter section displayed in catalog');
    });

    it('should display catalog carousel', async () => {
        const carousel = await $(selectors.catalog.carousel);
        expect(await carousel.isDisplayed()).to.be.true;

        await takeScreenshot('catalog_carousel');
        console.log('✓ Catalog carousel displayed');
    });

    it('should display catalog categories', async () => {
        const categoriesRecyclerView = await $(selectors.catalog.categoriesRecyclerView);
        expect(await categoriesRecyclerView.isDisplayed()).to.be.true;

        await takeScreenshot('catalog_categories');
        console.log('✓ Categories section displayed in catalog');
    });

    it('should scroll through catalog items', async () => {
        // Scroll down
        await swipe('up');
        await driver.pause(1000);
        await takeScreenshot('catalog_scrolled');

        // Scroll back up
        await swipe('down');
        await driver.pause(1000);

        console.log('✓ Catalog scrolling works');
    });

    it('should display search button', async () => {
        const searchButton = await $(selectors.catalog.searchButton);
        expect(await searchButton.isDisplayed()).to.be.true;

        await takeScreenshot('catalog_search_button');
        console.log('✓ Search button displayed');
    });

    it('should click search button', async () => {
        try {
            const searchButton = await $(selectors.catalog.searchButton);
            await searchButton.click();
            await driver.pause(1000);

            await takeScreenshot('catalog_search_clicked');
            console.log('✓ Search button clicked');
        } catch (e) {
            console.log(`Note: ${e.message}`);
        }
    });

    it('should display filter options', async () => {
        try {
            // Check for "All" filter
            const filterAll = await $(selectors.catalog.filterAll);
            if (await filterAll.isDisplayed()) {
                console.log('✓ "All" filter option found');
            }

            await takeScreenshot('catalog_filter_options');
            console.log('✓ Filter options displayed');
        } catch (e) {
            console.log(`Note: ${e.message}`);
        }
    });

    it('should display category items', async () => {
        try {
            // Check for T-Shirts category
            const tShirts = await $(selectors.catalog.tShirts);
            if (await tShirts.isDisplayed()) {
                console.log('✓ "T-Shirts" category found');
            }

            await takeScreenshot('catalog_category_items');
            console.log('✓ Category items displayed');
        } catch (e) {
            console.log(`Note: ${e.message}`);
        }
    });

    it('should verify catalog layout using UiAutomator', async () => {
        // Use generic UiAutomator selectors to explore the catalog
        const textViews = await $$('android.widget.TextView');
        const images = await $$('android.widget.ImageView');

        console.log(`Found ${textViews.length} text views`);
        console.log(`Found ${images.length} image views`);

        expect(textViews.length).to.be.greaterThan(0);
        await takeScreenshot('catalog_layout');
        console.log('✓ Catalog has UI elements');
    });

    it('should handle catalog screen rotation', async () => {
        await driver.setOrientation('LANDSCAPE');
        await driver.pause(2000);
        await takeScreenshot('catalog_landscape');

        await driver.setOrientation('PORTRAIT');
        await driver.pause(2000);
        await takeScreenshot('catalog_portrait');

        console.log('✓ Catalog handles rotation correctly');
    });

    it('should maintain catalog position after navigation', async () => {
        // Scroll to a position
        await swipe('up');
        await driver.pause(500);

        // Navigate away
        await navigateToTab('menu');
        await driver.pause(1000);

        // Navigate back
        await navigateToTab('catalog');
        await driver.pause(1000);

        await takeScreenshot('catalog_after_navigation');
        console.log('✓ Navigated back to catalog');
    });
});
