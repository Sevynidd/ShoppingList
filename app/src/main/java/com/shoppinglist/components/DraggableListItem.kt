package com.shoppinglist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun DraggableListItem(
    anchors: List<Float>,
    onDelete: () -> Unit?,
    onEdit: () -> Unit?,
    onListItemClick: () -> Unit,
    headline: @Composable () -> Unit,
    supporting: @Composable () -> Unit,
    trailing: @Composable () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    val colorFactor = 0.15f
    val darkerContainerColour = Color(
        red = ListItemDefaults.containerColor.red * (1 - colorFactor),
        green = ListItemDefaults.containerColor.green * (1 - colorFactor),
        blue = ListItemDefaults.containerColor.blue * (1 - colorFactor),
        alpha = ListItemDefaults.containerColor.alpha
    )

    var offsetX by remember {
        mutableFloatStateOf(0f)
    }

    val draggableState = rememberDraggableState { delta ->
        offsetX += delta
    }

    var showDeleteDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(12.dp))
            .background(darkerContainerColour)
            .draggable(
                state = draggableState,
                orientation = Orientation.Horizontal,
                onDragStopped = {
                    coroutineScope.launch {
                        val target =
                            anchors.minByOrNull { kotlin.math.abs(it - offsetX) } ?: 0f
                        offsetX = target
                    }
                }
            )
    ) {
        val mod = Modifier
            .width(50.dp)
            .matchParentSize()
            .clip(shape = RoundedCornerShape(12.dp))

        if (offsetX > anchors[0]) {
            ButtonOnBox(
                modifier = mod.background(Color(0xFF48E078)),
                alignment = Alignment.CenterStart,
                onClick = { onEdit() },
                iconButtonModifier = Modifier
                    .fillMaxHeight()
                    .width(70.dp),
                icon = {
                    Icon(
                        Icons.Default.Edit,
                        "Edit"
                    )
                }
            )

        } else {
            ButtonOnBox(
                modifier = mod.background(Color(0xFFE04A48)),
                alignment = Alignment.CenterEnd,
                onClick = { showDeleteDialog = true },
                iconButtonModifier = Modifier
                    .fillMaxHeight()
                    .width(70.dp),
                icon = {
                    Icon(
                        Icons.Default.Delete,
                        "Delete"
                    )
                }
            )
        }

        ListItem(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(12.dp))
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .clickable(
                    onClick = {
                        onListItemClick()
                    }
                ),
            headlineContent = headline,
            supportingContent = supporting,
            trailingContent = trailing,
            leadingContent = {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = "ShoppingCart",
                    tint = MaterialTheme.colorScheme.surfaceTint
                )
            },
            tonalElevation = 4.dp
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete()
                        showDeleteDialog = false
                        offsetX = anchors[0]
                    }
                ) {
                    Text("Löschen")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        offsetX = anchors[0]
                    }
                ) {
                    Text("Cancel")
                }
            },
            title = { Text("Bestätige löschen") },
            text = { Text("Bist du dir sicher, dass du diese Liste löschen möchtest?") }
        )
    }

}

@Composable
private fun ButtonOnBox(
    modifier: Modifier,
    alignment: Alignment,
    onClick: () -> Unit,
    iconButtonModifier: Modifier,
    icon: @Composable () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = alignment
    ) {
        IconButton(
            onClick = onClick,
            modifier = iconButtonModifier
        ) {
            icon()
        }
    }
}