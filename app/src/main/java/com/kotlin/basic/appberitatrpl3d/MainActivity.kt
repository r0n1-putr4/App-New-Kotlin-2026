package com.kotlin.basic.appberitatrpl3d

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kotlin.basic.appberitatrpl3d.config.SessionManager
import com.kotlin.basic.appberitatrpl3d.databinding.ActivityMainBinding
import com.kotlin.basic.appberitatrpl3d.pages.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



    }

    override fun onStart() {
        super.onStart()

        val sessionManager = SessionManager(this)
        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = "Halo, ${sessionManager.getUserId()}"
        if (!sessionManager.isLogin()) {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }
        binding.ivUser.setOnClickListener {
            sessionManager.logout()
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }
    }
}