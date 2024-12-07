package com.victor.listacompras.repositories


import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.victor.listacompras.TAG
import com.victor.listacompras.models.ListItem

object ListItemRepository {

    val db = Firebase.firestore

    fun add(listItem: ListItem, onAddListSuccess: () -> Unit) {


        var currentUser = Firebase.auth.currentUser

        if (currentUser == null) {
            Log.e("ListItemRepository", "User not logged in")
            return
        }

        listItem.owners = arrayListOf(currentUser.uid)

        db.collection("listTypes")
            .add(listItem)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }

    fun getAll(onSuccess: (List<ListItem>) -> Unit) {
        val currentUserUid = Firebase.auth.currentUser?.uid
        Log.d("TAG", "Current User UID: $currentUserUid")
        if (currentUserUid == null) {
            Log.e("ListItemRepository", "User not logged in")
            return
        }

        db.collection("listTypes")
            .whereArrayContains("owners", currentUserUid)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("ListItemRepository", "Error fetching list items", error)
                    return@addSnapshotListener
                }

                if (value != null && !value.isEmpty) {
                    for (document in value.documents) {
                        Log.d("TAG", "Fetched Document: ${document.id}, Data: ${document.data}")
                    }
                } else {
                    Log.d("TAG", "No documents found for current user UID: $currentUserUid")
                }

                val listItems = value?.documents?.mapNotNull { document ->
                    val item = document.toObject(ListItem::class.java)
                    item?.apply { docId = document.id }
                }

                listItems?.let { onSuccess(it) }
            }
    }

    fun updateListItem(
        listItem: ListItem,
        newOwnerUid: String? = null,
        onUpdateSuccess: () -> Unit,
        onUpdateFailure: (Exception) -> Unit
    ) {
        if (listItem.docId.isNullOrEmpty()) {
            Log.e("ListItemRepository", "Document ID is null or empty")
            onUpdateFailure(IllegalArgumentException("Document ID is null or empty"))
            return
        }

        val documentRef = db.collection("listTypes").document(listItem.docId!!)

        val updates = mutableMapOf<String, Any>(
            "name" to (listItem.name ?: ""),
            "description" to (listItem.description ?: "")
        )

        // Se um novo owner for fornecido, adicione ao campo "owners"
        newOwnerUid?.let {
            updates["owners"] = FieldValue.arrayUnion(it)
        }

        Log.d("ListItemRepository", "Updating document with updates: $updates")

        documentRef.update(updates)
            .addOnSuccessListener {
                Log.d("ListItemRepository", "List item and owner successfully updated")
                onUpdateSuccess()
            }
            .addOnFailureListener { exception ->
                Log.e("ListItemRepository", "Error updating list item and owner", exception)
                onUpdateFailure(exception)
            }
    }
}