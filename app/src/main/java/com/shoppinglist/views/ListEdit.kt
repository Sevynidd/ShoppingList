package com.shoppinglist.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shoppinglist.ScreenListEdit
import com.shoppinglist.ScreenLists
import com.shoppinglist.roomDatabase.entities.RoomList
import com.shoppinglist.ui.theme.ShoppingListTheme
import com.shoppinglist.viewModel.RoomViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListEdit(
    args: ScreenListEdit,
    viewModel: RoomViewModel,
    navController: NavHostController
) {
    ShoppingListTheme {
        var textListName by remember { mutableStateOf(TextFieldValue("")) }

        LaunchedEffect(args.listID) {
            viewModel.getListFromListID(args.listID)
        }

        val listItem by viewModel.listFromListID.collectAsState()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(text = "Liste: ${listItem?.name}") },
                    navigationIcon = {
                        IconButton(onClick = {
                            viewModel.upsertList(
                                RoomList(
                                    listID = args.listID,
                                    name = textListName.text,
                                    note = ""
                                )
                            )

                            navController.navigate(ScreenLists)
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
                        LaunchedEffect(listItem) {
                            listItem?.let {
                                textListName = TextFieldValue(it.name)
                            }
                        }

                        OutlinedTextField(
                            value = textListName,
                            label = { Text(text = "Name") },
                            onValueChange = {
                                textListName = it
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true,
                        )
                    }
                }
            }
        )
    }
}