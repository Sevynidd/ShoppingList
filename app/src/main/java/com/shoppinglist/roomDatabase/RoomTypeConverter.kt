package com.shoppinglist.roomDatabase

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.TypeConverter
import java.net.URL

class RoomTypeConverter {
    @TypeConverter
    fun fromIcon(icon: ImageVector): String {
        return when (icon) {
            Icons.Default.ShoppingCart -> "ic_shoppingCart"
            Icons.Default.AcUnit -> "ic_acUnit"
            Icons.Default.AttachMoney -> "ic_attachMoney"
            Icons.Default.Book -> "ic_book"
            else -> "ic_shoppingCart"
        }
    }

    @TypeConverter
    fun toIcon(iconName: String): ImageVector {
        return when (iconName) {
            "ic_shoppingCart" -> Icons.Default.ShoppingCart
            "ic_acUnit" -> Icons.Default.AcUnit
            "ic_attachMoney" -> Icons.Default.AttachMoney
            "ic_book" -> Icons.Default.Book
            else -> Icons.Default.ShoppingCart
        }
    }

    @TypeConverter
    fun fromUrl(url: URL?): String? {
        return url?.toString()
    }

    @TypeConverter
    fun toUrl(urlString: String?): URL? {
        return urlString?.let {
            try {
                URL(it)
            } catch (e: Exception) {
                null
            }
        }
    }
}