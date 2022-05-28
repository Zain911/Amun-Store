package com.example.amunstore.ui.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.amunstore.R
import com.example.amunstore.model.details.ProductDetailsResponse

class ProductDetailsAdapter(private val context: Context, private val arrayList:ProductDetailsResponse) : BaseAdapter() {
    private lateinit var imageView: ImageView
    private lateinit var titles: TextView
    private lateinit var price: TextView

    override fun getCount(): Int {
        return arrayList.product.options.size
    }
    override fun getItem(position: Int): Any {
        return position
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView = inflater.inflate(R.layout.item_similar_products, parent,false)

       // var convertView = convertView
       // convertView = LayoutInflater.from(context).inflate(R.layout.item_similar_products, parent, false)
        imageView = convertView.findViewById(R.id.details_image)
        titles = convertView.findViewById(R.id.details_name)
        price = convertView.findViewById(R.id.details_price)

        Glide.with(context).load(arrayList.product.image?.src).into(imageView)
        titles.text =  arrayList.product.options[position].name
        price.text = arrayList.product.options[position].values[0]

        return convertView
    }
}
//Class MyData class MyData(var num: Int, var name: String, var mobileNumber: String)