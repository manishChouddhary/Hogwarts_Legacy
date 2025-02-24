package com.hogwartslegacy.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry

private const val DURATION_SPEC =  300
val ENTER_ANIMATION: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) = {
    slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(DURATION_SPEC)
    ) + fadeIn(animationSpec = tween(DURATION_SPEC))
}

val EXIT_ANIMATION: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) = {
    slideOutHorizontally(
        targetOffsetX = { fullWidth -> -fullWidth },
        animationSpec = tween(DURATION_SPEC)
    ) + fadeOut(animationSpec = tween(DURATION_SPEC))
}