package com.shoppinglist.components

data class DraggableItemInfo(
    var headline: String,
    var supporting: String = "",
    var trailing: String = "",
    var overline: String = "",
    var isItem: Boolean = true,
    var isChecked: Boolean = false
)
