package com.hogwartslegacy.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hogwartslegacy.R
import com.hogwartslegacy.ui.theme.LocalExtendedColorScheme

@Composable
fun TopBarScaffold(
    title: String,
    onBackClick: (() -> Unit)? = null,
    onSearchClick: (() -> Unit)? = null
) {
    val extendedColors = LocalExtendedColorScheme.current
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .fillMaxWidth()
            .background(extendedColors.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        onBackClick?.let {
            IconButton(onClick = it) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_cta)
                )
            }
        }
        Text(
            text = title,
            fontSize = 24.sp,
            style = MaterialTheme.typography.titleLarge,
            color = extendedColors.primaryText,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        )
        onSearchClick?.let {
            IconButton(onClick = it) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_cta)
                )
            }
        }
    }
}

@Composable
@Preview
fun TopBarScaffoldPreview() {
    TopBarScaffold(
        title = "Hogwarts Legacy",
        onSearchClick = { }
    )
}