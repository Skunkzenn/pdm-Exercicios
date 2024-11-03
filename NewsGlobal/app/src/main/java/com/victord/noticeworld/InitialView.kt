package com.victord.noticeworld

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.random.Random


@Composable
fun InicialView(
    modifier: Modifier = Modifier,
    onArticleClick: (String) -> Unit = {}
) {
    MatrixBackground(modifier = Modifier.fillMaxSize())

    // Cria uma instância do ViewModel para gerenciar o estado da interface
    val viewModel: InicialViewModel = viewModel()

    // Obtemos o estado atual da UI usando collectAsState para observar mudanças
    val uiState by viewModel.uiState.collectAsState()

    // Conteúdo da aplicação sobre o fundo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 0.dp) // Garante que o conteúdo esteja alinhado ao topo sem padding
        ) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Loading...",
                            color = androidx.compose.ui.graphics.Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
                uiState.errorMessage.isNotEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.errorMessage,
                            color = androidx.compose.ui.graphics.Color.Red,
                            fontSize = 20.sp
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 0.dp) // Remove qualquer padding adicional
                    ) {
                        itemsIndexed(items = uiState.articles) { _, item ->
                            PubliLineView(
                                modifier = Modifier.clickable {
                                    onArticleClick(item.url.encodeURL())
                                },
                                article = item
                            )
                        }
                    }
                }
            }
        }

    LaunchedEffect(key1 = true) {
        viewModel.fetchArticles()
    }
}
