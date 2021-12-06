package com.peter.news.ui.main

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.peter.news.R
import com.peter.news.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.recyclerView.adapter = NewsAdapter(OnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(it.url)
            startActivity(i)
        })
        binding.listCategories.adapter = CategoriesAdapter(OnClickListenerCategory {
            if (it.value == "general")
                viewModel.getDefaultData()
            else viewModel.onCategorise(it.value)
            binding.search.setQuery("", false);
            binding.search.clearFocus();
        })

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty())
                    viewModel.getDefaultData()
                else
                    viewModel.onSearch(query)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                if (query.isEmpty())
                    viewModel.getDefaultData()
                return true
            }
        })

    }
}