package com.toprak.newsapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.toprak.newsapplication.NewsDiffUtil
import com.toprak.newsapplication.databinding.NewsRowLayoutBinding
import com.toprak.newsapplication.model.Article
import com.toprak.newsapplication.model.News

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.MyViewHolder>(){

    private var news = emptyList<Article>()
    var onItemClicked: ((Article) -> Unit)? = null

    class MyViewHolder(val binding: NewsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(article: Article, onItemClicked: ((Article) -> Unit)?) {
            binding.article = article
            binding.imageUrl = article.urlToImage ?: ""
            binding.newsRowLayout.setOnClickListener {
                onItemClicked?.invoke(article)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentNews = news[position]
        holder.bind(currentNews, onItemClicked)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    fun setData(newData: News) {
        val newsDiffUtil = NewsDiffUtil(news, newData.articles)
        val diffUtilResult = DiffUtil.calculateDiff(newsDiffUtil)
        news =newData.articles
        diffUtilResult.dispatchUpdatesTo(this)
    }



}