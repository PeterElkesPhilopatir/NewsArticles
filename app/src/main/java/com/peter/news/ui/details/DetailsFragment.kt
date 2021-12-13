package com.peter.news.ui.details

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProviders
import com.peter.news.R
import com.peter.news.databinding.FragmentDetailsBinding
import com.peter.news.ui.main.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    val viewModel: DetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.setData(DetailsFragmentArgs.fromBundle(requireArguments()).article)
        viewModel.navToInternet.observe(viewLifecycleOwner, {
            if (null != it)
                onInternet(it)
        })
        viewModel.navToShare.observe(viewLifecycleOwner, {
            if (null != it)
                onShare()
        })
        return binding.root
    }

    private fun onShare() {
        val shareIntent = activity?.let {
            ShareCompat.IntentBuilder.from(it)
                .setText("This Article " + viewModel.selectedProperty.value!!.title + " is Good continue " + viewModel.selectedProperty.value!!.url)
                .setType("text/plain")
                .intent
        }
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                context, getString(R.string.sharing_not_available),
                Toast.LENGTH_SHORT
            ).show()
        }
        viewModel.displayShareComplete()

    }

    private fun onInternet(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
        viewModel.displayInternetComplete()
    }

}