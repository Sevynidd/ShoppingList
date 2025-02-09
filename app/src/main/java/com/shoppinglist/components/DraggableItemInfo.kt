package com.shoppinglist.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

data class DraggableItemInfo(
    var headline: String,
    var supporting: String = "",
    var trailing: String = "",
    var overline: String = "",
    var isItem: Boolean = true,
    var isChecked: Boolean = false,
    var listIcon: ImageVector = Icons.Default.ShoppingCart
)
