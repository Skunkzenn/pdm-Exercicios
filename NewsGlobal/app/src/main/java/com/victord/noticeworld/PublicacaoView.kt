package com.victord.noticeworld

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.util.Date


@Composable
fun PubliLineView(modifier: Modifier = Modifier, article: Publicacao) {
    Row(
        modifier = modifier
            .padding(8.dp) // Espaçamento externo
            .border(3.dp, Color.White, RoundedCornerShape(8.dp)) // Borda externa ao redor do artigo
            .padding(8.dp)
            .background(Color.Black)// Espaçamento interno da borda
    ) {
        article.urlToImage?.let {
            AsyncImage(
                modifier = Modifier
                    .width(180.dp)
                    .height(150.dp)
                    .border(1.dp, Color.White, RoundedCornerShape(8.dp)),
                model = it,
                contentDescription = "Article Image"
            )
        } ?: run {
            Image(
                modifier = Modifier
                    .width(180.dp)
                    .height(150.dp),
                painter = painterResource(id = R.drawable.baseline_photo_camera_back_24),
                contentDescription = "Article Image",
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
                .border(2.dp, Color.White, AbsoluteCutCornerShape(8.dp))
        ) {
            //Trata Aspecto Visual do Título da noticia
            Text(
                text = article.titulo ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White, // Cor do texto em branco
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black) // Fundo preto
                    .padding(4.dp),
                fontSize = 20.sp, // Aumenta o tamanho do título
                fontWeight = FontWeight.Bold
            )

            //Trata Aspecto Visual do Comentário da Noticia
            Text(
                text = article.descricao ?: "",
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = Color.Red, // Cor do texto em branco
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black) // Fundo preto
                    .padding(4.dp),
                fontSize = 20.sp, // Aumenta o tamanho da descrição
                fontWeight = FontWeight.Bold
            )

            //Trata Aspecto Visual da Data
            Text(
                text = article.data?.toStringDate() ?: "",
                color = Color.Yellow, // Cor do texto em branco
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black) // Fundo preto
                    .padding(4.dp)
                    .padding(top = 8.dp),
                fontSize = 14.sp, // Aumenta o tamanho da data
            )
        }
    }
}
