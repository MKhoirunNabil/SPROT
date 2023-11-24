package com.sport

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.sport.Database.Raket
import com.sport.Database.SPORT
import com.sport.databinding.ActivityRaketBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RaketActivity : AppCompatActivity() {
    private lateinit var bindlistraket: ActivityRaketBinding
    private val db by lazy { SPORT(this) }
    private lateinit var adapter : AdapterRaketActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindlistraket = ActivityRaketBinding.inflate(layoutInflater)
        setContentView(bindlistraket.root)
        adapter = AdapterRaketActivity (arrayListOf())
        bindlistraket.backraket.setOnClickListener{
            startActivity(Intent(this,DasboardActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    fun getData() {
        bindlistraket.recyclerRaket.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        CoroutineScope(Dispatchers.IO).launch {
            val data = db.daooo().TampilRaket()
            withContext(Dispatchers.Main){
                adapter.list.clear()
                adapter.list.addAll(data)
                adapter.notifyDataSetChanged()
            }
        }
        bindlistraket.recyclerRaket.adapter = adapter
    }
}