package hu.bme.photoapp.model

import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    private val repository = MainActivityRepository()

    companion object {
        var user: User = User("")
    }

    fun registerUser(registerUser: RegisterUser,
                     onSuccess: () -> Unit,
                     onError: (Throwable) -> Unit) {
        repository.registerUser(registerUser, onSuccess, onError)
    }

    fun loginUser(registerUser: RegisterUser,
                     onSuccess: (user: User) -> Unit,
                     onError: (Throwable) -> Unit) {
        repository.loginUser(registerUser, onSuccess, onError)
    }

    fun changeUser(u: User) {
        user = u
    }

}