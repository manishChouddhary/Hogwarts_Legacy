package com.hogwartslegacy.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFE6EFEC),
    secondary = Color(0xFFF3F7F6),
    tertiary = Pink80,
    background = Color(0xFF080C0B),
    surface = Color(0xFF182522)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF182521),
    secondary = Color(0xFF20312C),
    tertiary = Pink40,
    background = Color(0xFFA9C6BF),
    surface = Color(0xFF6B9E92),
)

data class ExtendedColorScheme(
    val colorScheme: ColorScheme,
    val gryffindor: Color,
    val slytherin: Color,
    val ravenclaw: Color,
    val hufflepuff: Color,
    val surface: Color,
    val background: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val success: Color = Color(0xFF046218),
    val error: Color = Color.Red
)

val extendedLightColorScheme = ExtendedColorScheme(
    colorScheme = LightColorScheme,
    gryffindor = gryffindor,
    slytherin = slytherin,
    ravenclaw = ravenclaw,
    hufflepuff = hufflepuff,
    background = lightBackground,
    surface = lightSurface,
    primaryText = lightPrimaryText,
    secondaryText = lightSecondaryText,
)

val extendedDarkColorScheme = ExtendedColorScheme(
    colorScheme = DarkColorScheme,
    gryffindor = gryffindor,
    slytherin = slytherin,
    ravenclaw = ravenclaw,
    hufflepuff = hufflepuff,
    background = darkBackground,
    surface = darkSurface,
    primaryText = darkPrimaryText,
    secondaryText = darkSecondaryText,
)

val LocalExtendedColorScheme = staticCompositionLocalOf { extendedLightColorScheme }

@Composable
fun HogwartsLegacyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> extendedDarkColorScheme.colorScheme
        else -> extendedLightColorScheme.colorScheme
    }
    val extendedColorScheme = if (darkTheme) extendedDarkColorScheme else extendedLightColorScheme

    CompositionLocalProvider(
        LocalExtendedColorScheme provides extendedColorScheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content,
            typography = Typography,
        )
    }
}