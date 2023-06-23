package com.toprak.newsapplication.data

import com.toprak.newsapplication.data.network.NewsApi
import com.toprak.newsapplication.model.News
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val newsApi: NewsApi
) {
    suspend fun getNews(): News {
        return newsApi.getNews()
    }

    suspend fun getNewsTesla(): News{
        return newsApi.getNewsTesla()
    }

    suspend fun getNewsTech(): News{
        return newsApi.getNewsTech()
    }
}