package com.teamwork.sample.maicon.ui.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class SimpleFragmentPagerAdapter

constructor(fm: FragmentManager,
            private val fragmentList: List<Fragment>,
            private val titleList: List<String>): FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount() = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence {
        return titleList[position]
    }
}