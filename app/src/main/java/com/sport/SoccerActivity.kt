package com.sport

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sport.Database.ALLDAO
import com.sport.Database.Bola
import com.sport.Database.Raket
import com.sport.Database.SPORT
import com.sport.databinding.ActivitySoccerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SoccerActivity : AppCompatActivity() {
    private lateinit var bindlistsoccer: ActivitySoccerBinding
    private val db by lazy { SPORT(this) }
    private lateinit var adapter : AdapterBolaActivity
    private lateinit var dao: ALLDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindlistsoccer = ActivitySoccerBinding.inflate(layoutInflater)
        setContentView(bindlistsoccer.root)
        adapter = AdapterBolaActivity (arrayListOf())
        bindlistsoccer.backbolasepak.setOnClickListener{
            startActivity(Intent(this,DasboardActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    fun getData() {
        bindlistsoccer.recylerbolasepak.layoutManager = LinearLayoutManager(this)
        CoroutineScope(Dispatchers.IO).launch {
            val data = db.daooo().TampilSepak()
            withContext(Dispatchers.Main){
                adapter.list.clear()
                adapter.list.addAll(data)
                adapter.notifyDataSetChanged()
            }
        }
        bindlistsoccer.recylerbolasepak.adapter = adapter
    }
}