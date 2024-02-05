package com.example.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.Destinations.*
import com.example.littlelemon.Onboarding

//erick alvarez hizo esto

@Composable
fun NavigationHost(){
    val navController = rememberNavController()

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val textStateFirstName:String = sharedPreferences.getString("firstName","")!!
    val textStateLastName:String = sharedPreferences.getString("lastName","")!!
    val textStateEmail:String = sharedPreferences.getString("email","")!!

    val initScreen = if(textStateFirstName !="" && textStateLastName != "" && textStateEmail != ""){
        Home.route
    }
    else{ Onboarding.route}

    NavHost(navController = navController, startDestination = initScreen){

        composable(Onboarding.route){
            Onboarding(
                navigateToHome = {
                    navController.navigate(Home.route)
                }
            )
        }

        composable(Home.route){
            Home(
                navigateToProfile = {
                  navController.navigate(Profile.route)
            }
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