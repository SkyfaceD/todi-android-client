package org.skyfaced.todi.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import org.skyfaced.todi.repositories.task.TaskRepository

class HomeViewModel(private val repository: TaskRepository) : ViewModel() {
    var firstVisibleItemPosition: Int = 0

    val tasks = liveData { emit(repository.getAll()) }
}