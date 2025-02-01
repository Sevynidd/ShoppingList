package com.shoppinglist.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreData(private val context: Context) {
    companion object {
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore("DataStore")
        //val API_KEY = stringPreferencesKey("api_key")
    }

    /*val getApiKey: Flow<String?> = context.datastore.data
        .map { preferences ->
            preferences[API_KEY] ?: ""
        }

    suspend fun saveApiKey(key: String) {
        context.datastore.edit { preferences ->
            preferences[API_KEY] = key
        }
    }*/
}