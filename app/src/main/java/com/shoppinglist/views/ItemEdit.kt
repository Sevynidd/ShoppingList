package com.shoppinglist.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.shoppinglist.ScreenItemEdit
import com.shoppinglist.ScreenLItems
import com.shoppinglist.roomDatabase.entities.RoomItem
import com.shoppinglist.ui.theme.ShoppingListTheme
import com.shoppinglist.viewModel.RoomViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemEdit(
    args: ScreenItemEdit,
    viewModel: RoomViewModel,
    navController: NavHostController
) {
    ShoppingListTheme {
        var textItemName by remember { mutableStateOf(TextFieldValue("")) }
        var textItemNote by remember { mutableStateOf(TextFieldValue("")) }
        var textItemPrice by remember { mutableStateOf(TextFieldValue("")) }

        LaunchedEffect(args.itemID) {
            viewModel.getItemFromItemID(args.itemID)
        }

        val listItem by viewModel.itemFromItemID.collectAsState()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(text = "Item: ${args.name}") },
                    navigationIcon = {
                        IconButton(onClick = {
                            viewModel.upsertItem(
                                RoomItem(
                                    itemID = args.itemID,
                                    listID = args.listID,
                                    name = textItemName.text,
                                    note = textItemNote.text,
                                    price = textItemPrice.text.toDoubleOrNull(),
                                    categoryID = null,
                                    amount = 1,
                                    unitID = null,
                                    groupID = null
                                )
                            )

                            navController.navigate(
                                ScreenLItems(
                                    listID = args.listID
                                )
                            )
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
                            .padding(horizontal = 40.dp)
                    ) {
                        LaunchedEffect(listItem) {
                            listItem?.let {
                                textItemName = TextFieldValue(it.name)
                                textItemNote = TextFieldValue(it.note ?: "")
                                textItemPrice = TextFieldValue(
                                    if (it.price == null) {
                                        "0.0"
                                    } else {
                                        it.price.toString()
                                    }
                                )
                            }
                        }

                        OutlinedTextField(
                            value = textItemName,
                            label = { Text(text = "Name") },
                            onValueChange = {
                                textItemName = it
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = textItemNote,
                            label = { Text(text = "Notiz") },
                            onValueChange = {
                                textItemNote = it
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = textItemPrice,
                            label = { Text(text = "Preis") },
                            onValueChange = {
                                textItemPrice = it
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        )
    }
}