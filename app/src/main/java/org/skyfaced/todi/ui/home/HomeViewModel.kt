package org.skyfaced.todi.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.skyfaced.todi.datastore.UserPreferences
import org.skyfaced.todi.datastore.user.UserPreferencesRepository
import org.skyfaced.todi.models.task.Task
import org.skyfaced.todi.repositories.task.TaskRepository

class HomeViewModel(
    private val taskRepository: TaskRepository,
    userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    var firstVisibleItemPosition: Int = 0

    private val _tasks: MutableStateFlow<List<Task>> = MutableStateFlow(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    init {
        fetchTasks()
    }

    fun fetchTasks() {
        viewModelScope.launch {
            _tasks.emit(taskRepository.getAll())
        }
    }

    val userPreferencesId: Flow<String> =
        userPreferencesRepository.userPreferencesFlow.map(UserPreferences::getId)
}