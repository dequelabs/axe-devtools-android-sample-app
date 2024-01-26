package com.deque.mobile.axedevtoolssampleapp

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

object MyIcons {
    val List = Icons.Rounded.List
    val Info = Icons.Rounded.Info
    val AccountBox = Icons.Default.AccountBox
    val Location = Icons.Rounded.LocationOn
    val ArrowBack = Icons.Filled.ArrowBack
    val Search = Icons.Filled.Search
    val MoreVert = Icons.Filled.MoreVert
    val Star = Icons.Filled.Star
    val Email = Icons.Filled.Email
    val Share = Icons.Filled.Share
    val Edit = Icons.Filled.Edit
    val KeyboardArrowRight = Icons.Default.KeyboardArrowRight

    val AppIcon = R.drawable.ic_launcher_background
    val Policy = R.drawable.ic_chevron_right
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class DCodeIcon {
    data class ImageVectorIcon(val imageVector: ImageVector) : DCodeIcon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : DCodeIcon()
}