package org.skyfaced.todi.ui.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.skyfaced.todi.models.user.User
import org.skyfaced.todi.repositories.user.UserRepository

class SignInViewModel(private val repository: UserRepository) : ViewModel() {
    private val _user = MutableSharedFlow<User>()
    val user = _user.asSharedFlow()

    fun findUserById(id: String) {
        viewModelScope.launch {
            _user.emit(repository.getById(id))
        }
    }

    suspend fun validateCredentials(username: String, password: String): Boolean {
        return flow<Boolean> { repository.checkCredentials(username, password) }.first()
    }
}