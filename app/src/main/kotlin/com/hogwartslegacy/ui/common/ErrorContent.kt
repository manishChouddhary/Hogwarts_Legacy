package com.hogwartslegacy.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hogwartslegacy.R
import com.hogwartslegacy.ui.theme.HogwartsLegacyTheme
import com.hogwartslegacy.ui.theme.LocalExtendedColorScheme

@Composable
fun ErrorContent(onRetry: (() -> Unit)? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            imageVector = Icons.Default.Warning,
            contentDescription = stringResource(R.string.search_cta),
            tint = LocalExtendedColorScheme.current.primaryText
        )
        Text(
            text = stringResource(R.string.error_message),
            textAlign = TextAlign.Center,
            color = LocalExtendedColorScheme.current.secondaryText
        )
        onRetry?.let {
            Button(
                it,
                colors = ButtonColors(
                    containerColor = LocalExtendedColorScheme.current.surface,
                    contentColor = LocalExtendedColorScheme.current.primaryText,
                    disabledContainerColor = LocalExtendedColorScheme.current.surface,
                    disabledContentColor = LocalExtendedColorScheme.current.secondaryText
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = stringResource(R.string.retry))
            }
        }
    }
}

@Preview
@Composable
fun ErrorContentPreview() {
    HogwartsLegacyTheme { ErrorContent({}) }
}