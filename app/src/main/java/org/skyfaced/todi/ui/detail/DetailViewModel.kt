package org.skyfaced.todi.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.skyfaced.todi.database.entities.TaskEntity
import org.skyfaced.todi.models.task.Task
import org.skyfaced.todi.repositories.task.TaskRepository

class DetailViewModel(private val repository: TaskRepository) : ViewModel() {
    private val _saveNotification = MutableLiveData<Task>()
    val saveNotification: LiveData<Task> get() = _saveNotification

    fun save(taskEntity: TaskEntity) {
        viewModelScope.launch {
            _saveNotification.value = repository.saveTask(taskEntity)
        }
    }
}