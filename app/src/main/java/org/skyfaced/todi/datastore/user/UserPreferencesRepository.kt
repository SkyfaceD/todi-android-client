package org.skyfaced.todi.datastore.user

import kotlinx.coroutines.flow.Flow
import org.skyfaced.todi.datastore.UserPreferences

interface UserPreferencesRepository {
    suspend fun saveId(id: String)

    val userPreferencesFlow: Flow<UserPreferences>
}