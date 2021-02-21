package org.skyfaced.todi.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {

    private val _visibleGroup = MutableLiveData(true)
    val visibleGroup: LiveData<Boolean> get() = _visibleGroup

    fun toggleGroup() {
        _visibleGroup.value = !visibleGroup.value!!
    }

}