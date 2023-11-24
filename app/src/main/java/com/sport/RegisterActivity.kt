package com.sport

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.*
import androidx.room.Room
import com.sport.Database.SPORT
import com.sport.Database.USER
import com.sport.databinding.ActivityRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var bindRegister: ActivityRegisterBinding
    private lateinit var database: SPORT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindRegister = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bindRegister.root)

        database = Room.databaseBuilder(applicationContext, SPORT::class.java, "app-database").build()

        bindRegister.TXTLOGIN.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        bindRegister.backtologin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        bindRegister.showHideCheckBoxSingup.setOnCheckedChangeListener { buttonView, isChecked ->
            // If the ToggleButton is checked, show the password
            val transformationMethod = if (isChecked) null else PasswordTransformationMethod.getInstance()
            bindRegister.ETPASSUP.transformationMethod = transformationMethod
            bindRegister.ETKofPASSUP.transformationMethod = transformationMethod
        }

        bindRegister.BTNSignup.setOnClickListener {
            val name = bindRegister.ETNameUP.text.toString()
            val email = bindRegister.ETEmailUP.text.toString()
            val password = bindRegister.ETPASSUP.text.toString()
            val confirmPassword = bindRegister.ETKofPASSUP.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    // Menjalankan registrasi pengguna menggunakan coroutines
                    GlobalScope.launch(Dispatchers.Main) {
                        handleUserRegistration(name, email, password, null)
                    }
                } else {
                    showToast("Password dan Konfirmasi Password tidak cocok")
                }
            } else {
                showToast("Semua bidang harus diisi")
            }
        }
    }

    private suspend fun handleUserRegistration(name: String, email: String, password: String, profileImageUri: Uri?) {
        val userDao = database.daooo()

        val existingUser = userDao.getUserByEmail(email)
        if (existingUser == null) {
            // Sertakan nilai null untuk profileImageUri
            val newUser = USER(name = name, email = email, password = password, profileImageUri = profileImageUri?.toString())
            userDao.insertUser(newUser)

            showToast("Pendaftaran berhasil")
            // Alihkan ke halaman login atau halaman lain sesuai kebutuhan
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        } else {
            showToast("Email sudah terdaftar")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}