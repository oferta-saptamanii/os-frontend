package com.example.os_frontend.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.os_frontend.firestore.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val repository: FirestoreRepository
) : ViewModel() {

    private val _splashState = MutableStateFlow<SplashState>(SplashState.Loading)
    val splashState: StateFlow<SplashState> = _splashState

    init {
        checkData()
    }

    private fun checkData() {
        viewModelScope.launch {
            try {
                val localDataExists = repository.hasLocalData()
                if (localDataExists) {
                    _splashState.value = SplashState.NavigateToMain
                } else {
                    _splashState.value = SplashState.Loading
                    repository.fetchAll()
                    _splashState.value = SplashState.NavigateToMain
                }
            } catch (e: Exception) {
                _splashState.value = SplashState.Error
            }
        }
    }

}

sealed class SplashState {
    data object Loading : SplashState()
    data object NavigateToMain : SplashState()
    data object Error : SplashState()
}

