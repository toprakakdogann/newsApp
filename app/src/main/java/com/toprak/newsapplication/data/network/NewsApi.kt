package com.toprak.newsapplication.data.network

import android.provider.SyncStateContract
import com.toprak.newsapplication.model.News
import com.toprak.newsapplication.util.Constants
import retrofit2.http.GET

interface NewsApi {

    @GET("/v2/everything?domains=wsj.com&apiKey=${Constants.API_KEY}")
    suspend fun getNews(): News

    @GET("/v2/top-headlines?sources=bbc-news&apiKey=${Constants.API_KEY}")
    suspend fun getNewsTesla(): News

    @GET("/v2/top-headlines?sources=techcrunch&apiKey=${Constants.API_KEY}")
    suspend fun getNewsTech(): News
}