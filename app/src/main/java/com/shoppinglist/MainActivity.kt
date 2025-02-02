package com.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.shoppinglist.ui.theme.ShoppingListTheme
import com.shoppinglist.viewModel.RoomViewModel
import com.shoppinglist.views.ItemEdit
import com.shoppinglist.views.LItems
import com.shoppinglist.views.ListEdit
import com.shoppinglist.views.Lists
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val roomViewModel: RoomViewModel by viewModels()

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
                        Lists(roomViewModel, navController)
                    }

                    composable<ScreenLItems> {
                        val args = it.toRoute<ScreenLItems>()
                        LItems(args, roomViewModel, navController)
                    }

                    composable<ScreenItemEdit> {
                        val args = it.toRoute<ScreenItemEdit>()
                        ItemEdit(args, roomViewModel, navController)
                    }

                    composable<ScreenListEdit> {
                        val args = it.toRoute<ScreenListEdit>()
                        ListEdit(
                            args = args,
                            viewModel = roomViewModel,
                            navController = navController
                        )
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