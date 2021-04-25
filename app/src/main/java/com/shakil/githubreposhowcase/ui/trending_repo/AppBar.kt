package com.shakil.githubreposhowcase.ui.trending_repo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shakil.githubreposhowcase.R

@Composable
fun AppBar(isDarkMode: Boolean = false, onToggle: () -> Unit) {
    Column {

        Box {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    modifier = Modifier.weight(1f).wrapContentSize(Alignment.Center),
                    text = stringResource(R.string.appbar_heading),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 22.sp, fontWeight = FontWeight.W700
                    )
                )
            }

            IconButton(onClick = {

            }, modifier = Modifier.align(Alignment.CenterEnd), content = {
                Icon(
                    imageVector = if (isDarkMode) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp).padding(end = 8.dp),
                    tint = if (isDarkMode) Color.White else Color.Black
                )
            })
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .padding(start = 32.dp)
                .background(Color.LightGray.copy(alpha = 0.5f))
        )
    }
}