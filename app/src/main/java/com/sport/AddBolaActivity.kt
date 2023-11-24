package com.sport

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.sport.Database.Bola
import com.sport.Database.Raket
import com.sport.Database.SPORT
import com.sport.databinding.ActivityAddBolaBinding
import com.sport.databinding.ActivityAddRaketBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddBolaActivity : AppCompatActivity() {
    private lateinit var selectedItemJenis : String
    private var opsiJenis : String = "0"
    private lateinit var bindinsertbola: ActivityAddBolaBinding
    private val db by lazy { SPORT(this) }
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindinsertbola = ActivityAddBolaBinding.inflate(layoutInflater)
        setContentView(bindinsertbola.root)
        bindinsertbola.btnsaveBola.setOnClickListener{
            this.inserttt()
        }
        bindinsertbola.backAddBola.setOnClickListener{
            startActivity(Intent(this,DasboardActivity::class.java))
        }
        bindinsertbola.imageBola.setOnClickListener {
            getImages.launch("image/*")
        }


        val dataJenis = arrayOf("Sepak", "Voli", "Basket")
        val spnJenis = bindinsertbola.inputJenisBola
        val spnJenisAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataJenis)
        spnJenisAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnJenis.adapter = spnJenisAdapter


        spnJenis.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItemJenis = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        spnJenis.setSelection(opsiJenis.toInt())

    }

    private val getImages = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
            bindinsertbola.imageBola.setImageURI(it)
        }
    }


    fun inserttt() {
        val namaBola = bindinsertbola.inputNamaBola.text.toString()
        val jenisBola = selectedItemJenis
        val hargaBola = bindinsertbola.inputHargaBola.text.toString()
        val stokBola = bindinsertbola.inputStokBola.text.toString()
        val deskBola = bindinsertbola.inputDeskBola.text.toString()

        if (namaBola.isNotEmpty() &&  jenisBola.isNotEmpty() && hargaBola.isNotEmpty() && stokBola.isNotEmpty() && deskBola.isNotEmpty() && imageUri != null) {
            CoroutineScope(Dispatchers.IO).launch {
                db.daooo().TambahBola(
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
                    Toast.makeText(this@AddBolaActivity, "Berhasil Menambahkan Data", Toast.LENGTH_SHORT).show()

                    // Pindah ke DasboardActivity setelah operasi selesai
                    val intent = Intent(this@AddBolaActivity, DasboardActivity::class.java)
                    startActivity(intent)
                }
            }
        } else {
            runOnUiThread {
                Toast.makeText(this@AddBolaActivity, "Isi semua dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }
}