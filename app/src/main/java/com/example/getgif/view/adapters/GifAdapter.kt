package com.example.getgif.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.getgif.R
import com.example.getgif.model.dataclasses.DataObject
import com.example.getgif.model.dataclasses.Gif
import kotlinx.android.synthetic.main.gif_card.view.*

class GifAdapter(var dataObjectsList: List<DataObject>) : RecyclerView.Adapter<GifAdapter.GifViewHolder>() {

    fun setList(list: List<DataObject>){dataObjectsList = list}

    class GifViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.gif_card, parent, false)
        return GifViewHolder(view)

    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {

        val data = dataObjectsList[position]
        Glide.with(holder.itemView.context).load(data.images.originalImage).into(holder.view.gif_card_imageView)

    }

    override fun getItemCount(): Int = dataObjectsList.size
}