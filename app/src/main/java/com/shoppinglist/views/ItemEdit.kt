package com.shoppinglist.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shoppinglist.ScreenItemEdit
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
        var textItemAmount by remember { mutableStateOf(TextFieldValue("")) }

        LaunchedEffect(args.itemID) {
            viewModel.getItemFromItemID(args.itemID)
        }

        val listItem by viewModel.itemFromItemID.collectAsState()

        val focusManager = LocalFocusManager.current

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
                                    price = textItemPrice.text.replace(",", ".").toFloatOrNull()
                                        ?: 0.0F,
                                    tagID = null,
                                    amount = if ((textItemAmount.text == "") or (textItemAmount.text == "0")) 1 else textItemAmount.text.toInt(),
                                    groupID = null
                                )
                            )
                            navController.navigateUp()
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
                                    if (it.price == 0.0F) "0.00" else it.price.toString()
                                )
                                textItemAmount = TextFieldValue(it.amount.toString())
                            }
                        }

                        OutlinedTextField(
                            value = textItemName,
                            label = { Text(text = "Name") },
                            onValueChange = {
                                textItemName = it
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            keyboardActions = KeyboardActions {
                                focusManager.moveFocus(FocusDirection.Next)
                            },
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
                            keyboardActions = KeyboardActions {
                                focusManager.moveFocus(FocusDirection.Next)
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = textItemPrice,
                            label = { Text(text = "Preis") },
                            onValueChange = {
                                textItemPrice = it
                            },
                            placeholder = { Text("0.00") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            keyboardActions = KeyboardActions {
                                textItemPrice = TextFieldValue(
                                    "%.2f".format(
                                        textItemPrice.text.replace(",", ".").toFloatOrNull()
                                            ?: 0.0F
                                    )
                                )
                                focusManager.moveFocus(FocusDirection.Next)
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = textItemAmount,
                            label = { Text(text = "Menge") },
                            onValueChange = {
                                textItemAmount = it
                            },
                            placeholder = { Text("0") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        )
    }
}