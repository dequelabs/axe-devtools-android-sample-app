package com.deque.mobile.axedevtoolssampleapp.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deque.mobile.axedevtoolssampleapp.R

@Composable
@Preview
fun Menu() {
    Surface(modifier = Modifier.background(color = Color.White)) {
        Column {
            CustomerName()
            SaleInfoBox()
            Spacer(modifier = Modifier.height(24.dp))
            MenuItem(icon = R.drawable.ic_user, text = R.string.my_details)
            MenuItem(icon = R.drawable.ic_note, text = R.string.my_orders)
            MenuItem(icon = R.drawable.ic_wallet, text = R.string.payment_methods)
            MenuItem(icon = R.drawable.ic_map_marker, text = R.string.address_book)
            MenuItem(icon = R.drawable.ic_map_marker, text = R.string.address_book)
            Spacer(modifier = Modifier.height(16.dp))
            MenuItem(icon = R.drawable.ic_info, text = R.string.need_help)
            LogOutButton()
        }
    }
}

@Composable
private fun SaleInfoBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(20.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.promo_background),
            contentDescription = stringResource(
                id = R.string.dancing_woman_on_purple_background
            )
        )
        Text(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .padding(end = 24.dp),
            text = stringResource(id = R.string._45_sale),
            textAlign = TextAlign.End,
            style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold)
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .padding(start = 24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.promo_for_you),
                style = TextStyle(color = Color.White)
            )
            Text(
                text = stringResource(id = R.string.promo_code),
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
        }
    }
}

@Composable
private fun CustomerName() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .padding(8.dp),
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = null
        )
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(text = stringResource(id = R.string.customer))
            Text(
                text = stringResource(id = R.string.james_anderson),
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(25.dp),
            painter = painterResource(id = R.drawable.ic_edit),
            contentDescription = stringResource(id = R.string.edit_profile)
        )
    }
}

@Composable
private fun MenuItem(@DrawableRes icon: Int, @StringRes text: Int) {
    val edgeMargin = 16.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = edgeMargin, end = edgeMargin)
    ) {
        Icon(
            modifier = Modifier.align(Alignment.CenterVertically),
            painter = painterResource(id = icon),
            contentDescription = stringResource(
                id = R.string.my_details_icon
            )
        )
        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically), text = stringResource(id = text)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier.align(Alignment.CenterVertically),
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = stringResource(
                id = R.string.right_arrow
            )
        )
    }
}

@Composable
private fun LogOutButton() {
    val edgeMargin = 16.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = edgeMargin, end = edgeMargin)
    ) {
        Icon(
            modifier = Modifier.align(Alignment.CenterVertically),
            painter = painterResource(id = R.drawable.ic_logout),
            tint = Color.Red,
            contentDescription = stringResource(
                id = R.string.my_details_icon
            )
        )
        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically),
            text = stringResource(id = R.string.log_out),
            style = TextStyle(color = Color.Red)
        )
    }
}