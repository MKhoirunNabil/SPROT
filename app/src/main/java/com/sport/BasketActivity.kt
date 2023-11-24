package com.sport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sport.Database.SPORT
import com.sport.databinding.ActivityBasketBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BasketActivity : AppCompatActivity() {
    private lateinit var bindlistbasket: ActivityBasketBinding
    private val db by lazy { SPORT(this) }
    private lateinit var adapter : AdapterBolaActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindlistbasket = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(bindlistbasket.root)
        adapter = AdapterBolaActivity (arrayListOf())
        bindlistbasket.backbasket.setOnClickListener{
            startActivity(Intent(this,DasboardActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    fun getData() {
        bindlistbasket.recylerbasket.layoutManager = LinearLayoutManager(this)
        CoroutineScope(Dispatchers.IO).launch {
            val data = db.daooo().TampilBasket()
            withContext(Dispatchers.Main){
                adapter.list.clear()
                adapter.list.addAll(data)
                adapter.notifyDataSetChanged()
            }
        }
        bindlistbasket.recylerbasket.adapter = adapter
    }
}