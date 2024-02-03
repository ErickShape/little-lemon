package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(
    navigateToOnboarding: () -> Unit
){
    var showDialog by remember {mutableStateOf(false)}
    if(showDialog){
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Registration unsuccessful")},
            text = { Text(text = "Please enter all data")},
            confirmButton = { }
        )
    }
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val textStateFirstName:String = sharedPreferences.getString("firstName","")!!
    val textStateLastName:String = sharedPreferences.getString("lastName","")!!
    val textStateEmail:String = sharedPreferences.getString("email","")!!

    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween

    ){

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ) {
            Image(
                painter = painterResource(id = R.drawable.littlelemonlogo ),
                contentDescription ="Logo de Little Lemon",
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 16.dp)
                    .fillMaxWidth()
                    .scale(1.5f)
            )
        }

        Text(
            text = "Personal information",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 160.dp, start = 32.dp, bottom = 16.dp))

        Text(
            text = "First Name",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 32.dp, top = 16.dp))

        OutlinedTextField(
            value = textStateFirstName,
            onValueChange = {},
            readOnly=true,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp))


        Text(
            text = "Last Name",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 32.dp, top = 16.dp))

        OutlinedTextField(
            value = textStateLastName,
            onValueChange = {},
            readOnly = true,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp))

        Text(
            text = "Email",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 32.dp, top = 16.dp))

        OutlinedTextField(
            value = textStateEmail,
            onValueChange = {},
            readOnly = true,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp))

        Button(
            onClick = {
                sharedPreferences.edit().apply {
                    remove("firstName") // Elimina el valor asociado a la clave "firstName"
                    remove("lastName") // Elimina el valor asociado a la clave "lastName"
                    remove("email")
                }.apply()
                navigateToOnboarding()
                },
            modifier = Modifier
                .padding(top = 150.dp, bottom = 20.dp, start = 10.dp)
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.green_button),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Log out",
                color = Color.Black,
                fontSize = 24.sp
            )
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun OnboardingPreview(){
//    Profile()
//}

