package com.example.amunstore.ui.cart.coupon

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.amunstore.databinding.ItemCouponBinding
import com.example.example.PriceRules


class CouponAdapter(
    private var couponList: MutableList<PriceRules>,

    ) :
    RecyclerView.Adapter<CouponAdapter.ViewHolder>() {


    private lateinit var context: Context

    @SuppressLint("NotifyDataSetChanged")
    fun setList(newList: MutableList<PriceRules>) {
        couponList.clear()
        couponList = newList
        notifyDataSetChanged()
    }

    class ViewHolder(var view: ItemCouponBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCouponBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.view.titleCouponTextView.text = couponList[position].title
        holder.view.discountValueTextView.text = couponList[position].value
        holder.view.startDateTextView.text = couponList[position].startsAt?.substringBefore("T")

        holder.view.copyCouponAppCompactButton.setOnClickListener {
            // val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            ///   val clip = ClipData.newPlainText("Copied", couponList[position].title)

            val clipboard: ClipboardManager? =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText("Copied", couponList[position].title)
            if (clipboard != null || clip != null) {
                clipboard?.setPrimaryClip(clip)
                Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun getItemCount() = couponList.size
}