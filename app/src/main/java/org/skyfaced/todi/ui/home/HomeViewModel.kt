package org.skyfaced.todi.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.skyfaced.todi.models.task.Task
import org.skyfaced.todi.repositories.task.TaskRepository

class HomeViewModel(private val repository: TaskRepository) : ViewModel() {
    var firstVisibleItemPosition: Int = 0

    private val _tasks: MutableStateFlow<List<Task>> = MutableStateFlow(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    init {
        fetchTasks()
    }

    fun fetchTasks() {
        viewModelScope.launch {
            _tasks.emit(repository.getAll())
        }
    }
}