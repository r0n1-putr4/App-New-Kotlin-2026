package com.kotlin.basic.appberitatrpl3d.pages.article

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import com.kotlin.basic.appberitatrpl3d.R
import com.kotlin.basic.appberitatrpl3d.config.ApiClient
import com.kotlin.basic.appberitatrpl3d.databinding.ActivityTambahArticleBinding
import com.kotlin.basic.appberitatrpl3d.models.ResponModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class TambahArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTambahArticleBinding

    private var imageFile: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTambahArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        getCover()
        tambahArticle()
    }

    private fun tambahArticle() {

        binding.btnSimpan.setOnClickListener {
            val userId = "1"
            val judul = binding.etJudul.text.toString()
            val rating = binding.rbArticle.rating.toString()
            val isi = binding.etIsi.text.toString()


            if ( (imageFile != null) && (judul.isNotEmpty()) &&
                (rating.isNotEmpty()) && (isi.isNotEmpty()) ) {

                val userIdPart = userId.toRequestBody("text/plain".toMediaTypeOrNull())
                val judulPart = judul.toRequestBody("text/plain".toMediaTypeOrNull())
                val isiPart = isi.toRequestBody("text/plain".toMediaTypeOrNull())
                val coverPart = MultipartBody.Part.createFormData(
                    "cover",imageFile!!.name,
                    imageFile!!.asRequestBody("image/*".toMediaType())
                )
                val ratingPart = rating.toRequestBody("text/plain".toMediaTypeOrNull())

                binding.btnSimpan.visibility = View.GONE
                binding.pbArticle.visibility = View.VISIBLE
                ApiClient.apiService.insertArticle(userIdPart,
                    judulPart,isiPart,coverPart,ratingPart).enqueue(object : Callback<ResponModel>{
                    override fun onResponse(
                        call: Call<ResponModel?>,
                        response: Response<ResponModel?>
                    ) {
                        if (response.isSuccessful){
                            val respon = response.body()!!
                            val success = respon.success
                            val message = respon.message

                            binding.btnSimpan.visibility = View.VISIBLE
                            binding.pbArticle.visibility = View.GONE

                            if (success){
                                startActivity(Intent(
                                    this@TambahArticleActivity,
                                    ArticleActivity::class.java))
                            }

                            Toast.makeText(this@TambahArticleActivity,
                                message,
                                Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(
                        call: Call<ResponModel?>,
                        t: Throwable
                    ) {
                        binding.btnSimpan.visibility = View.VISIBLE
                        binding.pbArticle.visibility = View.GONE
                        Toast.makeText(this@TambahArticleActivity,
                            "Gagal Terhubung ke API",
                            Toast.LENGTH_SHORT).show()
                    }
                })

            } else {
                Toast.makeText(
                    this,
                    "Lengkapi semua data dengan benar",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun getCover() {
        binding.btnCover.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1024, 1024)
                .start()
        }


    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,

        ) {
        super.onActivityResult(
            requestCode,
            resultCode, data
        )
        if (resultCode == RESULT_OK && data != null) {
            val uri = data.data!!

            if (uri.scheme == "file") {
                imageFile = File(uri.path!!)
                binding.ivArticle.setImageURI(uri)
                binding.ivArticle.visibility = View.VISIBLE
            }

        }
    }
}