package com.example.aula_1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aula_1.ui.theme.Aula_1Theme

@Composable
fun Greet(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = "bb54",
            onValueChange = { newValue -> print(newValue) }
        )

        Button(onClick = {}) {
            Text(text = "Caralho!")
        }

        Text("Olá Mundo ${soma(90, 9)}")
    }
}

fun soma(a: Int, b: Int): Int{
    return a + b
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greet()
}