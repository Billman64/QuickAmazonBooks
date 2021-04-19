package com.github.billman64.quickamazonbooks.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.billman64.quickamazonbooks.databinding.AmazonBookItemLayoutBinding
import com.github.billman64.quickamazonbooks.model.data.AmazonBooksResponseItem

class AmazonBookAdapter(private var responseItems: List<AmazonBooksResponseItem>): RecyclerView.Adapter<AmazonBookAdapter.AmazonBookViewHolder>() {

    inner class AmazonBookViewHolder(var binding: AmazonBookItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmazonBookViewHolder {
        val binding = AmazonBookItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AmazonBookViewHolder(binding)

    }

    fun updateListItems(responseItems: List<AmazonBooksResponseItem>){
        this.responseItems = responseItems
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AmazonBookViewHolder, position: Int) {

        val book = responseItems[position]

        holder.binding.apply {

            Glide.with(holder.itemView)
                .applyDefaultRequestOptions(RequestOptions.centerCropTransform())
                .load(book.imageURL)
                .into(bookCover)

            bookTitleTextview.text = book.title
        }

    }

    override fun getItemCount(): Int = responseItems.size

}