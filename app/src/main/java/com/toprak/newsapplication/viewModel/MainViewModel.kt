package com.toprak.newsapplication.viewModel

import androidx.lifecycle.*
import androidx.room.Insert
import com.toprak.newsapplication.data.Repository
import com.toprak.newsapplication.database.entity.FavoritesEntity
import com.toprak.newsapplication.database.entity.NewsEntity
import com.toprak.newsapplication.model.News
import com.toprak.newsapplication.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.CacheResponse
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
): ViewModel() {

    //ROOM DB

    val readNews : LiveData<List<NewsEntity>> = repository.local.readDatabase()
    val readFavoriteNews : LiveData<List<FavoritesEntity>> = repository.local.readFavoriteNews().asLiveData()

    private fun insertNews(newsEntity: NewsEntity) =
        viewModelScope.launch (Dispatchers.IO) {
            repository.local.insertNews(newsEntity)
        }

    //FAVORITES

    fun insertFavoriteNews(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO){
            repository.local.insertFavoriteNews(favoritesEntity)
        }

    fun deleteFavoriteNew(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO){
            repository.local.deleteFavoriteNew(favoritesEntity)
        }

    fun deleteAllFavoriteNews() =
        viewModelScope.launch(Dispatchers.IO){
            repository.local.deleteAllFavoriteNews()
        }

    //RETROFIT

    val newsResponse: MutableLiveData<NetworkResult<News>> = MutableLiveData()
    val newsTeslaResponse: MutableLiveData<NetworkResult<News>> = MutableLiveData()
    val newsTechResponse: MutableLiveData<NetworkResult<News>> = MutableLiveData()

    fun getNews() = viewModelScope.launch {
        getNewsSafeCall()
    }

    fun getNewsTesla() = viewModelScope.launch {
        getNewsTeslaSafeCall()
    }

    fun getNewsTech() = viewModelScope.launch {
        getNewsTechSafeCall()
    }

    private suspend fun getNewsSafeCall(){
        newsResponse.value = NetworkResult.loading()

        try{
            val response = repository.remote.getNews()
            newsResponse.value = handleNewsResponse(response)

            val news = newsResponse.value!!.data
            if(news != null){
                offlineCacheNews(news)
            }
        }catch (e: Exception){
            val error = 0
            println("News api exception: $error")
            newsResponse.value = NetworkResult.Error("News not found.")
        }
    }

    private suspend fun getNewsTeslaSafeCall(){
        newsTeslaResponse.value = NetworkResult.loading()

        try{
            val response = repository.remote.getNewsTesla()
            newsTeslaResponse.value = handleNewsResponse(response)

            val news = newsTeslaResponse.value!!.data
            if(news != null){
                offlineCacheNews(news)
            }
        }catch (e: Exception){
            val error = 0
            println("News api exception: $error")
            newsTeslaResponse.value = NetworkResult.Error("News not found.")
        }
    }

    private suspend fun getNewsTechSafeCall(){
        newsTechResponse.value = NetworkResult.loading()

        try{
            val response = repository.remote.getNewsTech()
            newsTechResponse.value = handleNewsResponse(response)

            val news = newsTechResponse.value!!.data
            if(news != null){
                offlineCacheNews(news)
            }
        }catch (e: Exception){
            val error = 0
            println("News api exception: $error")
            newsTechResponse.value = NetworkResult.Error("News not found.")
        }
    }

    private fun offlineCacheNews(news: News){
        val newsEntity = NewsEntity(news)
        insertNews(newsEntity)
    }

    private fun handleNewsResponse(response: News?): NetworkResult<News>{
        return if (response != null){
            NetworkResult.Success(response)
        }else{
            NetworkResult.Error("Could not fetch data")
        }
    }

}