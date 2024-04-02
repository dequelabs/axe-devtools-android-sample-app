package com.deque.mobile.axedevtoolssampleapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScopeInstance.align
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.deque.mobile.axedevtoolssampleapp.R

@Composable
@Preview
fun Menu() {
    Surface(modifier = Modifier.background(color = Color.White)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(painter = painterResource(id = R.drawable.ic_profile), contentDescription = null)
            Column() {
                Text(text = stringResource(id = R.string.customer))
                Text(text = stringResource(id = R.string.james_anderson))
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = stringResource(id = R.string.edit_profile)
            )
        }

        Row {
            Surface(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(20.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.promo_background),
                    contentDescription = stringResource(
                        id = R.string.dancing_woman_on_purple_background
                    )
                )
                Text(text = stringResource(id = R.string._45_sale), textAlign = TextAlign.End)
                Text(text = stringResource(id = R.string.promo_for_you))
                Text(text = stringResource(id = R.string.promo_code))
            }
        }
        
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(painter = painterResource(id = R.drawable.ic_user), contentDescription = stringResource(
                id = R.string.my_details_icon
            ))
            Text(text = stringResource(id = R.string.my_details))
            Icon(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = stringResource(
                id = R.string.right_arrow
            ))
        }
    }
}