package com.peter.news.ui.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.peter.news.pojo.Article

class DetailsViewModel(@Suppress("UNUSED_PARAMETER") app: Application) :
    AndroidViewModel(app) {
    private val _selectedProperty = MutableLiveData<Article>()
    val selectedProperty: LiveData<Article>
        get() = _selectedProperty

    private val _navToInternet = MutableLiveData<String?>()
    val navToInternet: MutableLiveData<String?>
        get() = _navToInternet

    private val _navToShare = MutableLiveData<String?>()
    val navToShare: MutableLiveData<String?>
        get() = _navToShare


    fun displayInternet(property: String) {
        _navToInternet.value = property
    }

    fun displayInternetComplete() {
        _navToInternet.value = null
    }

    fun displayShare(property: String) {
        _navToShare.value = property
    }

    fun displayShareComplete() {
        _navToShare.value = null
    }

    fun setData(article: Article){
        _selectedProperty.value = article
    }
}