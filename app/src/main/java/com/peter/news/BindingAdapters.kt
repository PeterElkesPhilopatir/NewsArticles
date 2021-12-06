package com.peter.webkeysnews

import android.content.ActivityNotFoundException
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
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
    Log.i("IMG", imgUrl.toString())
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
fun bindStatus(statusView: LottieAnimationView, status: NewsViewModel.ApiStatus?) {
    when (status) {
        NewsViewModel.ApiStatus.LOADING -> {
            statusView.visibility = View.VISIBLE
            statusView.setAnimationFromUrl("https://assets7.lottiefiles.com/packages/lf20_szlepvdh.json")
            statusView.playAnimation()

        }
        NewsViewModel.ApiStatus.ERROR -> {
            statusView.visibility = View.VISIBLE
            statusView.setAnimationFromUrl("https://assets6.lottiefiles.com/private_files/lf30_chkimb7d.json")
            statusView.playAnimation()
//            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        NewsViewModel.ApiStatus.DONE -> {
            statusView.visibility = View.GONE
            statusView.cancelAnimation()
        }
        NewsViewModel.ApiStatus.EMPTY -> {
            statusView.visibility = View.VISIBLE
            statusView.setAnimationFromUrl("https://assets4.lottiefiles.com/packages/lf20_wnqlfojb.json")
            statusView.playAnimation()

//            statusImageView.setImageResource(R.drawable.ic_empty)
        }

    }
}

@BindingAdapter("shareClicked")
fun bindShare(shareTextView: TextView, item: Article) {
    shareTextView.setOnClickListener {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "See this Article " + item.title + "\n" + "From Here " + item.url
            )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)

        try {
            shareTextView.context.startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                shareTextView.context,
                shareTextView.context.getString(R.string.sharing_not_available),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}



