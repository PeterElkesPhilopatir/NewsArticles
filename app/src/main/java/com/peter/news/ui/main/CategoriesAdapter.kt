package com.peter.news.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.peter.news.databinding.CategoriesItemBinding
import com.peter.news.pojo.Category

class CategoriesAdapter (val onClickListener: OnClickListenerCategory) :
    ListAdapter<Category, CategoryViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            CategoryViewHolder {
        return CategoryViewHolder(CategoriesItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(item)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.value == newItem.value
        }
    }
}

class CategoryViewHolder(private var binding: CategoriesItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Category) {
        binding.data = item
        binding.executePendingBindings()
    }
}

class OnClickListenerCategory(val clickListener: (item: Category) -> Unit) {
    fun onClick(item:Category) = clickListener(item)
}

