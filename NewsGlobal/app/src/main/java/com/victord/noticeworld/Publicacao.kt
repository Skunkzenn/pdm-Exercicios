package com.victord.noticeworld

import android.annotation.SuppressLint
import org.json.JSONObject
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date

fun String.encodeURL(): String {
    return URLEncoder.encode(this, "UTF-8")
}

@SuppressLint("SimpleDateFormat")
fun String.toDate(): Date? {
    val dateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss") // Exemplo: data com 'T' como separador
    return try {
        dateFormat.parse(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@SuppressLint("SimpleDateFormat")
fun Date.toStringDate(): String {
    val dateFormat = SimpleDateFormat("yyyy/MM/dd")
    return dateFormat.format(this)
}

@SuppressLint("SimpleDateFormat")
fun Date.toStringDateQuery(): String {
    val dateFormat = SimpleDateFormat("yyyy-dd-MM")
    return dateFormat.format(this)
}


data class Publicacao(
    var titulo: String?,
    var descricao: String?,
    var url: String,
    var urlToImage: String?,
    var data: Date?
) {
    companion object {
        fun fromJson(articleObject: JSONObject): Publicacao {
            val title = articleObject.optString("title")
            val description = articleObject.optString("description")
            val url = articleObject.optString("url")
            val urlToImage = articleObject.optString("urlToImage")
            val publishedAt = articleObject.optString("publishedAt").toDate()

            return Publicacao(title, description, url, urlToImage, publishedAt)
        }
    }
}

