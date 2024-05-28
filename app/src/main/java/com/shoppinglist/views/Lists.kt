package com.shoppinglist.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shoppinglist.ScreenLItems
import com.shoppinglist.ScreenListEdit
import com.shoppinglist.components.DraggableListItem
import com.shoppinglist.roomDatabase.entities.RoomList
import com.shoppinglist.ui.theme.ShoppingListTheme
import com.shoppinglist.viewModel.RoomViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Lists(viewModel: RoomViewModel, navController: NavHostController) {
    ShoppingListTheme {
        var showBottomSheet by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState()

        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = { TopAppBar(title = { Text(text = "Meine Listen") }) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        showBottomSheet = true
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
                    Content(navController, viewModel)
                }
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState
                ) {
                    var textListName by remember { mutableStateOf(TextFieldValue("")) }
                    var listNameMissing by remember { mutableStateOf(false) }
                    var textListNote by remember { mutableStateOf(TextFieldValue("")) }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        OutlinedTextField(
                            value = textListName,
                            label = { Text(text = "Name") },
                            onValueChange = {
                                textListName = it
                                listNameMissing = false
                            },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true,
                            isError = listNameMissing,
                            supportingText = {
                                if (listNameMissing) {
                                    Text("Es muss ein Listenname eingegeben werden")
                                }
                            }
                        )

                        OutlinedTextField(
                            value = textListNote,
                            label = { Text(text = "Notiz") },
                            onValueChange = {
                                textListNote = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.size(12.dp))

                        Button(
                            onClick = {

                                if (textListName.text.isNotBlank()) {
                                    val list = RoomList(
                                        name = textListName.text,
                                        note = textListNote.text
                                    )

                                    viewModel.upsertList(list)

                                    showBottomSheet = false
                                } else {
                                    listNameMissing = true
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text("Speichern")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Content(navController: NavHostController, viewModel: RoomViewModel) {

    val listofLists by viewModel.allLists.collectAsState()
    if (listofLists.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listofLists) { list ->

                DraggableListItem(
                    onDelete = {
                        viewModel.deleteList(
                            RoomList(
                                listID = list.list.listID,
                                name = list.list.name,
                                note = list.list.note
                            )
                        )
                    },
                    onEdit = {
                        navController.navigate(
                            ScreenListEdit(
                                listID = list.list.listID
                            )
                        )
                    },
                    onListItemClick = {
                        navController.navigate(
                            ScreenLItems(
                                list.list.listID
                            )
                        )
                    },
                    headline = {
                        Text(text = list.list.name)
                    },
                    supporting = {
                        Text(list.list.note)
                    },
                    trailing = {
                        Text("Anzahl: ${list.itemCount}")
                    }
                )
            }
        }
    }
}