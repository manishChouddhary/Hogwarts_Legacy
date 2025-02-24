package com.hogwartslegacy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.hogwartslegacy.ui.navigation.AppNavHost
import com.hogwartslegacy.ui.theme.HogwartsLegacyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val extendedColors = com.hogwartslegacy.ui.theme.LocalExtendedColorScheme.current
            HogwartsLegacyTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(extendedColors.background)
                ) { innerPadding ->
                    val navController = rememberNavController()
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}