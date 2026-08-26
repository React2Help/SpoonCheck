package dev.react2help.spooncheck

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.react2help.spooncheck.presentation.AuthRoute
import dev.react2help.spooncheck.presentation.CreateAccountScreen
import dev.react2help.spooncheck.presentation.DashboardScreen
import dev.react2help.spooncheck.presentation.LoginScreen
import dev.react2help.spooncheck.presentation.UserType
import dev.react2help.spooncheck.presentation.WelcomeScreen
import dev.react2help.spooncheck.theme.spoonCheckTypography

@Composable
@Preview
fun App() {
    MaterialTheme(typography = spoonCheckTypography()) {
        val navController = rememberNavController()
        var selectedUserType by rememberSaveable { mutableStateOf(UserType.Patient) }

        NavHost(
            navController = navController,
            startDestination = AuthRoute.WELCOME,
        ) {
            composable(AuthRoute.WELCOME) {
                WelcomeScreen(
                    selectedUserType = selectedUserType,
                    onUserTypeChange = { selectedUserType = it },
                    onLoginClick = { navController.navigate(AuthRoute.LOGIN) },
                    onCreateAccountClick = { navController.navigate(AuthRoute.CREATE_ACCOUNT) },
                )
            }

            composable(AuthRoute.LOGIN) {
                LoginScreen(
                    onSignIn = {
                        navController.navigate(AuthRoute.MAIN) {
                            popUpTo(AuthRoute.WELCOME) { inclusive = true }
                        }
                    },
                    onNavigateToCreateAccount = {
                        navController.navigate(AuthRoute.CREATE_ACCOUNT)
                    },
                )
            }

            composable(AuthRoute.CREATE_ACCOUNT) {
                CreateAccountScreen(
                    userType = selectedUserType,
                    onSubmit = {
                        navController.navigate(AuthRoute.MAIN) {
                            popUpTo(AuthRoute.WELCOME) { inclusive = true }
                        }
                    },
                )
            }

            composable(AuthRoute.MAIN) { DashboardScreen() }
        }
    }
}
