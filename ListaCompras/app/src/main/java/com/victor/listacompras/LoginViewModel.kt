package com.victor.listacompras

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



data class LoginState(
    var username: String = "",
    var password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoggedIn: Boolean = false
)

class LoginViewModel : ViewModel() {

    var state = mutableStateOf(LoginState())
        private set

    private val auth = Firebase.auth

    private val username
        get() = state.value.username
    private val password
        get() = state.value.password

    fun onUsernameChange(newValue: String) {
        state.value = state.value.copy(username = newValue)
    }

    fun onPasswordChange(newValue: String) {
        state.value = state.value.copy(password = newValue)
    }

    fun login(onLoginSuccess: () -> Unit) {
        if (username.isEmpty()) {
            state.value = state.value.copy(error = "Email is required")
            return
        }
        if (password.isEmpty()) {
            state.value = state.value.copy(error = "Password is required")
            return
        }

        state.value = state.value.copy(isLoading = true)
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                state.value = state.value.copy(isLoading = false)
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    state.value = state.value.copy(error = null, isLoggedIn = true)
                    onLoginSuccess()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    state.value = state.value.copy(error = task.exception?.message)
                }
            }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}
