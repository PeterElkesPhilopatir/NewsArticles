package com.peter.news.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.peter.news.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.compose.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    val viewModel: NewsViewModel by viewModel()
    lateinit var adapter: NewsAdapter

    //private lateinit var viewModel: NewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        adapter = NewsAdapter(OnClickListener {
            viewModel.displayPropertyDetails(it)
        })
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateFooter(footer = NewsLoadStateAdapter()
            )

        }
        adapter.addLoadStateListener {
            if (it.refresh == LoadState.Loading)
                Toast.makeText(context,"Loading",Toast.LENGTH_SHORT).show()


        }
        lifecycleScope.launch {
            viewModel.listData.collect { adapter.submitData(it) }
        }


        viewModel.selectedItem.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailsFragment(
                        it
                    )
                )
                viewModel.displayPropertyDetailsComplete()
            }
        })

        binding

        return binding.root
    }


}