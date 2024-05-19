package com.shoppinglist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.shoppinglist.roomDatabase.List
import com.shoppinglist.roomDatabase.RoomDatabase
import com.shoppinglist.ui.theme.ShoppingListTheme
import com.shoppinglist.viewModel.RoomRepository
import com.shoppinglist.viewModel.RoomViewModel

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
                override fun <T: ViewModel> create(modelClass: Class<T>): T {
                    return RoomViewModel(RoomRepository(db)) as T
                }
            }
        }
    )

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            ShoppingListTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = { TopAppBar(title = { Text(text = "Meine Listen") }) },
                    /*bottomBar = {
                        BottomAppBar {
                            NavigationBar {
                                NavigationBarItem(
                                    selected = true,
                                    onClick = { /*TODO*/ },
                                    icon = {
                                        Icon(
                                            Icons.Default.Home,
                                            contentDescription = "Home"
                                        )
                                    })
                            }
                        }
                    },*/
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                val list = List(
                                    name = "Liste ${(0..40).random()}",
                                    note = "Notiz ${(1..26).random()}"
                                )
                                viewModel.upsertList(list)
                            }
                        ) {
                            Icon(Icons.Default.Add, "Add Lists")
                        }
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 18.dp)
                        ) {
                            Content()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun Content() {

        var listofLists by remember {
            mutableStateOf(listOf<List>())
        }

        viewModel.getLists().observe(this){
            listofLists = it
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listofLists) { list ->
                ListItem(
                    modifier = Modifier.clip(shape = RoundedCornerShape(12.dp)),
                    headlineContent = { Text(list.name) },
                    supportingContent = { Text(list.note) },
                    trailingContent = { Text("Anzahl: ${list.listID}") },
                    leadingContent = {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "ShoppingCart",
                        )
                    },
                    tonalElevation = 4.dp
                )
            }
        }
    }
}
