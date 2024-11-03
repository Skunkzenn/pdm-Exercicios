package com.victord.noticeworld

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject


data class ArticleState(
    val articles: ArrayList<Publicacao> = arrayListOf(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
)

class InicialViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ArticleState())
    val uiState: StateFlow<ArticleState> = _uiState.asStateFlow()

    fun fetchArticles() {
        _uiState.value = ArticleState(
            isLoading = true
        )
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://newsapi.org/v2/everything?q=tesla&from=2024-10-03&sortBy=publishedAt&apiKey=fdff7594f550460291971dde2d25fbbf")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                _uiState.value = ArticleState(
                    errorMessage = e.message ?: "",
                    isLoading = false
                )
            }


            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val articlesResult = arrayListOf<Publicacao>()
                    val result = response.body?.string() ?: ""
                    val jsonObject = JSONObject(result)

                    if (jsonObject.getString("status") == "ok") {
                        val articlesArray = jsonObject.getJSONArray("articles")
                        for (index in 0 until articlesArray.length()) {
                            val articleObject = articlesArray.getJSONObject(index)
                            val article = Publicacao.fromJson(articleObject)
                            articlesResult.add(article)
                        }

                        _uiState.value = ArticleState(
                            articles = articlesResult,
                            isLoading = false
                        )
                    } else {
                        _uiState.value = ArticleState(
                            errorMessage = "Erro ao buscar artigos",
                            isLoading = false
                        )
                    }
                }
            }
        })
    }
}