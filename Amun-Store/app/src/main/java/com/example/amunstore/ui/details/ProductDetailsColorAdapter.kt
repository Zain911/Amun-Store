package com.example.amunstore.ui.details

import android.app.Dialog
import android.content.Context
import android.view.*
import android.webkit.WebView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.amunstore.R
import com.example.amunstore.data.model.details.ProductDetailsResponse

class ProductDetailsColorAdapter (private val arrayList: ProductDetailsResponse?) : RecyclerView.Adapter<ProductDetailsColorAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_details_color, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.imageView.context).load(arrayList?.product!!.images[position].src)
            .into(holder.imageView)
        holder.imageView.setOnClickListener {
            showZoomableImage(
                holder.imageView.context,
                arrayList.product.images[position].src.toString()
            )
        }
    }


    // return the number of the items in the list
    override fun getItemCount(): Int {
        return arrayList?.product?.images!!.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val imageView: ImageView = itemView.findViewById(R.id.item_product_color_image)

    }

    private fun showZoomableImage(context: Context, fileUrl: String) {
        val d = Dialog(context, android.R.style.Theme_DeviceDefault_Light_NoActionBar)
        d.window?.setGravity(Gravity.CENTER)
        d.setCancelable(true)
        val wv = WebView(context)
        wv.layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        wv.loadUrl(fileUrl)
        wv.settings.builtInZoomControls = true
        wv.settings.setSupportZoom(true)
        wv.settings.loadWithOverviewMode = true
        wv.settings.useWideViewPort = true
        wv.zoomOut()
        d.setContentView(wv)
        d.show()
    }
}
