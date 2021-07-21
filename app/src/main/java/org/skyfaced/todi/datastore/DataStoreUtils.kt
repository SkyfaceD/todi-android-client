package org.skyfaced.todi.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import org.skyfaced.todi.datastore.user.UserPreferencesSerializer

private const val DATA_STORE_FILE_NAME = "preferences.pb"

private const val USER_PREFERENCES_NAME = "user_preferences"
val Context.userPreferences: DataStore<UserPreferences> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = UserPreferencesSerializer
)