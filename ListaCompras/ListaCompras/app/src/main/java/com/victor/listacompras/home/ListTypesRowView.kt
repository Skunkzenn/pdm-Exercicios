package com.victor.listacompras.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.victor.listacompras.models.ListItem
import com.victor.listacompras.ui.theme.ListaComprasTheme

@Composable
fun ListTypeRowView(
    modifier: Modifier = Modifier,
    listItem: ListItem,
    onItemClick: (ListItem) -> Unit = {},
) {
    val backgroundColor = MaterialTheme.colorScheme.surface
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(16.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .clickable { onItemClick(listItem) },
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = listItem.name ?: "",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth(),
            color = onSurfaceColor,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = listItem.description ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = onSurfaceColor.copy(alpha = 0.7f),
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListTypeRowViewPreview(){
    ListaComprasTheme() {
        ListTypeRowView(listItem =  ListItem("",
            "Compras de casa",
            "As compras que são para casa",
            null)
        )
    }
}