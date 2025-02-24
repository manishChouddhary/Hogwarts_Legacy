package com.hogwartslegacy.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hogwartslegacy.ui.characterdetail.CharacterDetailScreen
import com.hogwartslegacy.ui.characterlist.CharacterListComposable

enum class Screen {
    SPLASH,
    CHARACTER_LIST,
    CHARACTER_DETAILS,
}

sealed class NavigationItem(val route: String) {
    data object Splash : NavigationItem(Screen.SPLASH.name)
    object CharacterList : NavigationItem(Screen.CHARACTER_LIST.name)
    object CharacterDetail : NavigationItem(Screen.CHARACTER_DETAILS.name + "/{$CHARACTER_ID}")
}


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = NavigationItem.CharacterList.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Splash.route) {
            //SplashScreen(navController)
        }
        composable(route = NavigationItem.CharacterList.route) {
            CharacterListComposable(
                onCharacterSelected = { characterId ->
                    navController.navigate(
                        NavigationItem.CharacterDetail.route.replace(
                            "{$CHARACTER_ID}",
                            Uri.encode(characterId)
                        )
                    )
                }
            )
        }
        composable(
            route = NavigationItem.CharacterDetail.route,
            arguments = listOf(navArgument(CHARACTER_ID) { type = NavType.StringType }),
            enterTransition = ENTER_ANIMATION,
            exitTransition = EXIT_ANIMATION
        ) {
            CharacterDetailScreen(
                characterId = it.arguments?.getString(CHARACTER_ID) ?: "",
                onBackPress = { navController.popBackStack() }
            )
        }
    }
}

const val CHARACTER_ID = "character_id"