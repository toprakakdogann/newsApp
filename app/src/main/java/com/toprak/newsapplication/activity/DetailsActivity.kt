package com.toprak.newsapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.toprak.newsapplication.viewModel.MainViewModel
import androidx.appcompat.widget.Toolbar
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.toprak.newsapplication.R
import com.toprak.newsapplication.adapter.PagerAdapter
import com.toprak.newsapplication.database.entity.FavoritesEntity
import com.toprak.newsapplication.databinding.ActivityDetailsBinding
import com.toprak.newsapplication.fragment.DetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()
    private var newsSaved = false
    private var savedNewsId = 0
    private var savedNewsUrlToImage = ""
    private lateinit var menuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(DetailsFragment())

        val titles = ArrayList<String>()
        titles.add("Details")

        val articleBundle = Bundle()
        articleBundle.putParcelable("article", args.article)

        val adapter = PagerAdapter(
            articleBundle,
            fragments,
            titles,
            supportFragmentManager
        )
        
        binding.viewPager.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        menuItem = menu!!.findItem(R.id.saveToFavoriteMenu)
        checkSavedNews(menuItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        else if (item.itemId == R.id.saveToFavoriteMenu && !newsSaved){
            saveToFavorites(item)
        }
        else if (item.itemId == R.id.saveToFavoriteMenu && newsSaved){
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }
    
    private fun checkSavedNews(menuItem: MenuItem){
        mainViewModel.readFavoriteNews.observe(this) { favoritesEntity ->
            try{
                for(savedNews in favoritesEntity){
                    if(savedNews.article.source.id == args.article.source.id
                            && savedNews.article.urlToImage == args.article.urlToImage)
                    {
                        changeMenuItemColor(menuItem, R.color.orange)
                        savedNewsId = savedNews.id
                        savedNewsUrlToImage = args.article.urlToImage.toString()
                        newsSaved = true
                    }
                }
            } catch (e: Exception){
                Log.d("DetailActivity", e.message.toString())
            }
        }
    }
    
    private fun saveToFavorites(item: MenuItem){
        val favoritesEntity = 
            FavoritesEntity(0,args.article)
        mainViewModel.insertFavoriteNews(favoritesEntity)
        changeMenuItemColor(item, R.color.orange)
        showSnackBar("News Saved.")
        newsSaved = true
    }
    
    private fun removeFromFavorites(item: MenuItem){
        val favoritesEntity =
            FavoritesEntity(savedNewsId,args.article)
        mainViewModel.deleteFavoriteNew(favoritesEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackBar("News Deleted.")
        newsSaved = false
    }
    
    private fun showSnackBar(message: String){
        Snackbar.make(
            binding.detailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }
    
    private fun changeMenuItemColor (item: MenuItem, color: Int){
        item.icon!!.setTint(ContextCompat.getColor(this,color))
    }

    override fun onDestroy() {
        super.onDestroy()
        changeMenuItemColor(menuItem, R.color.white)
    }
}