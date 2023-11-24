package com.sport

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.sport.Database.SPORT
import com.sport.Database.USER
import com.sport.databinding.ActivityProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan data dari SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val isAdmin = sharedPreferences.getBoolean("isAdmin", false)
        val email = sharedPreferences.getString("email", "")

        // Menampilkan data pada UI
        binding.Status.text = if (isAdmin) "Admin" else "User"
        binding.ETEmailProfile.text = "Email: $email"

        // Tombol kembali ke DasboardActivity
        binding.toDashboard.setOnClickListener {
            startActivity(Intent(this, DasboardActivity::class.java))
        }
    }
}