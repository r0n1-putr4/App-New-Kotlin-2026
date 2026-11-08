package com.kotlin.basic.appberitatrpl3d.models

data class ArticleModel(
    val success: Boolean,
    val message: String,
    val listArticle: MutableList<Article>
){
    data class Article(
        val id: String,
        val user_id: String,
        val fullname: String,
        val judul: String,
        val isi: String,
        val cover: String,
        val rating_: Float,
        val tanggal_publish_: String
    )
}
