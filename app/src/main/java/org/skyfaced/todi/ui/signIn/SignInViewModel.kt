package org.skyfaced.todi.ui.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.skyfaced.todi.datastore.user.UserPreferencesRepository
import org.skyfaced.todi.models.user.User
import org.skyfaced.todi.repositories.user.UserRepository

class SignInViewModel(
    private val userRepository: UserRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _user = MutableSharedFlow<User>()
    val user = _user.asSharedFlow()

    fun findUserById(id: String) {
        viewModelScope.launch {
            _user.emit(userRepository.getById(id))
        }
    }

    fun validateCredentials(username: String, password: String) = runBlocking {
        userRepository.checkCredentials(username, password)
    }

    fun saveIdByUsername(username: String) {
        viewModelScope.launch {
            val id = userRepository.getByUsername(username)?.id ?: ""
            userPreferencesRepository.saveId(id)
        }
    }
}