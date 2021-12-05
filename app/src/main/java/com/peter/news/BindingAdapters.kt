package com.peter.webkeysnews

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.peter.news.R
import com.peter.news.pojo.Article
import com.peter.news.pojo.Category
import com.peter.news.ui.main.CategoriesAdapter
import com.peter.news.ui.main.NewsAdapter
import com.peter.news.ui.main.NewsViewModel
import androidx.core.net.toUri as toUri


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    Log.i("IMG",imgUrl.toString())
    try {
        imgUrl?.let {
            imgView.clipToOutline = true
            var imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(imgView.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(imgView)
        }
    } catch (e: Exception) {
    }
}

@BindingAdapter("list_articles")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Article>?) {
    val adapter = recyclerView.adapter as NewsAdapter
    adapter.submitList(data)
}

@BindingAdapter("list_categories")
fun bindRecyclerViewCategories(recyclerView: RecyclerView, data: List<Category>?) {
    val adapter = recyclerView.adapter as CategoriesAdapter
    adapter.submitList(data)
}


@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: NewsViewModel.ApiStatus?) {
    when (status) {
        NewsViewModel.ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        NewsViewModel.ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        NewsViewModel.ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        NewsViewModel.ApiStatus.EMPTY -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_empty)
        }

    }
}



