package com.devshub.artbook.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.devshub.artbook.R
import com.devshub.artbook.db.Art
import javax.inject.Inject

class ArtRVAdapter @Inject constructor(
    val glide: RequestManager
) : RecyclerView.Adapter<ArtRVAdapter.ArtViewHolder>() {


    class ArtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffUtil = object : DiffUtil.ItemCallback<Art>() {

        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var arts: List<Art>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_art, parent, false)
        return ArtViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<AppCompatImageView>(R.id.itemIvArt)
        val nameText = holder.itemView.findViewById<AppCompatTextView>(R.id.itemTvArtName)
        val artistNameText = holder.itemView.findViewById<AppCompatTextView>(R.id.itemTvArtistName)
        val yearText = holder.itemView.findViewById<AppCompatTextView>(R.id.itemTvArtYear)
        val art = arts[position]
        holder.itemView.apply {
            glide.load(art.imageUrl).into(imageView)
            nameText.text = "Name: ${art.name}"
            artistNameText.text = "Artist Name: ${art.artistName}"
            yearText.text = "Year: ${art.year}"
        }
    }

    override fun getItemCount(): Int {
        return arts.size
    }

}