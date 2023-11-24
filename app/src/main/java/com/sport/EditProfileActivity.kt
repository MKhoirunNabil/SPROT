package com.sport

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imgProfile: ImageView
    private lateinit var edtName: EditText
    private lateinit var btnSave: Button

    private var imageUri: Uri? = null

    private val getImages = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri: Uri? = result.data?.data
            uri?.let {
                imageUri = it
                imgProfile.setImageURI(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        imgProfile = findViewById(R.id.imageEdit)
        edtName = findViewById(R.id.editNama)
        btnSave = findViewById(R.id.btnsaveprofile)

        imgProfile.setOnClickListener {
            openImagePicker()
        }

        btnSave.setOnClickListener {
            saveProfileChanges()
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        getImages.launch(intent)
    }

    private fun saveProfileChanges() {
        val newName = edtName.text.toString()
        // Save the changes to your data storage (e.g., SharedPreferences, database)

        // For simplicity, let's assume you want to pass the new name and image URI back to the calling activity
        val resultIntent = Intent()
        resultIntent.putExtra("newName", newName)
        resultIntent.putExtra("imageUri", imageUri?.toString())
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}