package com.sport

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.sport.Database.Raket
import com.sport.Database.SPORT
import com.sport.databinding.ActivityAddRaketBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddRaketActivity : AppCompatActivity() {
    private lateinit var bindinsertraket: ActivityAddRaketBinding
    private val db by lazy { SPORT(this) }
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindinsertraket = ActivityAddRaketBinding.inflate(layoutInflater)
        setContentView(bindinsertraket.root)

        bindinsertraket.btnsaveRaket.setOnClickListener {
            inserttt()
        }

        bindinsertraket.backAddRaket.setOnClickListener {
            startActivity(Intent(this, DasboardActivity::class.java))
        }
        bindinsertraket.imagePic.setOnClickListener {
            getImages.launch("image/*")
        }
    }
    private val getImages = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
            bindinsertraket.imagePic.setImageURI(it)
        }
    }

    private fun inserttt() {
        val namaRaket = bindinsertraket.inputNamaRaket.text.toString()
        val hargaRaket = bindinsertraket.inputHargaRaket.text.toString()
        val stokRaket = bindinsertraket.inputStokRaket.text.toString()
        val deskRaket = bindinsertraket.inputDeskRaket.text.toString()

        if (namaRaket.isNotEmpty() && hargaRaket.isNotEmpty() && stokRaket.isNotEmpty() && deskRaket.isNotEmpty() && imageUri != null) {
            CoroutineScope(Dispatchers.IO).launch {
                db.daooo().TambahRaket(
                    Raket(
                        0,
                        imageraket = imageUri.toString(),
                        namaRaket = namaRaket,
                        hargaRaket = hargaRaket.toDouble().toInt(),
                        stokRaket = stokRaket.toInt(),
                        deskRaket = deskRaket
                    )
                )
                runOnUiThread {
                    Toast.makeText(this@AddRaketActivity, "Berhasil Menambahkan Data", Toast.LENGTH_SHORT).show()

                    // Pindah ke DasboardActivity setelah operasi selesai
                    val intent = Intent(this@AddRaketActivity, DasboardActivity::class.java)
                    startActivity(intent)
                }
            }
        } else {
            runOnUiThread {
                Toast.makeText(this@AddRaketActivity, "Isi semua dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }
}