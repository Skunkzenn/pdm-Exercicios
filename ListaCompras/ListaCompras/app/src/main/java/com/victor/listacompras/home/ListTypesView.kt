package com.victor.listacompras.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.victor.listacompras.R
import com.victor.listacompras.models.ListItem
import com.victor.listacompras.models.User

@Composable
fun ListTypesView(
    modifier: Modifier = Modifier,
    onNavigateToAddList: () -> Unit
) {
    val viewModel: ListTypesViewModel = remember { ListTypesViewModel() }
    val state by viewModel.state

    var selectedItem by remember { mutableStateOf<ListItem?>(null) }
    var users by remember { mutableStateOf<List<User>>(emptyList()) }

    // Conteúdo principal da view
    ListTypesViewContent(
        modifier = modifier,
        state = state,
        onNavigateToAddList = onNavigateToAddList,
        onEditListType = { item ->
            selectedItem = item
            viewModel.loadUsers(
                onUsersLoaded = { loadedUsers ->
                    Log.d("TAG", "Users Loaded: ${loadedUsers.joinToString { it.uid }}")
                    users = loadedUsers
                },
                onError = { exception ->
                    Log.e("TAG", "Error loading users", exception)
                }
            )
        }
    )

    selectedItem?.let { item ->
        EditListItemDialog(
            listItem = item,
            users = users,
            onDismiss = { selectedItem = null },
            onSave = { updatedItem, newOwnerUid ->
                viewModel.editListType(updatedItem, newOwnerUid)
                selectedItem = null
            }
        )
    }

    // Carrega a lista inicial ao iniciar a Composable
    LaunchedEffect(key1 = Unit) {
        viewModel.loadListTypes()
    }
}



@Composable
fun ListTypesViewContent(
    modifier: Modifier = Modifier,
    state: ListState,
    onNavigateToAddList: () -> Unit = {},
    onEditListType: (ListItem) -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                items(state.listItems) { item ->
                    ListTypeRowView(
                        listItem = item,
                        onItemClick = { selectedItem ->
                            onEditListType(selectedItem)
                        }
                    )
                }
            }
        }

        // Botão para adicionar nova lista
        Button(
            modifier = Modifier
                .padding(16.dp)
                .height(80.dp)
                .width(80.dp),
            onClick = onNavigateToAddList
        ) {
            Image(
                modifier = Modifier.size(60.dp),
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = "Add List",
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}
