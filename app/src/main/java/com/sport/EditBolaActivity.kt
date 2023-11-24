package com.sport

import android.R
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.sport.Database.Bola
import com.sport.Database.Raket
import com.sport.Database.SPORT
import com.sport.databinding.ActivityEditBolaBinding
import com.sport.databinding.ActivityEditRaketBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditBolaActivity : AppCompatActivity() {
    private lateinit var selectedItemJenis : String
    private lateinit var bindeditbola : ActivityEditBolaBinding
    private val db by lazy { SPORT.invoke(this) }
    var idBola = 0
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindeditbola = ActivityEditBolaBinding.inflate(layoutInflater)
        setContentView(bindeditbola.root)

        bindeditbola.btnsaveBola.setOnClickListener {
            updateRaket()
        }

        bindeditbola.backEditRaket.setOnClickListener {
            startActivity(Intent(this, DasboardActivity::class.java))
        }

        bindeditbola.imagePicEditBola.setOnClickListener {
            getImages.launch("image/*")
        }
        val dataJenis = arrayOf("Sepak", "Voli", "Basket")
        val spnJenis = bindeditbola.editJenisBola
        val spnJenisAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, dataJenis)
        spnJenisAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnJenis.adapter = spnJenisAdapter

        val id = intent.getStringExtra("idbolaaa").toString().toInt()
        CoroutineScope(Dispatchers.IO).launch {
            val data = db.daooo().getBolaById(id)[idBola]
            runOnUiThread {
                bindeditbola.editNamaBola.setText(data.namaBola)
                bindeditbola.editHargaBola.setText(data.hargaBola.toString())
                bindeditbola.editStokBola.setText(data.stokBola.toString())
                bindeditbola.editDeskBola.setText(data.deskBola)
                Glide.with(this@EditBolaActivity)
                    .load(Uri.parse(data.imagebola))
                    .into(bindeditbola.imagePicEditBola)
            }
        }
    }

    private val getImages = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
            bindeditbola.imagePicEditBola.setImageURI(it)
        }
    }

    private fun updateRaket() {
        val namaBola = bindeditbola.editNamaBola.text.toString()
        val jenisBola = selectedItemJenis.toInt().toString()
        val hargaBola = bindeditbola.editHargaBola.text.toString()
        val stokBola = bindeditbola.editStokBola.text.toString()
        val deskBola = bindeditbola.editDeskBola.text.toString()

        if (namaBola.isNotEmpty() && hargaBola.isNotEmpty() && stokBola.isNotEmpty() && deskBola.isNotEmpty() && imageUri != null) {
            CoroutineScope(Dispatchers.IO).launch {
                db.daooo().EditBola(
                    Bola(
                        0,
                        imagebola = imageUri.toString(),
                        namaBola = namaBola,
                        jenisBola = jenisBola,
                        hargaBola = hargaBola.toDouble().toInt(),
                        stokBola = stokBola.toInt(),
                        deskBola = deskBola
                    )
                )
                runOnUiThread {
                    Toast.makeText(this@EditBolaActivity, "Berhasil Mengupdate Data", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@EditBolaActivity, DasboardActivity::class.java)
                    startActivity(intent)
                }
            }
        } else {
            runOnUiThread {
                Toast.makeText(this@EditBolaActivity, "Isi semua dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }
}