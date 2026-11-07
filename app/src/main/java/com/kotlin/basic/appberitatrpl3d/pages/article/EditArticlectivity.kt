package com.kotlin.basic.appberitatrpl3d.pages.article

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kotlin.basic.appberitatrpl3d.R
import com.kotlin.basic.appberitatrpl3d.config.ApiClient
import com.kotlin.basic.appberitatrpl3d.databinding.ActivityEditArticlectivityBinding
import com.kotlin.basic.appberitatrpl3d.models.ResponModel
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class EditArticlectivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditArticlectivityBinding
    private var imageFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditArticlectivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        editArticle()
    }

    private fun editArticle() {
        val id = intent.getStringExtra("id").toString()
        val user_id = intent.getStringExtra("user_id").toString()
        val judul = intent.getStringExtra("judul").toString()
        val isi = intent.getStringExtra("isi").toString()
        val cover = intent.getStringExtra("cover")
        val rating = intent.getFloatExtra("rating", 0f)




        binding.etJudul.setText(judul)
        binding.etIsi.setText(isi)
        binding.rbArticle.rating = rating
        Picasso.get().load(ApiClient.BASE_URL + "/api-berita-trpl3d/" + cover)
            .into(binding.ivArticle)
        binding.btnSimpan.setOnClickListener {
            val judul_ = binding.etJudul.text.toString()
            val isi_ = binding.etIsi.text.toString()
            val rating_ = binding.rbArticle.rating.toString()

            val userIdPart = user_id.toRequestBody("text/plain".toMediaTypeOrNull())
            val judulPart = judul_.toRequestBody("text/plain".toMediaTypeOrNull())
            val isiPart = isi_.toRequestBody("text/plain".toMediaTypeOrNull())
            val coverPart: MultipartBody.Part? = imageFile?.let {
                MultipartBody.Part.createFormData(
                    "cover",
                    it.name,
                    it.asRequestBody("image/*".toMediaTypeOrNull())
                )
            }
            val ratingPart = rating_.toRequestBody("text/plain".toMediaTypeOrNull())
            binding.btnSimpan.visibility = View.GONE
            binding.pbArticle.visibility = View.VISIBLE

            ApiClient.apiService.updateArticle(
                id,
                userIdPart,
                judulPart,
                isiPart,
                coverPart,
                ratingPart
            ).enqueue(object : Callback<ResponModel?> {
                override fun onResponse(
                    call: Call<ResponModel?>,
                    response: Response<ResponModel?>
                ) {
                    if (response.isSuccessful) {
                        val respon = response.body()!!
                        val success = respon.success
                        val message = respon.message

                        binding.btnSimpan.visibility = View.VISIBLE
                        binding.pbArticle.visibility = View.GONE

                        Log.v("query",message)
                        if (success) {
                            startActivity(
                                Intent(
                                    this@EditArticlectivity,
                                    ArticleActivity::class.java
                                )
                            )
                        }

                        Toast.makeText(
                            this@EditArticlectivity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponModel?>, t: Throwable) {
                    binding.btnSimpan.visibility = View.VISIBLE
                    binding.pbArticle.visibility = View.GONE
                    Toast.makeText(
                        this@EditArticlectivity,
                        "Gagal Terhubung ke API",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }


    }
}