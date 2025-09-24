package com.deque.mobile.axedevtoolssampleapp.ui

import kotlinx.serialization.Serializable

@Serializable
sealed class Destinations {
    @Serializable
    object Catalog : Destinations()
    @Serializable
    object Cart : Destinations()
    @Serializable
    object Menu : Destinations()
}