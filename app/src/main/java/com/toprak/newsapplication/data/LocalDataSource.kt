package com.toprak.newsapplication.data

import androidx.lifecycle.LiveData
import com.toprak.newsapplication.database.dao.NewsDao
import com.toprak.newsapplication.database.entity.FavoritesEntity
import com.toprak.newsapplication.database.entity.NewsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val newsDao: NewsDao
) {
    fun readDatabase(): LiveData<List<NewsEntity>> {
        return newsDao.readNews()
    }

    fun readFavoriteNews(): Flow<List<FavoritesEntity>> {
        return newsDao.readFavoriteNews()
    }

    suspend fun insertNews(newsEntity: NewsEntity) {
        newsDao.insertNews(newsEntity)
    }

    suspend fun insertFavoriteNews(favoritesEntity: FavoritesEntity){
        newsDao.insertFavoriteNews(favoritesEntity)
    }

    suspend fun deleteFavoriteNew(favoritesEntity: FavoritesEntity){
        newsDao.deleteFavoriteNew(favoritesEntity)
    }

    suspend fun deleteAllFavoriteNews(){
        newsDao.deleteAllFavoriteNews()
    }

}