package com.shoppinglist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.shoppinglist.ui.theme.ShoppingListTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            ShoppingListTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = { TopAppBar(title = { Text(text = "Meine Listen") }) },
                    bottomBar = {
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
                    },
                    floatingActionButton = {
                        val context = LocalContext.current
                        FloatingActionButton(
                            onClick = {
                                Toast.makeText(
                                    context,
                                    "Clicked on FAB",
                                    Toast.LENGTH_SHORT
                                ).show()
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
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(12) { index ->
                ListItem(
                    modifier = Modifier.clip(shape = RoundedCornerShape(12.dp)),
                    headlineContent = { Text("Liste ${index + 1}") },
                    supportingContent = { Text("Notiz ${index + 1}") },
                    trailingContent = { Text("Anzahl: ${(0..14).random()}") },
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
