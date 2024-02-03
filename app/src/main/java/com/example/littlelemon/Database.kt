package com.example.littlelemon

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.io.path.createTempDirectory

@Entity
data class MenuItem(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)

@Dao
interface MenuDao{
    @Query("SELECT * FROM MenuItem")
    fun getAllMenuItems():List<MenuItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(menuItems: List<MenuItem>)
}

@Database(entities = [MenuItem::class],version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase(){
    abstract fun menuDao(): MenuDao

    companion object{

        @Volatile
        private var INSTANCE:AppDataBase? = null

        fun getDataBase(context: Context): AppDataBase {
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "menu-database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

