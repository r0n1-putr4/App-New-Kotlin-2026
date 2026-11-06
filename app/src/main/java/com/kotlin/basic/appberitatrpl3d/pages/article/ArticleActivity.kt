package com.kotlin.basic.appberitatrpl3d.pages.article

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kotlin.basic.appberitatrpl3d.R
import com.kotlin.basic.appberitatrpl3d.adapters.ArticleAdapter
import com.kotlin.basic.appberitatrpl3d.config.ApiClient
import com.kotlin.basic.appberitatrpl3d.databinding.ActivityArticleBinding
import com.kotlin.basic.appberitatrpl3d.models.ArticleModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        showArticle()
        binding.fabTambah.setOnClickListener {
            startActivity(Intent(this,
                TambahArticleActivity::class.java))
        }
    }

    private fun showArticle() {
        binding.pbArticle.visibility = View.VISIBLE
        ApiClient.apiService.getArticles().enqueue(object : Callback<ArticleModel> {
            override fun onResponse(
                call: Call<ArticleModel?>,
                response: Response<ArticleModel?>
            ) {
                if (response.isSuccessful) {
                    val listArticle = response.body()!!.listArticle
                    binding.rvArticle.adapter = ArticleAdapter(listArticle)
                } else {
                    binding.lnErrorArticle.visibility = View.VISIBLE
                }
                binding.pbArticle.visibility = View.GONE
            }

            override fun onFailure(
                call: Call<ArticleModel?>,
                t: Throwable
            ) {
                binding.pbArticle.visibility = View.GONE
                binding.lnErrorArticle.visibility = View.VISIBLE
            }
        })
    }
}