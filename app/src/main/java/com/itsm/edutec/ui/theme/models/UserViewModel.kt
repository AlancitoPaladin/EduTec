package com.itsm.edutec.ui.theme.models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewModel : ViewModel() {
    private val _userRole = MutableStateFlow<UserRole>(UserRole.Student)
    val userRole: StateFlow<UserRole> = _userRole

    fun setUserRole(role: UserRole) {
        _userRole.value = role
    }
}