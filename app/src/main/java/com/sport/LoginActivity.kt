package com.sport


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import android.widget.*
import androidx.room.Room
import com.sport.Database.SPORT
import com.sport.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var bindLogin: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindLogin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindLogin.root)

        bindLogin.showHideCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // If the CheckBox is checked, show the password
                bindLogin.ETPASS.transformationMethod = null
            } else {
                // If the CheckBox is not checked, hide the password
                bindLogin.ETPASS.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        bindLogin.CrateAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        val emailEditText = findViewById<EditText>(R.id.ETEmail)
        val passwordEditText = findViewById<EditText>(R.id.ETPASS)
        val loginButton = findViewById<Button>(R.id.BTNLogin)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password harus diisi", Toast.LENGTH_SHORT).show()
            } else if (isValidLogin(email, password)) {
                navigateToLobby(email, password)
            } else {
                Toast.makeText(this, "Login gagal. Periksa kredensial Anda.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidLogin(email: String, password: String): Boolean {
        if (email == "admin@GG.com" && password == "admin135") {
            return true
        }

        if (email == "rstu.vv@gmail.com" && password == "rstuaditya11") {
            return true
        }

        return false
    }

    private fun navigateToLobby(email: String, password: String) {
        val isAdmin = email == "admin@GG.com" && password == "admin135"
        saveAdminStatus(isAdmin)
        val intent = Intent(this, DasboardActivity::class.java)
        intent.putExtra("isAdmin", isAdmin)
        startActivity(intent)
    }

    private fun saveAdminStatus(isAdmin: Boolean) {
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("isAdmin", isAdmin)
            apply()
        }
    }
}