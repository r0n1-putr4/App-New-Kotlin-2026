package com.kotlin.basic.appberitatrpl3d

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kotlin.basic.appberitatrpl3d.config.SessionManager
import com.kotlin.basic.appberitatrpl3d.databinding.ActivityMainBinding
import com.kotlin.basic.appberitatrpl3d.pages.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager
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
        sessionManager = SessionManager(this)
        if (!sessionManager.cekLogin()){
            startActivity(Intent(this,
                LoginActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        setSupportActionBar(binding.toolBar)
        supportActionBar?.title =
            "Hello, ${sessionManager.getFullName()}"

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu_berita,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menuLogout -> {
                sessionManager.logOut()
                startActivity(Intent(this,
                    LoginActivity::class.java))
                true
            }
//            R.id.menuKamera -> {
//                startActivity(Intent(this,
//                    RadioCheckBoxActivity::class.java))
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}