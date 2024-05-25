package com.shoppinglist.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shoppinglist.ScreenListeDetail
import com.shoppinglist.ScreenListen
import com.shoppinglist.ui.theme.ShoppingListTheme
import com.shoppinglist.viewModel.RoomViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenListDetails(
    args: ScreenListeDetail,
    viewModel: RoomViewModel,
    navController: NavHostController
) {
    ShoppingListTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(text = args.listName) },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigate(ScreenListen)
                        }) {
                            Icon(Icons.AutoMirrored.Default.ArrowBack, "Zurück")
                        }
                    }
                )
            },
            content = { innerPadding ->
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
                        Text(text = "Test")
                    }
                }
            }
        )
    }
}