package com.toprak.newsapplication.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.toprak.newsapplication.database.entity.FavoritesEntity
import com.toprak.newsapplication.database.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsEntity: NewsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteNews(favoritesEntity: FavoritesEntity)

    @Query("SELECT * FROM news_table ORDER BY id ASC")
    fun readNews(): LiveData<List<NewsEntity>>

    @Query("SELECT * FROM favorite_news_table ORDER BY id ASC")
    fun readFavoriteNews(): Flow<List<FavoritesEntity>>

    @Delete
    suspend fun deleteFavoriteNew(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM favorite_news_table")
    suspend fun deleteAllFavoriteNews()
}