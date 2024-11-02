package com.example.mycalculator.ui.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycalculator.R
import com.example.mycalculator.ui.theme.Orange
import com.example.mycalculator.ui.theme.Pink40
import com.example.mycalculator.ui.theme.CalculatorTheme


@Composable
fun CalcButton(modifier: Modifier = Modifier,
               label : String = "",
               isOperation : Boolean = false,
               onButtonPress : (String) -> Unit ) {
    Button(
        modifier = modifier
            .aspectRatio(1f)
            .padding(4.dp),
        colors = ButtonDefaults.run {
            buttonColors(
                if (isOperation) colorResource(id = R.color.button_operation)
                else colorResource(id = R.color.button_number)
            )
        },
        onClick = { onButtonPress(label) }
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 32.sp
            ),
            color = colorResource(id = R.color.white), // Cor do texto

        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalcButtonPreview(){
    CalculatorTheme {
        CalcButton(
            label = "0"
        ){ }
    }
}
