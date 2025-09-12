package com.example.elsmovieapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.elsmovieapp.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _isLoggedIn = MutableLiveData(false)
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn


    private val _username = MutableLiveData<String?>()
    val username: LiveData<String?> get() = _username

    fun signUp(email: String, password: String, fullName: String) {
        _loading.value = true
        repository.signUp(email, password, fullName) { success, errorMsg ->
            _loading.value = false
            if (success) _isLoggedIn.value = true
            else _error.value = errorMsg
        }
    }

    fun login(email: String, password: String) {
        _loading.value = true
        repository.login(email, password) { success, errorMsg ->
            _loading.value = false
            if (success) _isLoggedIn.value = true
            else _error.value = errorMsg
        }
    }

    fun fetchUserName() {
        repository.fetchUserName { name ->
            _username.value = name
        }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        _isLoggedIn.value = false
    }

}

class AuthViewModelFactory(
    private val repository: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
