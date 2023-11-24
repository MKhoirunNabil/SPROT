package com.sport

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.sport.Database.Bola
import com.sport.Database.Raket
import com.sport.Database.SPORT
import com.sport.databinding.ActivityDetailBolaBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailBolaActivity : AppCompatActivity() {
    private lateinit var binddetailbola: ActivityDetailBolaBinding
    private val db by lazy { SPORT.invoke(this) }
    var idBola = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binddetailbola = ActivityDetailBolaBinding.inflate(layoutInflater)
        setContentView(binddetailbola.root)

        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val isAdmin = sharedPreferences.getBoolean("isAdmin", false)

        if (isAdmin) {
            binddetailbola.BTNEditBola.visibility = View.VISIBLE
            binddetailbola.BTNHapusBola.visibility = View.VISIBLE
        } else {
            binddetailbola.BTNEditBola.visibility = View.GONE
            binddetailbola.BTNHapusBola.visibility = View.GONE
        }

        val idball = intent.getStringExtra("idbolaaa").toString().toInt()

        binddetailbola.BTNEditBola.setOnClickListener {
            val intent = Intent(this, EditBolaActivity::class.java)
            intent.putExtra("idbolaaa", idball.toString())
            startActivity(intent)
        }

        binddetailbola.backdetailbola.setOnClickListener {
            startActivity(Intent(this, DasboardActivity::class.java))
        }
        binddetailbola.BTNHapusBola.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val data = db.daooo().getBolaById(idball).firstOrNull()
                data?.let {
                    runOnUiThread {
                        HapusRaket(it)
                    }
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val data = db.daooo().getBolaById(idball)[idBola]
            runOnUiThread {
                binddetailbola.namadetailbola.setText(data.namaBola)
                binddetailbola.hargadetailbola.setText(data.hargaBola.toString())
                binddetailbola.stokdetailbola.setText(data.stokBola.toString())
                binddetailbola.deskdetailbola.setText(data.deskBola)
                Glide.with(this@DetailBolaActivity)
                    .load(Uri.parse(data.imagebola))
                    .into(binddetailbola.imgdetailbola)
            }
        }
    }
    fun HapusRaket(bola : Bola) {
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Konfirmasi Hapus Data")
            setMessage("Anda yakin ingin menghapus data ini?")
            setNegativeButton("Batal") { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface: DialogInterface, i: Int ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.daooo().HapusBola(bola)
                    dialogInterface.dismiss()
                    startActivity(Intent(this@DetailBolaActivity, DasboardActivity::class.java))
                }
            }
        }
        dialog.show()
    }
}