package com.toprak.newsapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.toprak.newsapplication.database.dao.NewsDao
import com.toprak.newsapplication.database.entity.FavoritesEntity
import com.toprak.newsapplication.database.entity.NewsEntity

@Database(
        entities = [NewsEntity::class, FavoritesEntity::class],
        version = 1,
        exportSchema = false
    )

@TypeConverters(NewsTypeConverter::class)
abstract class NewsDatabase: RoomDatabase(){

    abstract fun newsDao(): NewsDao
}
