package com.victord.noticeworld

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar() {
    TopAppBar(
        modifier = Modifier.height(80.dp), // Define uma altura menor para a TopAppBar
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.Yellow,

        ),
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(), // Garante que o Box ocupe toda a largura
                contentAlignment = Alignment.Center // Centraliza o conteúdo dentro do Box
            ) {
                Text(
                    text = " - - - Notices World - - -",
                    color = Color.Yellow,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    textAlign = TextAlign.Center
                )
            }
        }

    )
}

@Composable
fun CustomBottomAppBar(navController: NavController) {
    BottomAppBar(
        modifier = Modifier.height(48.dp),
        containerColor = Color.DarkGray
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
            IconButton(onClick = { /* Ação para o segundo botão */ }) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Favoritos",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
            IconButton(onClick = { /* Ação para o terceiro botão */ }) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Configurações",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}
