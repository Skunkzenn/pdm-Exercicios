package com.victor.listacompras.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.victor.listacompras.models.ListItem
import com.victor.listacompras.models.User
import com.victor.listacompras.repositories.ListItemRepository
import com.victor.listacompras.repositories.UserRepository

data class ListState(
    val listItems : List<ListItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ListTypesViewModel : ViewModel() {

    var state = mutableStateOf(ListState())
        private set

    fun loadUsers(onUsersLoaded: (List<User>) -> Unit, onError: (Exception) -> Unit) {
        UserRepository.getUsers(
            onSuccess = { users -> onUsersLoaded(users) },
            onFailure = { exception -> onError(exception) }
        )
    }

    fun loadListTypes() {
        ListItemRepository.getAll { listItems ->
            state.value = state.value.copy(listItems = listItems)
            for (item in listItems) {
                Log.d("TAG", item.name ?: "no name")
            }
        }
    }

    fun editListType(listItem: ListItem, newOwnerUid: String?) {
        ListItemRepository.updateListItem(
            listItem = listItem,
            newOwnerUid = newOwnerUid,
            onUpdateSuccess = {
                Log.d("TAG", "List item and owner successfully updated!")
                loadListTypes()
            },
            onUpdateFailure = { exception ->
                Log.e("TAG", "Error updating list item and owner", exception)
            }
        )
    }

}
