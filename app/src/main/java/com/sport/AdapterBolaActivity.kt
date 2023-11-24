package com.sport

import android.content.Context
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
import com.sport.Database.Bola

class AdapterBolaActivity(val list: ArrayList<Bola>):
    RecyclerView.Adapter<AdapterBolaActivity.BolaHolder>() {
    class BolaHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameee = itemView.findViewById<TextView>(R.id.txtnamaBola)
        val imageB = itemView.findViewById<ImageView>(R.id.imgBola)
        val hargaaa = itemView.findViewById<TextView>(R.id.txthargaBola)
        val detail = itemView.findViewById<CardView>(R.id.cardviewboladetail)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BolaHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.bola_adapter,
                parent,
                false
            )
        return BolaHolder(view)
    }

    override fun onBindViewHolder(holder: BolaHolder, position: Int) {
        holder.nameee.text = list[position].namaBola
        holder.hargaaa.text = list[position].hargaBola.toString()
        Glide.with(holder.itemView.context)
            .load(Uri.parse(list[position].imagebola))
            .into(holder.imageB)

        holder.detail.setOnClickListener{
            val context = holder.itemView.context
            val intent = Intent(context, DetailBolaActivity::class.java)
            intent.putExtra("idbolaaa", list[position].idBola.toString())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}