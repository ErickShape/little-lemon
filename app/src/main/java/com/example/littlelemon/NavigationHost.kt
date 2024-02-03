package com.example.littlelemon

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.Destinations.*


@Composable
fun NavigationHost(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Onboarding.route){

        composable(Onboarding.route){
            Onboarding(
                navigateToHome = {
                    navController.navigate(Home.route)
                }
            )
        }

        composable(Home.route){
            Home(
//                navigateToProfile = {
//                  navController.navigate(Profile.route)
//            }
            )
        }

        composable(Profile.route){
            Profile(
                navigateToOnboarding = {
                    navController.navigate(Onboarding.route)
                }
            )
        }
    }
}