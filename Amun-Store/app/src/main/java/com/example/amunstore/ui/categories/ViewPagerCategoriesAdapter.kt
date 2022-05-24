package com.example.amunstore.ui.categories

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.amunstore.ui.categories.singlecategory.SingleCategoryFragment

class ViewPagerCategoriesAdapter(private val items: ArrayList<SingleCategoryFragment>, activity: FragmentActivity) :
    FragmentStateAdapter(activity) {
    override fun getItemCount() =
        items.size

    override fun createFragment(position: Int) = items[position]
}