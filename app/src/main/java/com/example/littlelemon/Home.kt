package com.example.littlelemon

import android.app.PendingIntent.getActivity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.room.Room
import java.security.AccessController.getContext
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navigateToProfile: () -> Unit,
) {
    val db = AppDataBase.getDataBase(LocalContext.current)
    val menuItems = remember { mutableStateOf<List<MenuItem>>(emptyList()) }
    val textState = remember { mutableStateOf("") }
    val buttonClicked = remember { mutableStateOf("") }

    LaunchedEffect(textState.value,buttonClicked.value) {
        val items = withContext(Dispatchers.IO) {
            db.menuDao().getAllMenuItems()
        }
        val filteredItems :List<MenuItem>
//        var filteredItems = items.filter { content ->
//            content.title.contains(textState.value, ignoreCase = true)
//        }
        if(buttonClicked.value !="" && buttonClicked.value!="all" ){
            filteredItems = items.filter { content ->
                content.category.contains(buttonClicked.value, ignoreCase = true)
            }
        }else{
            filteredItems = items.filter { content ->
                content.title.contains(textState.value, ignoreCase = true)
            }
        }

        menuItems.value = filteredItems

    }




    // Mostrar los datos en la pantalla usando el estado actualizado
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                painter = painterResource(id = R.drawable.littlelemonlogo),
                contentDescription = "Logo de Little Lemon",
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 16.dp, end = 60.dp)
                    .scale(1.5f)
            )
            IconButton(
                onClick = {navigateToProfile()},
                modifier = Modifier
                    .size(80.dp)
                    .padding(top = 16.dp, bottom = 4.dp, end = 20.dp)
                    .clip(RoundedCornerShape(64.dp)),
                content = {
                    Image(
                        painter = painterResource(id = R.drawable.perfil),
                        contentDescription = "Profile picture",
                    )
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF495e57))
                .height(300.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Little Lemmon",
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = Color(0xFFf4ce14)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Chicago",
                        fontSize = 24.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "We are a family owned mediterranean restaurant, focused on traditional recipes served with a modern twist",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.upperpanelimage),
                    contentDescription = "Upe",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                )


            }

            OutlinedTextField(
                value = textState.value,
                onValueChange = { newText -> textState.value = newText },
                label = {
                    Text(
                        text = "Enter search phrase",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Black
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
            )
        }

        UnderHeroSection(menuItems.value,buttonClicked.value){ newFilter ->
            buttonClicked.value = newFilter
        }

    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UnderHeroSection(content:List<MenuItem>,filterButton:String ="",onFilterChange:(String) -> Unit){
    val typeOfDishes = listOf<String>("Starters","Mains","Desserts","Drinks","All")

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Text(
                text = "ORDER FOR DELIVERY!",
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                fontSize = 20.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            LazyRow{
                items(typeOfDishes.size){index ->
                    Button(
                        onClick = {
                            if(typeOfDishes[index].lowercase()=="all"){
                                val filter = "all"
                                onFilterChange(filter)
                            }else{
                                val filter = typeOfDishes[index].lowercase()
                                onFilterChange(filter)}
                        },
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.LightGray,
                            //contentColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = typeOfDishes[index],
                            color = Color.DarkGray,
                            fontSize = 16.sp
                        )
                    }


                }
            }
        }

        LazyColumn {

            items(content.size){index ->
                Divider(
                    color = Color.Gray, // Establece el color del divisor a gris
                    thickness = 1.dp, // Establece el grosor del divisor
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 10.dp))
                Row (
                    modifier = Modifier.fillMaxWidth()
                ){
                    Column (
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    ){
                        Text(text= content[index].title, fontWeight = FontWeight.Bold)
                        Text(text= content[index].description, fontWeight = FontWeight.Bold,color = Color.Gray)
                        Text(text= "$${content[index].price}", fontWeight = FontWeight.Bold, color = Color.DarkGray)
                    }
                    showImage(path = content[index].image)

                }
            }
        }
    }


}
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun showImage(path:String){
    GlideImage(
        model = path,
        contentDescription = "LoadImage",
        modifier = Modifier
            .size(100.dp)
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(16.dp)),
        )
}

//@Preview(showBackground = true)
//@Composable
//fun OnboardingPreview() {
//    //UnderHeroSection()
//}