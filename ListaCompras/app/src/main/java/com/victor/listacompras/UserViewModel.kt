package com.victor.listacompras

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    private val _userProfile = MutableLiveData<UserProfile>()
    val userProfile: LiveData<UserProfile> = _userProfile

    fun setUserProfile(displayName: String?, email: String?, photoUrl: Uri?) {
        _userProfile.value = UserProfile(displayName, email, photoUrl)
    }
}

data class UserProfile(
    val displayName: String?,
    val email: String?,
    val photoUrl: Uri?
)
