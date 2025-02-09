package com.shoppinglist.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shoppinglist.ScreenLItems
import com.shoppinglist.ScreenListEdit
import com.shoppinglist.components.DraggableItemInfo
import com.shoppinglist.components.DraggableListItem
import com.shoppinglist.roomDatabase.entities.RoomList
import com.shoppinglist.ui.theme.ShoppingListTheme
import com.shoppinglist.viewModel.RoomViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Lists(viewModel: RoomViewModel, navController: NavHostController) {
    ShoppingListTheme {
        var showBottomSheet by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState()

        var showDatePickerDialog by remember {
            mutableStateOf(false)
        }
        val datePickerState = rememberDatePickerState(
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    val dateCheck = Instant.fromEpochMilliseconds(utcTimeMillis)
                        .toLocalDateTime(TimeZone.currentSystemDefault()).date

                    val dateCurrent =
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

                    return dateCurrent.daysUntil(dateCheck) >= 0
                }
            }
        )

        var showTimePickerDialog by remember {
            mutableStateOf(false)
        }
        val timePickerState = rememberTimePickerState(
            initialHour = if (Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault()).hour == 23
            ) {
                0
            } else {
                Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault()).hour + 1
            },
            initialMinute = 0
        )

        var textNotifyField by remember { mutableStateOf(null as LocalDateTime?) }

        var showIconPickerDialog by remember {
            mutableStateOf(false)
        }

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
                        .padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val options = listOf("Alphabetical", "CreationDate")
                    val optionsIcon = listOf(Icons.Filled.Abc, Icons.Filled.DateRange)
                    var selectedIndex by remember { mutableIntStateOf(0) }

                    SingleChoiceSegmentedButtonRow {
                        options.forEachIndexed { index, label ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = options.size
                                ),
                                onClick = { selectedIndex = index },
                                selected = index == selectedIndex,
                                icon = {
                                    Icon(
                                        optionsIcon[index],
                                        contentDescription = label,
                                        Modifier.size(SegmentedButtonDefaults.IconSize)
                                    )
                                }
                            ) {
                                Text(label)
                            }
                        }
                    }

                    Spacer(Modifier.size(12.dp))
                    Content(navController, viewModel)
                }
            }
            var selectedIcon by remember { mutableStateOf(Icons.Default.ShoppingCart) }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState,
                    modifier = Modifier.imePadding()
                ) {
                    val focusManager = LocalFocusManager.current

                    var textListName by remember { mutableStateOf(TextFieldValue("")) }
                    var listNameMissing by remember { mutableStateOf(false) }
                    var textListNote by remember { mutableStateOf(TextFieldValue("")) }
                    var extraFieldsVisible by remember { mutableStateOf(false) }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            IconButton(
                                onClick = { showIconPickerDialog = true }
                            ) {
                                Icon(selectedIcon, "IconPicker")
                            }

                            OutlinedTextField(
                                value = textListName,
                                label = { Text(text = "Name") },
                                onValueChange = {
                                    textListName = it
                                    listNameMissing = false
                                },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                keyboardActions = KeyboardActions {
                                    focusManager.moveFocus(FocusDirection.Next)
                                },
                                singleLine = true,
                                isError = listNameMissing,
                                supportingText = {
                                    if (listNameMissing) {
                                        Text("Es muss ein Listenname eingegeben werden")
                                    }
                                }
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    extraFieldsVisible = extraFieldsVisible.not()
                                },
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                if (extraFieldsVisible) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                "IconDropdown"
                            )
                        }

                        AnimatedVisibility(extraFieldsVisible) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
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

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Benachrichtigung: ${
                                            if ((textNotifyField?.toInstant(TimeZone.currentSystemDefault())
                                                    ?.toEpochMilliseconds() == 0L) or (textNotifyField == null)
                                            ) {
                                                "Keine"
                                            } else {
                                                "\n" + (textNotifyField?.toJavaLocalDateTime()
                                                    ?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
                                            }
                                        }",
                                        modifier = Modifier.weight(0.7f)
                                    )

                                    IconButton(
                                        onClick = {
                                            focusManager.clearFocus()
                                            showDatePickerDialog = true
                                        },
                                        modifier = Modifier.weight(0.1f)
                                    ) {
                                        Icon(Icons.Default.DateRange, "Date")
                                    }

                                    IconButton(
                                        onClick = {
                                            focusManager.clearFocus()
                                            showTimePickerDialog = true
                                        },
                                        modifier = Modifier.weight(0.1f)
                                    ) {
                                        Icon(Icons.Default.AccessTime, "Time")
                                    }

                                    IconButton(
                                        onClick = {
                                            focusManager.clearFocus()
                                            textNotifyField = null
                                        },
                                        modifier = Modifier.weight(0.1f)
                                    ) {
                                        Icon(Icons.Default.Delete, "Delete")
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.size(12.dp))

                        Button(
                            onClick = {

                                if (textListName.text.isNotBlank()) {
                                    val list = RoomList(
                                        name = textListName.text,
                                        note = textListNote.text,
                                        notifyDate =
                                        if (textNotifyField == null) {
                                            null
                                        } else {
                                            textNotifyField?.toInstant(TimeZone.currentSystemDefault())
                                                ?.toEpochMilliseconds()
                                        }
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

            if (showIconPickerDialog) {
                val listIcons = listOf(
                    Icons.Default.ShoppingCart,
                    Icons.Default.AcUnit,
                    Icons.Default.AttachMoney,
                    Icons.Default.Book
                )
                var selectedIndexIcon by remember { mutableIntStateOf(0) }

                AlertDialog(
                    onDismissRequest = { showIconPickerDialog = false },
                    confirmButton = {
                        Button(onClick = {
                            selectedIcon = listIcons[selectedIndexIcon]
                            showIconPickerDialog = false
                        }) {
                            Text(text = "Ok")
                        }
                    },
                    text = {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 86.dp)
                        ) {
                            itemsIndexed(listIcons) { index, icon ->
                                Icon(
                                    modifier = Modifier
                                        .clickable {
                                            selectedIndexIcon = index
                                        }
                                        .background(
                                            color = if (selectedIndexIcon ==
                                                index
                                            ) Color.Gray else IconButtonDefaults.iconButtonColors().containerColor,
                                            shape = RoundedCornerShape(12.dp)
                                        ),
                                    imageVector = icon,
                                    contentDescription = "icon"
                                )
                            }
                        }
                    }
                )
            }

            if (showDatePickerDialog) {
                DatePickerDialog(
                    onDismissRequest = { showDatePickerDialog = false },
                    confirmButton = {
                        Button(onClick = {

                            val dateTime = Instant.fromEpochMilliseconds(
                                datePickerState.selectedDateMillis ?: 0L
                            ).toLocalDateTime(TimeZone.currentSystemDefault())

                            val newDate = LocalDateTime(
                                dateTime.year,
                                dateTime.month,
                                dateTime.dayOfMonth,
                                0,
                                0
                            )

                            textNotifyField = newDate

                            showDatePickerDialog = false
                        }) {
                            Text(text = "Speichern")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            if (showTimePickerDialog) {
                AlertDialog(
                    onDismissRequest = { showTimePickerDialog = false },
                    confirmButton = {
                        Button(onClick = {

                            val newDateTime: LocalDateTime? = if (textNotifyField == null) {
                                null
                            } else {
                                LocalDateTime(
                                    textNotifyField!!.year,
                                    textNotifyField!!.monthNumber,
                                    textNotifyField!!.dayOfMonth,
                                    timePickerState.hour,
                                    timePickerState.minute
                                )
                            }

                            textNotifyField = newDateTime

                            showTimePickerDialog = false
                        }) {
                            Text(text = "Speichern")
                        }
                    },
                    text = {
                        TimePicker(state = timePickerState)
                    })
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
                                note = list.list.note,
                                notifyDate = list.list.notifyDate
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
                    draggableItemInfo = DraggableItemInfo(
                        headline = list.list.name,
                        supporting = list.list.note,
                        trailing = "Anzahl: ${list.itemCount}",
                        isItem = false,
                        listIcon = list.list.icon ?: Icons.Default.ShoppingCart
                    )
                )
            }
        }
    }
}