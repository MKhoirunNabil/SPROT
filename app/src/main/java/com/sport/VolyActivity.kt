package com.sport
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sport.Database.SPORT
import com.sport.databinding.ActivityVolyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VolyActivity : AppCompatActivity() {
    private lateinit var bindlistvoli: ActivityVolyBinding
    private val db by lazy { SPORT(this) }
    private lateinit var adapter : AdapterBolaActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindlistvoli = ActivityVolyBinding.inflate(layoutInflater)
        setContentView(bindlistvoli.root)
        adapter = AdapterBolaActivity (arrayListOf())
        bindlistvoli.backvoli.setOnClickListener{
            startActivity(Intent(this,DasboardActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    fun getData() {
        bindlistvoli.recylervoli.layoutManager = LinearLayoutManager(this)
        CoroutineScope(Dispatchers.IO).launch {
            val data = db.daooo().TampilVoli()
            withContext(Dispatchers.Main){
                adapter.list.clear()
                adapter.list.addAll(data)
                adapter.notifyDataSetChanged()
            }
        }
        bindlistvoli.recylervoli.adapter = adapter
    }
}