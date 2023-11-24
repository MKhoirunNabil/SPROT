package com.sport

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sport.Database.Raket
import com.sport.databinding.RaketAdapterBinding

class AdapterRaketActivity (var list: ArrayList<Raket>) :
    RecyclerView.Adapter<AdapterRaketActivity.RaketHolder>(){
    class RaketHolder (view: View) : RecyclerView.ViewHolder(view){
        val nameee =itemView.findViewById<TextView>(R.id.txtnamaRaket)
        val hargaaa =itemView.findViewById<TextView>(R.id.txthargaRaket)
        val imageR = itemView.findViewById<ImageView>(R.id.imgraket)
        val detail = itemView.findViewById<CardView>(R.id.cardviewraketdetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaketHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.raket_adapter,parent, false
            )
        return RaketHolder(view)
    }

    override fun onBindViewHolder(holder: RaketHolder, position: Int) {
        holder.nameee.text = list[position].namaRaket
        holder.hargaaa.text = list[position].hargaRaket.toString()
        Glide.with(holder.itemView.context)
            .load(Uri.parse(list[position].imageraket))
            .into(holder.imageR)
        holder.detail.setOnClickListener{
            val context = holder.itemView.context
            val intent = Intent(context, DetailRaketActivity::class.java)
            intent.putExtra("idrakettt", list[position].idraket.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}