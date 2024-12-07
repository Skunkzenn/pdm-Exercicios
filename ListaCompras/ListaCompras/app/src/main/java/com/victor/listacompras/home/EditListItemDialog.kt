package com.victor.listacompras.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.victor.listacompras.models.ListItem
import com.victor.listacompras.models.User
@Composable
fun EditListItemDialog(
    listItem: ListItem,
    users: List<User>,
    onDismiss: () -> Unit,
    onSave: (ListItem, String?) -> Unit
) {
    var name by remember { mutableStateOf(listItem.name ?: "") }
    var description by remember { mutableStateOf(listItem.description ?: "") }
    var selectedOwner by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit List Item") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text("Select Owner", style = MaterialTheme.typography.bodyMedium)
                LazyColumn {
                    items(users) { user ->
                        Button(
                            onClick = {
                                selectedOwner = user.uid
                                Log.d("TAG", "Button Clicked: ${user.name}, Selected Owner UID: $selectedOwner")
                            },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            Text("${user.name ?: user.email ?: "Unknown User"} (${user.uid})")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                selectedOwner?.let {
                    Text("Selected Owner: ${users.find { user -> user.uid == it }?.name ?: "Unknown"}")
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                Log.d("TAG", "Saving with selectedOwner: $selectedOwner")
                onSave(listItem.copy(name = name, description = description), selectedOwner)
                onDismiss()
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
