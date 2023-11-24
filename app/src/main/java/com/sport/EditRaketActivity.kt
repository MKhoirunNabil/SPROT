package com.sport

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.sport.Database.Raket
import com.sport.Database.SPORT
import com.sport.databinding.ActivityEditRaketBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditRaketActivity : AppCompatActivity(){
    private lateinit var binding: ActivityEditRaketBinding
    private val db by lazy { SPORT.invoke(this) }
    var idRaket = 0
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRaketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnsaveRaket.setOnClickListener {
            updateRaket()
        }

        binding.backEditRaket.setOnClickListener {
            startActivity(Intent(this, RaketActivity::class.java))
        }

        binding.imagePicEdit.setOnClickListener {
            getImages.launch("image/*")
        }

        val id = intent.getStringExtra("idrakettt").toString().toInt()
        CoroutineScope(Dispatchers.IO).launch {
            val data = db.daooo().getRaketById(id)[idRaket]
            runOnUiThread {
                binding.editNamaRaket.setText(data.namaRaket)
                binding.editHargaRaket.setText(data.hargaRaket.toString())
                binding.editStokRaket.setText(data.stokRaket.toString())
                binding.editDeskRaket.setText(data.deskRaket)
                Glide.with(this@EditRaketActivity)
                    .load(Uri.parse(data.imageraket))
                    .into(binding.imagePicEdit)
            }
        }
    }

    private val getImages = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
            binding.imagePicEdit.setImageURI(it)
        }
    }

    private fun updateRaket() {
        val namaRaket = binding.editNamaRaket.text.toString()
        val hargaRaket = binding.editHargaRaket.text.toString()
        val stokRaket = binding.editStokRaket.text.toString()
        val deskRaket = binding.editDeskRaket.text.toString()

        if (namaRaket.isNotEmpty() && hargaRaket.isNotEmpty() && stokRaket.isNotEmpty() && deskRaket.isNotEmpty() && imageUri != null) {
            CoroutineScope(Dispatchers.IO).launch {
                db.daooo().EditRaket(
                    Raket(
                        idRaket,
                        imageraket = imageUri.toString(),
                        namaRaket = namaRaket,
                        hargaRaket = hargaRaket.toDouble().toInt(),
                        stokRaket = stokRaket.toInt(),
                        deskRaket = deskRaket
                    )
                )
                runOnUiThread {
                    Toast.makeText(this@EditRaketActivity, "Berhasil Mengupdate Data", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@EditRaketActivity, DasboardActivity::class.java)
                    startActivity(intent)
                }
            }
        } else {
            runOnUiThread {
                Toast.makeText(this@EditRaketActivity, "Isi semua dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }
}