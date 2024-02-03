package com.example.littlelemon

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(
    navigateToHome: () -> Unit
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
    val textStateFirstName = remember { mutableStateOf("")}
    val textStateLastName = remember { mutableStateOf("")}
    val textStateEmail = remember { mutableStateOf("")}
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.green_tittle_flag))
                .height(116.dp),
            contentAlignment = Alignment.Center,
        ){
            Text(
                text ="Let's get to know you",
                color = Color.White,
                fontSize = 28.sp,
                )}

        Text(
            text = "Personal information",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp, start = 32.dp, bottom = 16.dp))

        Text(
            text = "First Name",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 32.dp, top = 16.dp))

        OutlinedTextField(
            value = textStateFirstName.value,
            onValueChange = {newValue ->
                textStateFirstName.value = newValue},
            placeholder = { Text(text = "Type here")},
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
            value = textStateLastName.value,
            onValueChange = {newValue ->
                textStateLastName.value = newValue},
            placeholder = { Text(text = "Type here")},
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
            value = textStateEmail.value,
            onValueChange = {newValue ->
                textStateEmail.value = newValue},
            placeholder = { Text(text = "Type here")},
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp))

        Button(
            onClick = {
                if (textStateFirstName.value.isBlank() || textStateEmail.value.isBlank() || textStateLastName.value.isBlank()){
                    showDialog = true
                }else{
                    sharedPreferences.edit().apply {
                        putString("firstName",textStateFirstName.value)
                        putString("lastName",textStateLastName.value)
                        putString("email",textStateEmail.value)
                        commit()
                    }
                    navigateToHome()}},
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
                text = "Register",
                color = Color.Black,
                fontSize = 24.sp
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun OnboardingPreview(){
//    Onboarding()
//}

