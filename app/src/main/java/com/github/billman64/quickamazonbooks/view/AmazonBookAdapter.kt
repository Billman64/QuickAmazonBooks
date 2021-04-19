package com.github.billman64.quickamazonbooks.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.billman64.quickamazonbooks.databinding.AmazonBookItemLayoutBinding
import com.github.billman64.quickamazonbooks.model.data.AmazonBooksResponseItem

class AmazonBookAdapter(private var responseItems: List<AmazonBooksResponseItem>): RecyclerView.Adapter<AmazonBookAdapter.AmazonBookViewHolder>() {

    inner class AmazonBookViewHolder(var binding: AmazonBookItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    private lateinit var animation: Animation

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmazonBookViewHolder {
        val binding = AmazonBookItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        animation = AnimationUtils.loadAnimation(parent.context, android.R.anim.slide_in_left)
        return AmazonBookViewHolder(binding)

    }

    fun updateListItems(responseItems: List<AmazonBooksResponseItem>){
        this.responseItems = responseItems
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AmazonBookViewHolder, position: Int) {

        val book = responseItems[position]


        holder.binding.apply {

            this.root.animation = animation

            Glide.with(holder.itemView)
                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(book.imageURL)
                .into(bookCover)

            bookTitleTextview.text = book.title

            book.author?.let{
                authorTextview.text = it
                authorTextview.visibility = View.VISIBLE
            } ?: {
                authorTextview.visibility = View.GONE
            }()
        }

    }

    override fun getItemCount(): Int = responseItems.size

}