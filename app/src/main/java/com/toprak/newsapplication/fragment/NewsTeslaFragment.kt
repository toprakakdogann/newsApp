package com.toprak.newsapplication.fragment

import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.toprak.newsapplication.R
import com.toprak.newsapplication.databinding.FragmentNewsTeslaBinding
import com.toprak.newsapplication.adapter.FavoriteNewsAdapter
import com.toprak.newsapplication.adapter.NewsAdapter
import com.toprak.newsapplication.util.NetworkResult
import com.toprak.newsapplication.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsTeslaFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private val mAdapter by lazy { NewsAdapter() }
    private var _binding: FragmentNewsTeslaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewsTeslaBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setupRecyclerView()

        binding.categories.setOnClickListener{
            findNavController().navigate(R.id.action_newsTeslaFragment_to_bottomSheetFilter)
        }

        return binding.root
    }

    private fun setupRecyclerView(){
        binding.shimmerRecyclerView.adapter = mAdapter
        binding.shimmerRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
        requestApiData()
        mAdapter.onItemClicked = { article ->
            try{
                val action = NewsTeslaFragmentDirections.actionNewsTeslaFragmentToDetailsActivity2(article)
                findNavController().navigate(action)
            } catch (e: Exception){
                Log.d("onNewClickListener", e.toString())
            }
        }
    }

    private fun requestApiData(){
        Log.d("News Fragment", "requestApiData called")
        mainViewModel.getNewsTesla()
        mainViewModel.newsTeslaResponse.observe(viewLifecycleOwner) { response ->
            when(response){
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun showShimmerEffect(){
        binding.shimmerRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect(){
        binding.shimmerRecyclerView.hideShimmer()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}