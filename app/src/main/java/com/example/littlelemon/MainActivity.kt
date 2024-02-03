package com.example.littlelemon

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import android.content.Context



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationHost()
        }
        lifecycleScope.launch {
            loadToDB(applicationContext)
        }
    }
}

val httpClient = HttpClient(Android){
    install(ContentNegotiation){
        json(contentType = ContentType("text","plain"))
    }
}

suspend fun fetchContent():String{
    val url:String = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
    return httpClient.get(url).bodyAsText()
}

suspend fun decodeMenu(jsonString:String): MenuNetwork { //: List<MenuNetwork>
    Log.d("DecodeMenu", "JSON RECIBIDO EN DECODE MENU 00000: $jsonString")

    //val json = Json {ignoreUnknownKeys = true}
    //Log.d("DecodeMenu", "JSON CONFIGURADO CORRECTAMENTE 00000: $json")

    val menu = Json.decodeFromString<MenuNetwork>(jsonString)
    Log.d("DecodeMenu", "MENU DECODIFICADO CORRECTAMENTE 00000: $menu")

    return menu
}

suspend fun loadToDB(context: Context){
    val jsonString = fetchContent()
    val decodedMenu = decodeMenu(jsonString)

    // Guardar los datos en la base de datos
    val menuItemList = decodedMenu.menu.map { menuItem ->
        MenuItem(
            id = menuItem.id,
            title = menuItem.title,
            description = menuItem.description,
            price = menuItem.price,
            image = menuItem.image,
            category = menuItem.category
        )
    }
    val db = AppDataBase.getDataBase(context)
    db.menuDao().insertAll(menuItemList)
}