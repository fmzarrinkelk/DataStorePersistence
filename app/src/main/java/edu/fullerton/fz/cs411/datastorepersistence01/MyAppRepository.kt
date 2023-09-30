package edu.fullerton.fz.cs411.datastorepersistence01

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class MyAppRepository private constructor(private val dataStore: DataStore<Preferences>) {

    private val counterKey = intPreferencesKey("counter")

    val counter: Flow<Int> = this.dataStore.data.map { prefs ->
        prefs[counterKey] ?: INITIAL_COUNTER_VALUE
    }

    suspend fun saveCounter(value: Int) {
        saveIntValue(counterKey, value)
    }

    private suspend fun saveIntValue(key: Preferences.Key<Int>, value: Int) {
        this.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    companion object {
        private const val PREFERENCES_DATA_FILE_NAME = "MyDataStoreFile"
        private var singleInstanceOfMyAppRepository: MyAppRepository? = null
        fun initialize(context: Context) {
            Log.d(LOG_TAG, "initializing MyAppRepository")
            if (singleInstanceOfMyAppRepository == null) {
                val dataStore = PreferenceDataStoreFactory.create {
                    context.preferencesDataStoreFile(PREFERENCES_DATA_FILE_NAME)
                }
                singleInstanceOfMyAppRepository = MyAppRepository(dataStore)
            }
        }
        fun getRepository(): MyAppRepository {
            Log.d(LOG_TAG, "getting MyAppRepository")
            return singleInstanceOfMyAppRepository ?: throw IllegalStateException("singleInstanceOfMyAppRepository is not initialized yet")
        }
    }
}