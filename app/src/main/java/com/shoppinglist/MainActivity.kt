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
import com.shoppinglist.views.ItemEdit
import com.shoppinglist.views.LItems
import com.shoppinglist.views.ListEdit
import com.shoppinglist.views.Lists
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RoomDatabase::class.java,
            name = "ShoppingList.db"
        ).build()
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
                    startDestination = ScreenLists
                ) {
                    composable<ScreenLists> {
                        Lists(viewModel, navController)
                    }

                    composable<ScreenLItems> {
                        val args = it.toRoute<ScreenLItems>()
                        LItems(args, viewModel, navController)
                    }

                    composable<ScreenItemEdit> {
                        val args = it.toRoute<ScreenItemEdit>()
                        ItemEdit(args, viewModel, navController)
                    }

                    composable<ScreenListEdit> {
                        val args = it.toRoute<ScreenListEdit>()
                        ListEdit(args = args, viewModel = viewModel, navController = navController)
                    }
                }
            }
        }
    }
}

@Serializable
object ScreenLists

@Serializable
data class ScreenLItems(
    val listID: Int
)

@Serializable
data class ScreenItemEdit(
    val listID: Int,
    val itemID: Int,
    val name: String,
    val note: String?,
    val price: Float,
    val amount: Int,
    val categoryID: Int
)

@Serializable
data class ScreenListEdit(
    val listID: Int
)