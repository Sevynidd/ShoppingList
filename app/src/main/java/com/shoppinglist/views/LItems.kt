package com.shoppinglist.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shoppinglist.ScreenItemEdit
import com.shoppinglist.ScreenLItems
import com.shoppinglist.components.DraggableItemInfo
import com.shoppinglist.components.DraggableListItem
import com.shoppinglist.roomDatabase.entities.RoomItem
import com.shoppinglist.ui.theme.ShoppingListTheme
import com.shoppinglist.viewModel.RoomViewModel

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LItems(
    args: ScreenLItems,
    viewModel: RoomViewModel,
    navController: NavHostController
) {
    ShoppingListTheme {
        remember {
            viewModel.setListID(args.listID)
        }

        LaunchedEffect(args.listID) {
            viewModel.getListFromListIDAndItemsSum(args.listID)
        }

        val listFromListIDAndItemsSum by viewModel.listFromListIDAndItemsSum.collectAsState()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(text = "Liste: ${listFromListIDAndItemsSum?.list?.name}") },
                    navigationIcon = {
                        IconButton(onClick = {
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
                        .padding(
                            top = innerPadding.calculateTopPadding(),
                            start = innerPadding.calculateStartPadding(layoutDirection = LayoutDirection.Ltr),
                            end = innerPadding.calculateEndPadding(layoutDirection = LayoutDirection.Ltr)
                        )
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 18.dp)
                            .padding(top = 20.dp)
                    ) {
                        Content(viewModel, navController)
                    }
                }
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                        .fillMaxWidth()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .imePadding(),
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(
                                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                                ),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val focusManager = LocalFocusManager.current
                            val keyboardController = LocalSoftwareKeyboardController.current

                            var textAddItem by remember { mutableStateOf(TextFieldValue("")) }
                            var itemNameMissing by remember { mutableStateOf(false) }

                            OutlinedTextField(
                                value = textAddItem,
                                label = { Text(text = "Ich brauche...") },
                                onValueChange = {
                                    textAddItem = it
                                    itemNameMissing = false
                                },
                                modifier = Modifier
                                    .fillMaxWidth(0.9f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                keyboardActions = KeyboardActions(onDone = {
                                    if (textAddItem.text.isNotBlank()) {
                                        val item = RoomItem(
                                            listID = args.listID,
                                            name = textAddItem.text
                                        )

                                        viewModel.upsertItem(item)

                                        focusManager.clearFocus()
                                        textAddItem = TextFieldValue("")
                                    } else {
                                        itemNameMissing = true
                                    }
                                }),
                                singleLine = true,
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            if (textAddItem.text.isNotBlank()) {
                                                val item = RoomItem(
                                                    listID = args.listID,
                                                    name = textAddItem.text
                                                )

                                                viewModel.upsertItem(item)

                                                keyboardController?.hide()
                                                focusManager.clearFocus()
                                                textAddItem = TextFieldValue("")
                                            } else {
                                                itemNameMissing = true
                                            }
                                        }
                                    ) {
                                        Icon(
                                            Icons.Default.AddCircle,
                                            "Hinzufügen",
                                            tint = MaterialTheme.colorScheme.surfaceTint,
                                            modifier = Modifier.fillMaxSize(0.8f)
                                        )
                                    }
                                },
                                isError = itemNameMissing,
                                supportingText = {
                                    if (itemNameMissing) {
                                        Text("Es muss ein Itemname eingegeben werden")
                                    }
                                }
                            )


                        }
                    }
                )
            }
        )
    }
}

@Composable
private fun Content(
    viewModel: RoomViewModel,
    navController: NavHostController
) {
    val listOfItems by viewModel.allItems.collectAsState()

    val listFromListIDAndItemsSum by viewModel.listFromListIDAndItemsSum.collectAsState()

    if (listOfItems.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.End
        ) {
            items(listOfItems) { item ->
                DraggableListItem(
                    onDelete = {
                        viewModel.deleteItem(
                            RoomItem(
                                itemID = item.itemID,
                                listID = item.listID,
                                name = item.name,
                                note = item.note,
                                amount = item.amount,
                                price = item.price,
                                isChecked = item.isChecked,
                                tagID = item.tagID,
                                categoryID = item.categoryID
                            )
                        )
                    },
                    onEdit = {
                        navController.navigate(
                            ScreenItemEdit(
                                item.listID,
                                item.itemID,
                                item.name,
                                item.note,
                                item.price,
                                item.amount,
                                item.tagID ?: -1
                            )
                        )
                    },
                    onCheckBoxClick = {
                        viewModel.upsertItem(
                            RoomItem(
                                itemID = item.itemID,
                                listID = item.listID,
                                name = item.name,
                                note = item.note,
                                amount = item.amount,
                                price = item.price,
                                isChecked = item.isChecked.not(),
                                tagID = item.tagID,
                                categoryID = item.categoryID
                            )
                        )
                    },
                    draggableItemInfo = DraggableItemInfo(
                        headline = item.name,
                        supporting = item.note ?: "",
                        trailing = "${item.amount}x" +
                                if (item.price != 0.0F) " ${"%.2f".format(item.price)} €" else "",
                        isChecked = item.isChecked
                    )
                )
            }

            item {
                Text(
                    if ((listFromListIDAndItemsSum?.sumPrice ?: 0.0F) > 0.0F) {
                        "Summe: ${"%.2f".format(listFromListIDAndItemsSum?.sumPrice)} €"
                    } else {
                        ""
                    }
                )
            }
        }
    }
}