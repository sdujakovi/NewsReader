package com.example.newsreaderfactory.database

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
    context: Context
) {

    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = applicationContext.createDataStore(
            name = "factory_datastore"
        )
    }

    val last_load: Flow<Long?>?
        get() = dataStore.data.map { preferences ->
            preferences[TIME]
        }

    suspend fun saveLastLoad(last_load: Long) {
        dataStore.edit{ preferences ->
            preferences[TIME] = last_load
        }
    }

    companion object {
        private val TIME = preferencesKey<Long>("key_auth")
    }
}