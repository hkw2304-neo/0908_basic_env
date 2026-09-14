package com.hkw.a0908_test

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hkw.a0908_test.ui.view.IntroView
import com.hkw.a0908_test.ui.viewModel.IntroViewModel

object AppRoute {
    const val INTROPAGE = "intropage"
}
@Composable
fun NavRoute(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: String = AppRoute.INTROPAGE,
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300),
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300),
            ) + fadeOut(animationSpec = tween(300))
        },
    ){
        composable(route = AppRoute.INTROPAGE) {
            val introViewModel: IntroViewModel = hiltViewModel()
            IntroView(
                modifier = modifier,
                viewModel = introViewModel,
            )
        }
    }
}