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
import com.sport.Database.Raket
import com.sport.Database.SPORT
import com.sport.databinding.ActivityDetailRaketBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailRaketActivity : AppCompatActivity() {
    private lateinit var binddetailraket: ActivityDetailRaketBinding
    private val db by lazy { SPORT.invoke(this) }
    var idRaket = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binddetailraket = ActivityDetailRaketBinding.inflate(layoutInflater)
        setContentView(binddetailraket.root)

        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val isAdmin = sharedPreferences.getBoolean("isAdmin", false)

        if (isAdmin) {
            binddetailraket.BTNEditRaket.visibility = View.VISIBLE
            binddetailraket.BTNHapusRaket.visibility = View.VISIBLE
        } else {
            binddetailraket.BTNEditRaket.visibility = View.GONE
            binddetailraket.BTNHapusRaket.visibility = View.GONE
        }

        val id = intent.getStringExtra("idrakettt").toString().toInt()

        binddetailraket.BTNEditRaket.setOnClickListener {
            val intent = Intent(this, EditRaketActivity::class.java)
            intent.putExtra("idrakettt", id.toString())
            startActivity(intent)
        }

        binddetailraket.backdetailraket.setOnClickListener {
            startActivity(Intent(this, RaketActivity::class.java))
        }

        binddetailraket.BTNHapusRaket.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val data = db.daooo().getRaketById(id).firstOrNull()
                data?.let {
                    runOnUiThread {
                        HapusRaket(it)
                    }
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val data = db.daooo().getRaketById(id)[idRaket]
            runOnUiThread {
                binddetailraket.namadetailraket.setText(data.namaRaket)
                binddetailraket.hargadetailraket.setText(data.hargaRaket.toString())
                binddetailraket.stokdetailraket.setText(data.stokRaket.toString())
                binddetailraket.deskdetailraket.setText(data.deskRaket)
                Glide.with(this@DetailRaketActivity)
                    .load(Uri.parse(data.imageraket))
                    .into(binddetailraket.imgdetailraket)
            }
        }
    }

    fun HapusRaket(raket: Raket) {
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Konfirmasi Hapus Data")
            setMessage("Anda yakin ingin menghapus data ini?")
            setNegativeButton("Batal") { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface: DialogInterface, i: Int ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.daooo().HapusRaket(raket)
                    dialogInterface.dismiss()
                    startActivity(Intent(this@DetailRaketActivity, RaketActivity::class.java))
                }
            }
        }
        dialog.show()
    }
}