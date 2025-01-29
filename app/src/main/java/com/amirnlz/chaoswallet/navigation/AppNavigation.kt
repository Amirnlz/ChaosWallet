package com.amirnlz.chaoswallet.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.amirnlz.chaoswallet.OnboardingScreen
import com.amirnlz.chaoswallet.TouchSurface
import com.amirnlz.chaoswallet.TouchViewModel
import com.amirnlz.chaoswallet.WalletDisplay

sealed class Screen(val route: String) {
    data object Onboarding : Screen("onboarding")
    data object Tapping : Screen("tapping")
    data object WalletDisplay : Screen("wallet_display")
}


@Composable
fun AppNavigation(modifier: Modifier = Modifier, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route,
        modifier = modifier

    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen {
                navController.navigate(Screen.Tapping.route)
            }
        }
        composable(Screen.Tapping.route) {
            val viewModel: TouchViewModel = viewModel<TouchViewModel>()
            TouchSurface(viewModel = viewModel)
        }
        composable(Screen.WalletDisplay.route) {
            val viewModel: TouchViewModel = viewModel<TouchViewModel>()

            WalletDisplay(viewModel = viewModel, onReset = {
                navController.popBackStack(Screen.Onboarding.route, inclusive = false)
            })
        }
    }

}