package org.skyfaced.todi.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.skyfaced.todi.models.user.User
import org.skyfaced.todi.repositories.user.UserRepository

class SignUpViewModel(private val repository: UserRepository) : ViewModel() {
    private val _visibleGroup = MutableLiveData(true)
    val visibleGroup: LiveData<Boolean> get() = _visibleGroup

    private val _registration = MutableSharedFlow<User>()
    val registration = _registration.asSharedFlow()

    fun toggleGroup() {
        _visibleGroup.value = !visibleGroup.value!!
    }

    suspend fun isUsernameFree(username: String) = flow {
        emit(repository.isUsernameAvailable(username))
    }.first()

    fun registerUser(username: String, password: String) {
        viewModelScope.launch {
            _registration.emit(repository.createUser(username, password))
        }
    }
}