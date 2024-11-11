package com.victord.noticeworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.victord.noticeworld.ui.theme.NoticeWorldTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoticeWorldTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { CustomTopAppBar() },     // Barra superior
                    bottomBar = { CustomBottomAppBar(navController) } // Barra inferior
                ) { innerPadding ->
                    // Aplica o innerPadding ao conteúdo dentro do NavHost
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable(route = "home") {
                            InicialView(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding), // Aplica padding apenas ao conteúdo
                                onArticleClick = { articleUrl ->
                                    navController.navigate("article/$articleUrl")
                                }
                            )
                        }
                        composable(route = "article/{url}") { backStackEntry ->
                            val url = backStackEntry.arguments?.getString("url")
                            url?.let { articleUrl ->
                                PubDetailView(
                                    url = articleUrl,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}




