package com.victor.listacompras

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter

@Composable
fun ProfileView(userViewModel: UserViewModel) {
    val userProfile by userViewModel.userProfile.observeAsState()

    userProfile?.let { profile ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            profile.photoUrl?.let { uri ->
                Image(
                    painter = rememberImagePainter(uri),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(128.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Nome: ${profile.displayName}", fontSize = 20.sp)
            Text(text = "Email: ${profile.email}", fontSize = 16.sp)
        }
    }
}
