package com.toprak.newsapplication.adapter

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.toprak.newsapplication.NewsDiffUtil
import com.toprak.newsapplication.R
import com.toprak.newsapplication.database.entity.FavoritesEntity
import com.toprak.newsapplication.databinding.FavoriteNewsRowLayoutBinding
import com.toprak.newsapplication.fragment.FavoritesFragmentDirections
import com.toprak.newsapplication.viewModel.MainViewModel
import java.util.*

class FavoriteNewsAdapter (
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
    ): RecyclerView.Adapter<FavoriteNewsAdapter.MyViewHolder>(), ActionMode.Callback{

        private var multiSeletion = false

    private lateinit var mActionMode: ActionMode
    private lateinit var rootView: View
    private var selectedNews = arrayListOf<FavoritesEntity>()
    private var myViewHolders = arrayListOf<MyViewHolder>()
    private var favoriteNews = emptyList<FavoritesEntity>()

    class MyViewHolder(val binding: FavoriteNewsRowLayoutBinding) :
            RecyclerView.ViewHolder(binding.root) {

                fun bind(favoritesEntity: FavoritesEntity){
                    binding.favoritesEntity = favoritesEntity
                    binding.executePendingBindings()
                }

                companion object {

                    fun from(parent: ViewGroup):MyViewHolder{
                        val layoutInflater = LayoutInflater.from(parent.context)
                        val binding = FavoriteNewsRowLayoutBinding.inflate(layoutInflater, parent, false)
                        return  MyViewHolder(binding)
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return favoriteNews.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolders.add(holder)
        rootView = holder.itemView.rootView
        val currentNews = favoriteNews[position]
        holder.bind(currentNews)

        holder.binding.favoriteNewsRowLayout.setOnClickListener{
           if(multiSeletion){
               applySelection(holder, currentNews)
           }else{
               val action =
                   FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(
                       currentNews.article
                   )
               holder.itemView.findNavController().navigate(action)
           }
        }

        holder.binding.favoriteNewsRowLayout.setOnLongClickListener {
            if(!multiSeletion){
                multiSeletion = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentNews)
                true
            }else{
                multiSeletion = false
                false
            }
        }
    }

    private fun applySelection(holder:MyViewHolder, currentNews: FavoritesEntity){
        if(selectedNews.contains(currentNews)){
            selectedNews.remove(currentNews)
            changeNewsStyle(holder, R.color.cardBackground, R.color.purple_500)
            applyActionModeTitle()
        }
    }

    private fun changeNewsStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int){
        holder.binding.favoriteNewsRowLayout.setBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )
        holder.binding.favoriteRowCardView.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)
    }

    private fun applyActionModeTitle(){
        when(selectedNews.size){
            0 ->{
                mActionMode.finish()
            }

            1 ->{
                mActionMode.title = "${selectedNews.size} item selected"
            }

            2 ->{
                mActionMode.title = "${selectedNews.size} items selected"
            }
        }
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorites_contexual_menu, menu)
        mActionMode = actionMode!!
        applyStatusBarColor(R.color.black)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu): Boolean{
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if(menu?.itemId == R.id.delete_favorite_news_menu){
            selectedNews.forEach{
                mainViewModel.deleteFavoriteNew(it)
            }
            showSnackBar("${selectedNews.size} New/s removed.")
            multiSeletion = false
            selectedNews.clear()
            actionMode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        myViewHolders.forEach {holder ->
            changeNewsStyle(holder, R.color.white, R.color.gray)
        }
        multiSeletion = false
        selectedNews.clear()
        applyStatusBarColor(R.color.purple_700)
    }

    private fun applyStatusBarColor(color: Int){
        requireActivity.window.statusBarColor =
            ContextCompat.getColor(requireActivity, color)
    }

    fun setData(newFavoriteNews: List<FavoritesEntity>){
        val favoriteNewsDiffUtil =
            NewsDiffUtil(favoriteNews.mapTo(arrayListOf()){it.article}, newFavoriteNews.mapTo(
                arrayListOf()){it.article})
        val diffUtilResult = DiffUtil.calculateDiff(favoriteNewsDiffUtil)
        favoriteNews = newFavoriteNews
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun showSnackBar(message: String){
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }

    fun clearContextualActionMode(){
        if(this::mActionMode.isInitialized){
            mActionMode.finish()
        }
    }
    }


