package com.shoppinglist.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.shoppinglist.ScreenListEdit
import com.shoppinglist.ScreenLists
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
fun ListEdit(
    args: ScreenListEdit,
    viewModel: RoomViewModel,
    navController: NavHostController
) {
    ShoppingListTheme {

        var textListName by remember { mutableStateOf(TextFieldValue("")) }
        var textListNote by remember { mutableStateOf(TextFieldValue("")) }
        var textNotifyField by remember { mutableStateOf(null as LocalDateTime?) }

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
            initialHour = Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault()).hour + 1,
            initialMinute = 0
        )

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
                                    note = textListNote.text,
                                    notifyDate = textNotifyField?.toInstant(TimeZone.currentSystemDefault())
                                        ?.toEpochMilliseconds()
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
                            .padding(horizontal = 40.dp)
                    ) {
                        LaunchedEffect(listItem) {
                            listItem?.let {
                                textListName = TextFieldValue(it.name)
                                textListNote = TextFieldValue(it.note)
                                textNotifyField = Instant.fromEpochMilliseconds(it.notifyDate ?: 0L)
                                    .toLocalDateTime(TimeZone.currentSystemDefault())
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
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = textListNote,
                            label = { Text(text = "Notiz") },
                            onValueChange = {
                                textListNote = it
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
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
                                    showDatePickerDialog = true
                                },
                                modifier = Modifier.weight(0.1f)
                            ) {
                                Icon(Icons.Default.DateRange, "Date")
                            }

                            IconButton(
                                onClick = {
                                    showTimePickerDialog = true
                                },
                                modifier = Modifier.weight(0.1f)
                            ) {
                                Icon(Icons.Default.AccessTime, "Time")
                            }

                            IconButton(
                                onClick = {
                                    textNotifyField = null
                                },
                                modifier = Modifier.weight(0.1f)
                            ) {
                                Icon(Icons.Default.Delete, "Delete")
                            }
                        }
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
        )
    }
}