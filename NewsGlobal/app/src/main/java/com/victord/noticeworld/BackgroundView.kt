package com.victord.noticeworld

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.sp
import kotlin.random.Random
import android.graphics.Paint
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.unit.dp

@Composable
fun MatrixBackground(modifier: Modifier = Modifier) {
    val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

    // Controle de largura/densidade horizontal
    val numColumns = 40 // Número total de colunas de letras
    val columnWidths = remember {
        (0 until numColumns).map {
            Random.nextInt(30, 80).toFloat()
        }
    } // Largura aleatória para cada coluna

    // Posições horizontais aleatórias para cada coluna (soma das larguras anteriores)
    val density = remember {
        var xPosition = 0f
        columnWidths.map {
            val position = xPosition
            xPosition += it // Avança o x com a largura da coluna atual
            position
        }
    }

    // Posições verticais aleatórias para letras
    val randomPositions = remember { density.map { Random.nextInt(0, 100) } }
    val transition = rememberInfiniteTransition()

    // Animação para a posição vertical
    val yOffset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 2000f, // Valor para cobrir altura da tela
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Canvas(modifier = modifier.fillMaxSize().width(1000.dp).height(500.dp)) {
        // Fundo preto
        drawRect(
                color = Color.Black,
                size = size
        )

        // Letras verdes caindo em colunas de larguras variadas
        for (i in density.indices) {
            val xPos = density[i] // Posição x da coluna
            val yPos = (randomPositions[i] * 50 + yOffset) % size.height // Posição y animada

            // Desenha várias letras em cada posição x para aumentar a densidade
            for (j in 0..20) { // Desenha 5 letras em alturas variadas para cada coluna
                val letterYPos =
                    yPos - j * 40f // Ajusta a posição vertical com espaçamento entre letras
                drawContext.canvas.nativeCanvas.drawText(
                    letters.random().toString(),
                    xPos,
                    letterYPos,
                    Paint().apply {
                        color = android.graphics.Color.WHITE
                        textSize =
                            columnWidths[i] // Tamanho da letra proporcional à largura da coluna
                    }
                )
            }
        }
    }
}