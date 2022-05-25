package com.example.amunstore.ui.categories.singlecategory

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amunstore.databinding.ItemSubCategoriesFilterBinding
import com.example.amunstore.model.subcategory.SubCategory

class SubCategoriesRecyclerAdapter(
    private var list: ArrayList<SubCategory>,
    val itemOnClickFilter: (SubCategory) -> Unit
) :
    RecyclerView.Adapter<SubCategoriesRecyclerAdapter.ViewHolder>() {

    private var selectedItem: ViewHolder? = null
    private var selected = 0

    class ViewHolder(var view: ItemSubCategoriesFilterBinding) :
        RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSubCategoriesFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.view.subCategoryTitleTextView.text = list[position].name
        if (position == 0) {
            holder.view.mainConstrainLayout.setBackgroundColor(Color.BLACK)
            holder.view.subCategoryTitleTextView.setTextColor(Color.WHITE)
            selectedItem = holder
        }
        holder.view.mainConstrainLayout.setOnClickListener {

            selected = position
            // unselect previous
            selectedItem?.view?.mainConstrainLayout?.setBackgroundColor(Color.WHITE)
            selectedItem?.view?.subCategoryTitleTextView?.setTextColor(Color.BLACK)

            selectedItem = holder
            itemOnClickFilter(list[position])
            holder.view.mainConstrainLayout.setBackgroundColor(Color.BLACK)
            holder.view.subCategoryTitleTextView.setTextColor(Color.WHITE)
        }
    }

    override fun getItemCount() = list.size
}