package com.toprak.newsapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.toprak.newsapplication.data.network.NewsApi
import com.toprak.newsapplication.model.News
import com.toprak.newsapplication.util.Constants.NEWS_TABLE


@Entity(tableName = NEWS_TABLE)
class NewsEntity (
    var news: News
    ){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}