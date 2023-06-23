package com.toprak.newsapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.toprak.newsapplication.model.Article
import com.toprak.newsapplication.util.Constants
import com.toprak.newsapplication.util.Constants.FAVORITE_NEWS_TABLE


@Entity(tableName = FAVORITE_NEWS_TABLE)
class FavoritesEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var article: Article
    )
