package com.deque.mobile.axedevtoolssampleapp.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
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
    var displayDialog by rememberSaveable { mutableStateOf(false) }
    var selectedPaymentMethod by rememberSaveable { mutableStateOf(PaymentMethod.CASH) }

    Surface(modifier = Modifier.background(color = Color.White)) {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            AccessibleHeading(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            CustomerName()
            SaleInfoBox()
            Text(
                modifier = Modifier
                    .semantics {
                        heading()
                    },
                text = "Profile Settings",
                fontSize = 20.sp
            )
            MenuItem(icon = R.drawable.ic_user, text = R.string.my_details) {}
            MenuItem(icon = R.drawable.ic_note, text = R.string.my_orders) {}
            MenuItem(icon = R.drawable.ic_wallet, text = R.string.payment_methods) {
                displayDialog = true
            }
            MenuItem(icon = R.drawable.ic_map_marker, text = R.string.address_book) {}
            MenuItem(icon = R.drawable.ic_map_marker, text = R.string.address_book) {}
            Spacer(modifier = Modifier.height(16.dp))
            MenuItem(icon = R.drawable.ic_info, text = R.string.need_help) {}
            LogOutButton()
        }

        if (displayDialog) {
            AlertDialog(
                onDismissRequest = {
                    displayDialog = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            displayDialog = false
                        }
                    ) {
                        Text("Accept")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            displayDialog = false
                        }
                    ) {
                        Text("Decline")
                    }
                },
                title = {
                    Text(
                        modifier = Modifier.semantics {
                            heading()
                        },
                        text = "Update Payment Method"
                    )
                },
                text = {
                    AccessibleRadioGroup(
                        selectedPaymentMethod = selectedPaymentMethod,
                        onPaymentMethodSelected = { selectedPaymentMethod = it }
                    )
                }
            )
        }
    }
}

@Composable
private fun AccessibleHeading(
    modifier: Modifier
) {
    Text(
        modifier = modifier
            .padding(top = 24.dp)
            .semantics {
                heading()
            },
        text = "Profile",
        fontSize = 26.sp
    )
}

@Composable
private fun InaccessibleHeading(
    modifier: Modifier
) {
    Text(
        modifier = modifier
            .padding(top = 24.dp),
        text = "Profile",
        fontSize = 26.sp
    )
}

@Composable
private fun AccessibleRadioGroup(
    selectedPaymentMethod: PaymentMethod,
    onPaymentMethodSelected: (PaymentMethod) -> Unit
) {
    Column(
        modifier = Modifier.selectableGroup(),
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        PaymentMethod.entries.forEach { paymentMethod ->
            Row(
                modifier = Modifier.selectable(
                    selected = paymentMethod == selectedPaymentMethod,
                    role = Role.RadioButton,
                    onClick = { onPaymentMethodSelected(paymentMethod) }
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = paymentMethod == selectedPaymentMethod,
                    onClick = null
                )
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = paymentMethod.text,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
private fun InaccessibleRadioGroup(
    selectedPaymentMethod: PaymentMethod,
    onPaymentMethodSelected: (PaymentMethod) -> Unit
) {
    Column {
        PaymentMethod.entries.forEach { paymentMethod ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = paymentMethod == selectedPaymentMethod,
                    onClick = { onPaymentMethodSelected(paymentMethod) }
                )
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = paymentMethod.text,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
private fun AlmostAccessibleRadioGroup(
    selectedPaymentMethod: PaymentMethod,
    onPaymentMethodSelected: (PaymentMethod) -> Unit
) {
    Column {
        PaymentMethod.entries.forEach { paymentMethod ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    modifier = Modifier.semantics {
                        contentDescription = paymentMethod.text
                    },
                    selected = paymentMethod == selectedPaymentMethod,
                    onClick = { onPaymentMethodSelected(paymentMethod) }
                )
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = paymentMethod.text,
                    fontSize = 20.sp
                )
            }
        }
    }
}

private enum class PaymentMethod(val text: String) {
    CASH("Cash"),
    CREDIT_CARD("Credit Card"),
    PAY_PAL("PayPal")
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
            style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .padding(start = 16.dp)
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
private fun MenuItem(
    @DrawableRes icon: Int,
    @StringRes text: Int,
    onClick: () -> Unit
) {
    val edgeMargin = 24.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = edgeMargin, end = edgeMargin)
            .clickable {
                onClick()
            }
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
    val edgeMargin = 24.dp
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