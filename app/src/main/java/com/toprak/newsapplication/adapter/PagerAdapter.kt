@file:Suppress("DEPRECATION")

package com.toprak.newsapplication.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

@Suppress("DEPRECATION")
class PagerAdapter(

    private val articleBundle: Bundle,
    private val fragments : ArrayList<Fragment>,
    private val title:  ArrayList<String>,
    fm: FragmentManager
    ):FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            fragments[position].arguments = articleBundle
            return fragments[position]
        }

        override fun getPageTitle(position: Int): CharSequence {
            return title[position]
        }
    }
