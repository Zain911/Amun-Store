package com.example.amunstore.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CheckedTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.CheckedTextViewCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.amunstore.R
import com.example.amunstore.data.model.details.ProductDetailsResponse
import com.example.amunstore.data.model.product.Options


class ProductDetailsSizeAdapter (private val arrayList:ArrayList<String> ) : RecyclerView.Adapter<ProductDetailsSizeAdapter.ViewHolder>() {

    var checkedItemPosition : MutableLiveData<Int> = MutableLiveData<Int>(0)
//    var checkedItemPosition = 0
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_details_size, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         if (position==checkedItemPosition.value)
         {     holder.size.isChecked = true  }
        else
         {     holder.size.isChecked = false  }

        holder.size.text=  arrayList[position]
        holder.size.setOnClickListener {

            if (checkedItemPosition.value != position) {
                holder.size.isChecked = true
                checkedItemPosition.postValue(position)
            }
            //update fragment
            notifyDataSetChanged()
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return arrayList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val size :CheckedTextView = itemView.findViewById(R.id.item_product_size_checkbox)

    }
}
