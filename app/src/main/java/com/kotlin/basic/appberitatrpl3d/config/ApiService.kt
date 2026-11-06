package com.kotlin.basic.appberitatrpl3d.config

import com.kotlin.basic.appberitatrpl3d.models.ArticleModel
import com.kotlin.basic.appberitatrpl3d.models.ResponModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @GET("/api-berita-trpl3d/listArticle.php")
    fun getArticles(): Call<ArticleModel>

    @Multipart
    @POST("/api-berita-trpl3d/insertArticle.php")
    fun insertArticle(
        @Part("user_id") user_id: RequestBody,
        @Part("judul") judul: RequestBody,
        @Part("isi") isi: RequestBody,
        @Part cover: MultipartBody.Part,
        @Part("rating") rating: RequestBody

    ): Call<ResponModel>
}