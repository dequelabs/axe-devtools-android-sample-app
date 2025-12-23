/**
 * Selectors for AxeDevTools Sample App
 * Using AxeUiAutomator2 selector strategies
 */

module.exports = {
    // Bottom Navigation (Only 3 tabs: Catalog, Cart, Menu - NO Carousel in navigation)
    bottomNavigation: {
        catalog: 'android=new UiSelector().text("Catalog")',
        cart: 'android=new UiSelector().text("Cart")',
        menu: 'android=new UiSelector().text("Menu")',
        // Alternative: Using resource-id from navigation graph
        catalogById: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/catalog")',
        cartById: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/cart")',
        menuById: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/menu")',
        // Container
        bottomNavBar: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/bottom_nav_bar")'
    },

    // Menu Screen (Jetpack Compose - use text-based selectors)
    menu: {
        // Customer Profile Section
        customerLabel: 'android=new UiSelector().text("Customer")',
        customerName: 'android=new UiSelector().text("James Anderson")',
        editProfileButton: 'android=new UiSelector().description("Edit profile")',

        // Promo/Sale Section
        saleText: 'android=new UiSelector().text("45% SALE")',
        promoLabel: 'android=new UiSelector().text("Promo for you")',
        promoCode: 'android=new UiSelector().text("XT4FH33D")',

        // Menu Items
        myDetails: 'android=new UiSelector().text("My details")',
        myOrders: 'android=new UiSelector().text("My orders")',
        paymentMethods: 'android=new UiSelector().text("Payment methods")',
        addressBook: 'android=new UiSelector().text("Address book")',
        needHelp: 'android=new UiSelector().text("Need help?")',
        logOut: 'android=new UiSelector().text("Log out")',

        // Container
        composeView: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/fragment_menu_compose_view")'
    },

    // Catalog Screen
    catalog: {
        // Header
        heading: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/catalog_heading")',
        headingText: 'android=new UiSelector().text("Catalog")',
        searchButton: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/catalog_search_button")',
        searchBox: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/catalog_search_box")',

        // Filter Section
        filterRecyclerView: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/catalog_filter_rv")',
        filterText: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/filter_text")',
        // Filter options by text
        filterAll: 'android=new UiSelector().text("All")',
        filterMale: 'android=new UiSelector().text("Male")',
        filterFemale: 'android=new UiSelector().text("Female")',
        filterBoy: 'android=new UiSelector().text("Boy")',
        filterGirl: 'android=new UiSelector().text("Girl")',

        // Carousel Section
        carousel: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/catalog_carousel")',
        carouselPositionIndicators: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/catalog_carousel_pos_rv")',
        newCollectionText: 'android=new UiSelector().text("NEW COLLECTION")',

        // Categories Section
        categoriesRecyclerView: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/catalog_categories_rv")',

        // Category Item Elements
        collectionBackground: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/catalog_collection_background")',
        collectionHeading: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/catalog_collection_heading")',
        collectionDescription: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/catalog_collection_description")',
        collectionImage: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/catalog_collection_image")',

        // Category names by text
        tShirts: 'android=new UiSelector().text("T-Shirts")',
        sweatshirts: 'android=new UiSelector().text("Sweatshirts")',
        tankTops: 'android=new UiSelector().text("Tank Tops")',
        bottoms: 'android=new UiSelector().text("Bottoms")',
        accessories: 'android=new UiSelector().text("Accessories")'
    },

    // Cart Screen
    cart: {
        // Header
        heading: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/heading")',
        cartHeading: 'android=new UiSelector().text("Cart")',

        // Cart Items RecyclerView
        cartRecyclerView: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/cart_rv")',

        // Cart Item Elements (within RecyclerView items)
        cartItemBackground: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/cart_item_background")',
        cartItemImage: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/cart_item_image")',
        cartItemTitle: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/cart_item_title")',
        cartItemColor: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/cart_item_color")',
        cartItemPrice: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/cart_item_price")',
        cartItemCounter: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/cart_item_counter")',
        cartItemDecrement: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/cart_item_decrement")',
        cartItemIncrement: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/cart_item_increment")',

        // Footer Section
        deleteAllButton: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/cart_delete_all")',
        totalBackground: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/cart_total_background")',
        totalItems: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/cart_total_items")',
        totalPrice: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/cart_total_price")',
        checkoutButton: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/cart_checkout_button")',

        // Text-based selectors
        deleteAllText: 'android=new UiSelector().text("Delete all")',
        checkoutButtonText: 'android=new UiSelector().text("Proceed to checkout")',
        totalItemsText: 'android=new UiSelector().textContains("Total (")',
        grayText: 'android=new UiSelector().text("Gray")',

        // Symbols
        plusSymbol: 'android=new UiSelector().text("+")',
        minusSymbol: 'android=new UiSelector().text("-")'
    },

    // Carousel Screen (NOTE: NOT in bottom navigation - separate screen)
    carousel: {
        // Header Section
        titleAcronym: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/title_acronym")',
        titleAcronymText: 'android=new UiSelector().text("CB")',
        appTitleText: 'android=new UiSelector().text("CHIC BOUTIQUE")',

        // Description
        descriptionSentence: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/carousel_sentence")',

        // Carousel Elements
        carouselRecyclerView: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/carousel_rv")',
        carouselImage: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/carousel_image")',
        positionIndicatorRecyclerView: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/carousel_position_indicator_rv")',
        activeDot: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/active_dot")',
        inactiveDot: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/inactive_dot")',
        bottomDivider: 'android=new UiSelector().resourceId("com.deque.mobile.axedevtoolssampleapp:id/carousel_bottom_divider")'
    },

    // Common elements
    common: {
        backButton: 'android=new UiSelector().description("Navigate up")',
        scrollView: 'android=new UiSelector().className("android.widget.ScrollView")',
        recyclerView: 'android=new UiSelector().className("androidx.recyclerview.widget.RecyclerView")'
    }
};
