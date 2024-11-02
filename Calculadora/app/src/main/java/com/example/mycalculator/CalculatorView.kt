package com.example.mycalculator

import CalculadoraBrain
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycalculator.ui.components.CalcButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource

@Composable
fun CalculatorView(modifier: Modifier = Modifier) {

    var display by remember { mutableStateOf("0") }
    var userIsInTheMiddleOfTyping by remember { mutableStateOf(false) }
    var calculadoraBrain = remember { CalculadoraBrain() }
    var shouldClearDisplay by remember { mutableStateOf(false) }

    fun getDisplay(): Double {
        return display.toDouble()
    }

    fun setDisplay(value: Double) {
        val roundedValue = String.format("%.6f", value).toDouble() // Arredonda para 6 casas decimais

        display = if (roundedValue % 1 == 0.0) {
            roundedValue.toInt().toString()
        } else {
            roundedValue.toString()
        }
    }

    val onNumPress: (String) -> Unit = { num ->
        if (shouldClearDisplay) {
            // Limpa o visor e reinicia `shouldClearDisplay`
            display = if (num == ".") "0." else num // Garante que "." inicie com "0."
            shouldClearDisplay = false
            userIsInTheMiddleOfTyping = true
        } else if (!(num == "." && display.contains("."))) {
            if (display == "0" && num != ".") {
                // Substitui o "0" inicial se for um número diferente de "."
                display = num
            } else if (num == "." && (display.isEmpty() || display == "0")) {
                // Garante que "." inicie com "0."
                display = "0."
            } else {
                display += num
            }
            userIsInTheMiddleOfTyping = true
        }
    }

    val onOperationPressed: (String) -> Unit = { op ->
        if (userIsInTheMiddleOfTyping) {
            setDisplay(calculadoraBrain.doOperation(getDisplay()))
            userIsInTheMiddleOfTyping = false
        }
        calculadoraBrain.operand = getDisplay()
        calculadoraBrain.operation = CalculadoraBrain.Operation.getOp(op)
        shouldClearDisplay = true // Define para limpar o visor ao iniciar nova entrada numérica após a operação
    }

    // Função para o botão "="
    val onEqualPress: () -> Unit = {
        setDisplay(calculadoraBrain.doOperation(getDisplay()))
        calculadoraBrain.operation = null
        userIsInTheMiddleOfTyping = false
        shouldClearDisplay = true // Define para limpar o visor na próxima entrada numérica
    }

    // Função para o botão "AC" (Clear)
    val onClearPress: () -> Unit = {
        display = "0"
        calculadoraBrain.operation = null
        calculadoraBrain.operand = 0.0
        userIsInTheMiddleOfTyping = false
        shouldClearDisplay = false
    }

    /* // Função para o botão "+/-" (Inverter sinal)
    val onToggleSignPress: () -> Unit = {
        val currentValue = getDisplay()
        setDisplay(-currentValue)
    } */


    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = display,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.displayLarge
        )

        Row {
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "AC",
                onButtonPress = { onClearPress() }
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "±",
                //onButtonPress = { onToggleSignPress() }
                onButtonPress = onOperationPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "%",
                onButtonPress = onOperationPressed
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "/",
                isOperation = true,
                onButtonPress = onOperationPressed
            )
        }
        // Linhas de números e operações
        Row {
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "7",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "8",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "9",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "*",
                isOperation = true,
                onButtonPress = onOperationPressed
            )
        }
        Row {
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "4",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "5",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "6",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "-",
                isOperation = true,
                onButtonPress = onOperationPressed
            )
        }
        Row {
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "1",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "2",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "3",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "+",
                isOperation = true,
                onButtonPress = onOperationPressed
            )
        }
        Row {
            /* Icone no lugar de um botao
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu, // Escolha o ícone desejado
                    contentDescription = "Options",
                    modifier = Modifier.fillMaxSize(0.8f)
                )
            }*/
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "√",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "0",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = ".",
                onButtonPress = onNumPress
            )
            CalcButton(
                modifier = Modifier.weight(1f),
                label = "=",
                isOperation = true,
                onButtonPress = { onEqualPress() }
            )
        }
        // Adicione a imagem abaixo do visor
        Image(
            painter = painterResource(id = R.drawable.images), // substitua com o id da sua imagem
            contentDescription = "Imagem Abaixo do Visor",
            modifier = Modifier
                .fillMaxWidth()
                .height(1000.dp) // Defina a altura conforme necessário
                .padding(top = 20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorView()
}
