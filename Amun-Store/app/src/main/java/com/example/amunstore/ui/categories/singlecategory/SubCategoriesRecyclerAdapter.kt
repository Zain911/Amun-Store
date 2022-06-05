package com.example.amunstore.ui.categories.singlecategory

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.amunstore.R
import com.example.amunstore.databinding.ItemSubCategoriesFilterBinding
import com.example.amunstore.data.model.subcategory.SubCategory

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
            selectedItem?.view?.mainConstrainLayout?.setBackgroundColor(ContextCompat.getColor(holder.view.mainConstrainLayout.context , R.color.subcategory_unselected_background))
            selectedItem?.view?.subCategoryTitleTextView?.setTextColor(ContextCompat.getColor(holder.view.mainConstrainLayout.context , R.color.subcategory_unselected_text_color))

            selectedItem = holder
            itemOnClickFilter(list[position])
            selectedItem?.view?.mainConstrainLayout?.setBackgroundColor(ContextCompat.getColor(holder.view.mainConstrainLayout.context , R.color.subcategory_selected_background))
            holder.view.subCategoryTitleTextView.setTextColor(ContextCompat.getColor(holder.view.mainConstrainLayout.context , R.color.subcategory_selected_text_color))
        }
    }

    override fun getItemCount() = list.size
}