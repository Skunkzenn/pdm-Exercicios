package com.victord.noticeworld

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun InicialView(
    navController: NavController,
    modifier: Modifier = Modifier,
    onArticleClick: (String) -> Unit = {}
) {
    val viewModel: InicialViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        MatrixBackground(modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize()) {
            when {
                uiState.isLoading -> LoadingView()
                uiState.errorMessage.isNotEmpty() -> ErrorView(errorMessage = uiState.errorMessage)
                else -> ArticleList(articles = uiState.articles, onArticleClick = onArticleClick)
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.fetchArticles()
    }
}


@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Loading...",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun ErrorView(errorMessage: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = errorMessage,
            color = Color.Red,
            fontSize = 20.sp
        )
    }
}

@Composable
fun ArticleList(
    articles: List<Publicacao>,
    onArticleClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(items = articles) { _, item ->
            PubliLineView(
                modifier = Modifier.clickable {
                    onArticleClick(item.url.encodeURL())
                },
                article = item
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun InicialViewPreview() {
    val navController = rememberNavController() // Cria uma instância de NavController para o preview
    // Chama InicialView com dados simulados
    InicialView(
        navController = navController,
        modifier = Modifier,
        onArticleClick = { /* Ação simulada para clique no artigo */ }
    )
}

