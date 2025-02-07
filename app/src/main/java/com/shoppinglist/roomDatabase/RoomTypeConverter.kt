package com.shoppinglist.roomDatabase

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.TypeConverter
import java.net.URL

class RoomTypeConverter {
    @TypeConverter
    fun fromIcon(icon: ImageVector?): String {
        return when (icon) {
            Icons.Default.Home -> "ic_home"
            Icons.Default.Favorite -> "ic_favorite"
            Icons.Default.Settings -> "ic_settings"
            else -> "ic_default"
        }
    }

    @TypeConverter
    fun toIcon(iconName: String): ImageVector {
        return when (iconName) {
            "ic_home" -> Icons.Default.Home
            "ic_favorite" -> Icons.Default.Favorite
            "ic_settings" -> Icons.Default.Settings
            else -> Icons.Default.Info
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