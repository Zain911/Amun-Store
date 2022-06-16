package com.example.amunstore.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.amunstore.R
import com.example.amunstore.data.model.cart.ItemCart
import com.example.amunstore.databinding.ItemCartBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class CartAdapter(
    private var itemCartList: MutableList<ItemCart>,
    val removeProductFromFavourite: (ItemCart) -> Unit,
    val increaseItem: (ItemCart) -> Unit,
    val decreaseItem: (ItemCart) -> Unit,

) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private var removedPosition = 0
    lateinit var removedObject: ItemCart


    @SuppressLint("NotifyDataSetChanged")
    fun changeList(list: MutableList<ItemCart>) {
        itemCartList.clear()
        itemCartList = list
        notifyDataSetChanged()
    }

    class ViewHolder(var view: ItemCartBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide
            .with(holder.view.itemCartImage.context)
            .load(itemCartList[position].src)
            .placeholder(R.drawable.tshirt)
            .into(holder.view.itemCartImage)

        holder.view.itemCartTitle.text = itemCartList[position].title
        holder.view.itemCartPrice.text = itemCartList[position].price
        holder.view.sizeTextView.text = itemCartList[position].size
        holder.view.itemCountText.text = itemCartList[position].item_number.toString()

        holder.view.removeBtnTextView.setOnClickListener {
            removedPosition = holder.adapterPosition
            removedObject = itemCartList[holder.adapterPosition]
            itemCartList.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
            Snackbar.make(
               it,
                "${removedObject.title?.slice(0..7)} removed",
                Snackbar.LENGTH_LONG
            ).apply {
                setAction("Undo") {
                    itemCartList.add(removedPosition, removedObject)
                    notifyItemInserted(removedPosition)
                }
                addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    override fun onShown(transientBottomBar: Snackbar?) {
                        super.onShown(transientBottomBar)
                    }

                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                            removeProductFromFavourite(itemCartList[removedPosition])
                        }
                    }
                })
            }.show()
        }

        holder.view.increaseButton.setOnClickListener {

            increaseItem(itemCartList[position])
            holder.view.decreaseButton.isEnabled = itemCartList[position].item_number != 0
        }

        holder.view.decreaseButton.setOnClickListener {
            decreaseItem(itemCartList[position])
            holder.view.decreaseButton.isEnabled = itemCartList[position].item_number != 0
        }

    }


    override fun getItemCount() = itemCartList.size
}