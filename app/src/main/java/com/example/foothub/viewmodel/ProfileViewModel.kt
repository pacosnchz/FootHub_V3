package com.example.foothub.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.foothub.datastore.AppTheme
import com.example.foothub.datastore.UserPreferences
import com.example.foothub.datastore.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = UserPreferencesRepository(application)

    /** Estado de las preferencias del usuario, expuesto como StateFlow. */
    val preferences: StateFlow<UserPreferences> = repo.userPreferencesFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UserPreferences()
    )

    fun saveUsername(name: String) {
        viewModelScope.launch { repo.saveUsername(name) }
    }

    fun saveTheme(theme: AppTheme) {
        viewModelScope.launch { repo.saveTheme(theme) }
    }
}