package com.toprak.newsapplication.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.toprak.newsapplication.model.Article
import com.toprak.newsapplication.model.News

class NewsTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun newToString(news: News): String{
        return gson.toJson(news)
    }

    @TypeConverter
    fun stringToNew(data: String): News{
        var listType = object : TypeToken<News>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun articleToString(article: Article): String{
        return gson.toJson(article)
    }

    @TypeConverter
    fun stringToArticle(data: String): Article{
        var listType = object : TypeToken<Article>() {}.type
        return gson.fromJson(data, listType)
    }

}