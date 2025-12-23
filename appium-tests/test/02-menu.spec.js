const { expect } = require('chai');
const selectors = require('../helpers/selectors');
const {
    waitForAppReady,
    navigateToTab,
    takeScreenshot
} = require('../helpers/utils');

describe('Menu Screen Tests', () => {

    before(async () => {
        await waitForAppReady();
        console.log('App is ready for menu tests');
    });

    beforeEach(async () => {
        // Navigate to menu tab before each test
        await navigateToTab('menu');
        await driver.pause(1000);
    });

    it('should display customer profile information', async () => {
        const customerName = await $(selectors.menu.customerName);
        expect(await customerName.isDisplayed()).to.be.true;

        const nameText = await customerName.getText();
        expect(nameText).to.equal('James Anderson');

        await takeScreenshot('customer_profile');
        console.log('✓ Customer profile displayed correctly');
    });

    it('should display sale information banner', async () => {
        const saleText = await $(selectors.menu.saleText);
        const isSaleDisplayed = await saleText.isDisplayed();
        expect(isSaleDisplayed).to.be.true;

        await takeScreenshot('sale_banner');
        console.log('✓ Sale banner displayed correctly');
    });
});
