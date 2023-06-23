package com.toprak.newsapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.toprak.newsapplication.R
import com.toprak.newsapplication.databinding.BottomSheetFilterBinding

class BottomSheetFilter : BottomSheetDialogFragment() {

    private var _binding: BottomSheetFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetFilterBinding.inflate(inflater, container, false)

        binding.btnBusiness.setOnClickListener {
            findNavController().navigate(R.id.action_bottomSheetFilter_to_newsFragment)
        }

        binding.btnTesla.setOnClickListener {
            findNavController().navigate(R.id.action_bottomSheetFilter_to_newsTeslaFragment)
        }

        binding.btnTech.setOnClickListener {
            findNavController().navigate(R.id.action_bottomSheetFilter_to_newsTechFragment)
        }

        return binding.root
    }
}