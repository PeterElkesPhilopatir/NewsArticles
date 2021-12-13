package com.peter.news.ui.main

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.peter.news.R
import com.peter.news.network.ApiService
import com.peter.news.network.ApiStatus
import com.peter.news.network.NewsJsonNestedResponse
import com.peter.news.pojo.Article
import kotlinx.coroutines.*
import org.koin.androidx.compose.inject
import java.lang.Exception
import java.util.ArrayList
const val PAGE_SIZE = 5

class NewsViewModel(application: Application,private val service: ApiService) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    var app = application

    private val _data = MutableLiveData<List<Article>>()
    val data: LiveData<List<Article>>
        get() = _data

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _selectedItem = MutableLiveData<Article?>()
    val selectedItem: MutableLiveData<Article?>
        get() = _selectedItem

    init {
        Log.i("News_VIEWMODEL","init")
        _selectedItem.value = null
    }

    val listData = Pager(PagingConfig(PAGE_SIZE)) {
        PostDataSource(service)
    }.flow.cachedIn(coroutineScope)

    fun displayPropertyDetails(property: Article) {
        _selectedItem.value = property
    }

    fun displayPropertyDetailsComplete() {
        _selectedItem.value = null
    }


   private suspend fun callNetwork(getPropertiesDeferred : Deferred<NewsJsonNestedResponse>){
       try {
           _status.value = ApiStatus.LOADING
           var listResult = getPropertiesDeferred.await().articles
//           _data.value = listResult
           _status.value = ApiStatus.DONE
           if (_data.value!!.isEmpty())
               _status.value = ApiStatus.EMPTY
           Log.i("RESPONSE", _status.value.toString())
       } catch (e: Exception) {
           _status.value = ApiStatus.ERROR
           _data.value = ArrayList()
           Log.e("ERROR",e.message.toString())
       }
    }


}