package org.skyfaced.todi.datastore.user

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import org.skyfaced.todi.datastore.UserPreferences

class UserPreferencesRepositoryImpl(
    private val dataStore: DataStore<UserPreferences>
) : UserPreferencesRepository {
    override suspend fun saveId(id: String) {
        dataStore.updateData { preferences ->
            preferences.toBuilder()
                .setId(id)
                .build()
        }
    }

    override val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
}