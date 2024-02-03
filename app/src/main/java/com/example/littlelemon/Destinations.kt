package com.example.littlelemon

sealed class Destinations(
    val route : String
){
    object Home:Destinations("home")
    object Onboarding:Destinations("onboarding")
    object Profile:Destinations("profile")
}
