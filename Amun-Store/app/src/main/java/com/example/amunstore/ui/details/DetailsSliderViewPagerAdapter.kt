package com.example.amunstore.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.amunstore.R
import com.example.amunstore.model.product.Images
import com.google.android.material.imageview.ShapeableImageView


class DetailsSliderViewPagerAdapter(private val imageList: List<Images>) :
    RecyclerView.Adapter<DetailsSliderViewPagerAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val slider = itemView.findViewById<ShapeableImageView>(R.id.sliderImageView)

        fun bind(data: String?) {
         Glide.with(slider)
                .load(data)
                .into(slider)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.details_slider_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageList[position].src)
    }

    override fun getItemCount(): Int {
      return  imageList.size
    }
}