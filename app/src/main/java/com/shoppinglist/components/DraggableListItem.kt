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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.shoppinglist.R
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * @param onDelete What should happen on delete-click?
 * @param onEdit What should happen on edit-click?
 * @param onListItemClick What should happen, when the ListItem itself is clicked?
 * @param draggableItemTexts Object of texts, which are used for the headline, supporting and trailing text of the ListItem
 */
@Composable
fun DraggableListItem(
    onDelete: () -> Unit?,
    onEdit: () -> Unit?,
    onListItemClick: (() -> Unit)? = null,
    draggableItemTexts: DraggableItemTexts
) {
    val anchors: List<Float> = listOf(0f, 180f, -180f)

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

        if (offsetX > 0f) {
            ButtonOnBox(
                isEdit = true,
                modifier = mod,
                onClick = { onEdit() }
            )
        } else {
            ButtonOnBox(
                isEdit = false,
                modifier = mod,
                onClick = { showDeleteDialog = true }
            )
        }

        ListItem(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(12.dp))
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .then(
                    if (onListItemClick != null) {
                        Modifier.clickable(
                            onClick = {
                                onListItemClick()
                            }
                        )
                    } else {
                        Modifier
                    }
                ),
            headlineContent = { Text(draggableItemTexts.headline) },
            supportingContent = {
                if (draggableItemTexts.supporting.isNotEmpty()) {
                    Text(draggableItemTexts.supporting)
                }
            },
            trailingContent = {
                if (draggableItemTexts.trailing.isNotEmpty()) {
                    Text(draggableItemTexts.trailing)
                }
            },
            overlineContent = {
                if (draggableItemTexts.overline.isNotEmpty()) {
                    Text(draggableItemTexts.overline)
                }
            },
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
                        offsetX = 0f
                    }
                ) {
                    Text("Löschen")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        offsetX = 0f
                    }
                ) {
                    Text("Cancel")
                }
            },
            title = { Text("Bestätige Löschen") },
            text = { Text("Bist du dir sicher, dass du dies löschen möchtest?") }
        )
    }

}

@Composable
private fun ButtonOnBox(
    isEdit: Boolean = true,
    modifier: Modifier,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = modifier.background(
            color = if (isEdit)
                Color(context.getColor(R.color.green)) else
                Color(context.getColor(R.color.red))
        ),
        contentAlignment = if (isEdit) Alignment.CenterStart else Alignment.CenterEnd
    )
    {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxHeight()
                .width(70.dp)
        ) {
            Icon(
                imageVector = if (isEdit) Icons.Default.Edit else Icons.Default.Delete,
                contentDescription = if (isEdit) "Edit" else "Delete"
            )
        }
    }
}
