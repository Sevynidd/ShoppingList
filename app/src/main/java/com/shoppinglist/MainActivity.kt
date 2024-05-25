package com.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.room.Room
import com.shoppinglist.roomDatabase.RoomDatabase
import com.shoppinglist.ui.theme.ShoppingListTheme
import com.shoppinglist.viewModel.RoomRepository
import com.shoppinglist.viewModel.RoomViewModel
import com.shoppinglist.views.ScreenListDetails
import com.shoppinglist.views.ScreenListen
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RoomDatabase::class.java,
            name = "ShoppingList.db"
        ).fallbackToDestructiveMigration().build()
    }
    private val viewModel by viewModels<RoomViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return RoomViewModel(RoomRepository(db)) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            ShoppingListTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ScreenListen
                ) {
                    composable<ScreenListen> {
                        ScreenListen(viewModel, navController)
                    }

                    composable<ScreenListeDetail> {
                        val args = it.toRoute<ScreenListeDetail>()
                        ScreenListDetails()
                    }
                }
            }
        }
    }
}

@Serializable
object ScreenListen

@Serializable
data class ScreenListeDetail(
    val listID: Int,
    val listName: String,
    val listNote: String
)
