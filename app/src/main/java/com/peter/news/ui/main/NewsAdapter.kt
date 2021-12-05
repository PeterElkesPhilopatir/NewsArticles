package com.peter.news.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.peter.news.databinding.NewsItemBinding
import com.peter.news.pojo.Article

class NewsAdapter (val onClickListener: OnClickListener) :
    ListAdapter<Article, NewsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            NewsViewHolder {
        return NewsViewHolder(NewsItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(item)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }
    }
}

class NewsViewHolder(private var binding: NewsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Article) {
        binding.data = item
        binding.executePendingBindings()
    }
}

class OnClickListener(val clickListener: (item: Article) -> Unit) {
    fun onClick(item:Article) = clickListener(item)
}

