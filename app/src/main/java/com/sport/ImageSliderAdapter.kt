package com.sport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sport.databinding.CarouselItemBinding

class ImageSliderAdapter(private val IklanList:List<IklanModel>): RecyclerView.Adapter<ImageSliderAdapter.ImageSliderAdapterHolder>(){
    class ImageSliderAdapterHolder(val binding: CarouselItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderAdapterHolder {
        val binding =CarouselItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ImageSliderAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageSliderAdapterHolder, position: Int) {
        val Iklan = IklanList[position]
        holder.binding.apply {
            Glide.with(IklanImage).load(IklanList[position].image).into(IklanImage)
        }
    }

    override fun getItemCount(): Int = IklanList.size

}