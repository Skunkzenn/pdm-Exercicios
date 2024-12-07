package com.victor.listacompras.repositories

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.victor.listacompras.models.ListItem
import com.victor.listacompras.models.User

object UserRepository {
    val db = Firebase.firestore

    fun getUsers(onSuccess: (List<User>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("users")
            .get()
            .addOnSuccessListener { snapshot ->
                val users = snapshot.documents.mapNotNull { it.toObject(User::class.java) }
                onSuccess(users)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}
