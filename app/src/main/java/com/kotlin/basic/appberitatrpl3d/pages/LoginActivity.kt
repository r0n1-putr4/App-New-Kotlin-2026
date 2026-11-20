package com.kotlin.basic.appberitatrpl3d.pages

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kotlin.basic.appberitatrpl3d.MainActivity
import com.kotlin.basic.appberitatrpl3d.R
import com.kotlin.basic.appberitatrpl3d.config.ApiClient
import com.kotlin.basic.appberitatrpl3d.config.ApiService
import com.kotlin.basic.appberitatrpl3d.config.SessionManager
import com.kotlin.basic.appberitatrpl3d.databinding.ActivityArticleBinding
import com.kotlin.basic.appberitatrpl3d.databinding.ActivityLoginBinding
import com.kotlin.basic.appberitatrpl3d.models.LoginModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        loginUser()
    }

    private fun loginUser() {

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            ApiClient.apiService.login(username, password).enqueue(object : Callback<LoginModel> {
                override fun onResponse(
                    call: Call<LoginModel?>,
                    response: Response<LoginModel?>
                ) {
                    if (response.isSuccessful) {
                        val responseLogin = response.body()!!
                        val success = responseLogin.success
                        val message = responseLogin.message
                        if (success) {
                            val session = SessionManager(this@LoginActivity)
                            session.saveLogin(
                                responseLogin.user.id,
                                responseLogin.user.fullname,
                                responseLogin.user.username,
                                responseLogin.user.email
                            )
                            startActivity(
                                Intent(
                                    this@LoginActivity,
                                    MainActivity::class.java
                                )
                            )
                        } else {
                            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(
                    call: Call<LoginModel?>,
                    t: Throwable
                ) {

                }
            })
        }


    }
}