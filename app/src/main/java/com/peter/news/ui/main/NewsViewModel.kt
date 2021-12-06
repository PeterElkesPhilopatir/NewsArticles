package com.peter.news.ui.main

import android.app.Application
import android.util.Log
import androidx.databinding.adapters.SearchViewBindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peter.news.R
import com.peter.news.network.ApiService
import com.peter.news.pojo.Article
import com.peter.news.pojo.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.ArrayList

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    var app = application

    enum class ApiStatus { LOADING, ERROR, DONE, EMPTY }

    private val _data = MutableLiveData<List<Article>>()
    val data: LiveData<List<Article>>
        get() = _data

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
        get() = _categories

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    init {
        getDefaultData()
        getCategories(application)
    }

    fun getDefaultData() {
        _data.value = ArrayList()
        coroutineScope.launch {
            var getPropertiesDeferred =
                ApiService.NewsApi.retrofitService.getNews(
                    "eg",
                    app.getString(R.string.api_key)
                )
            try {
                _status.value = ApiStatus.LOADING
                var listResult = getPropertiesDeferred.await().articles
                _data.value = listResult
                _status.value = ApiStatus.DONE
                if (_data.value!!.isEmpty())
                    _status.value = ApiStatus.EMPTY
                Log.i("RESPONSE", _status.value.toString())
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _data.value = ArrayList()
            }
        }
    }

    fun onSearch(query: String) {
        _data.value = ArrayList()
        coroutineScope.launch {
            var getPropertiesDeferred = ApiService.NewsApi.retrofitService.getFilteredNews(
                query,
                app.getString(R.string.api_key)
            )
            try {
                _status.value = ApiStatus.LOADING
                var listResult = getPropertiesDeferred.await().articles
                _data.value = listResult
                _status.value = ApiStatus.DONE
                if (_data.value!!.isEmpty())
                    _status.value = ApiStatus.EMPTY
                Log.i("RESPONSE", _status.value.toString())
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _data.value = ArrayList()
            }
        }
    }

    fun onCategorise(category: String) {
        _data.value = ArrayList()
        coroutineScope.launch {
            var getPropertiesDeferred = ApiService.NewsApi.retrofitService.getCategorizedNews(
                category,
                app.getString(R.string.api_key)
            )
            try {
                _status.value = ApiStatus.LOADING
                var listResult = getPropertiesDeferred.await().articles
                _data.value = listResult
                _status.value = ApiStatus.DONE
                if (_data.value!!.isEmpty())
                    _status.value = ApiStatus.EMPTY
                Log.i("RESPONSE", _status.value.toString())
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _data.value = ArrayList()
                Log.e("Error", e.message.toString())
            }
        }
    }

    private fun getCategories(application: Application) {
        val list: ArrayList<Category> = ArrayList()
        list.add(Category("All", "general", application.getDrawable(R.drawable.ic_all)!!))
        list.add(Category("Business", "business", application.getDrawable(R.drawable.ic_economy)!!))
        list.add(
            Category(
                "Entertainment",
                "entertainment",
                application.getDrawable(R.drawable.ic_entertainment)!!
            )
        )
        list.add(Category("Healthy", "health", application.getDrawable(R.drawable.ic_healthy)!!))
        list.add(
            Category(
                "Scientific",
                "science",
                application.getDrawable(R.drawable.ic_science)!!
            )
        )
        list.add(Category("Sport", "sports", application.getDrawable(R.drawable.ic_sports)!!))
        list.add(Category("Geeky", "technology", application.getDrawable(R.drawable.ic_geek)!!))
        _categories.value = list
    }


}