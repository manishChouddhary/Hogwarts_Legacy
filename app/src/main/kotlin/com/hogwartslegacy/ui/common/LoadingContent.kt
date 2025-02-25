package com.hogwartslegacy.ui.common

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
fun LoadingContent() {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val position by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(100, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .background(LocalExtendedColorScheme.current.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(48.dp).offset(x = position.dp),
            imageVector = Icons.Default.Notifications,
            contentDescription = stringResource(R.string.search_cta),
            tint = LocalExtendedColorScheme.current.primaryText
        )
        Text(
            text = stringResource(R.string.loading_message),
            textAlign = TextAlign.Center,
            color = LocalExtendedColorScheme.current.primaryText
        )
    }
}

@Composable
@Preview
private fun LoadingPreview(){
    HogwartsLegacyTheme {
        LoadingContent()
    }
}