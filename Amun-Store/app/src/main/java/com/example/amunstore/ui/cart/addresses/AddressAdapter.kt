package com.example.amunstore.ui.cart.addresses

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amunstore.R
import com.example.amunstore.databinding.ItemAddressBinding
import com.example.amunstore.data.model.address.Address

class AddressAdapter(
    private var addressList: MutableList<Address>,
    private val itemClick : (Address) -> Unit
) :
    RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    private var selected = 0
    private var selectedItem: ViewHolder? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(newList: MutableList<Address>) {
        addressList.clear()
        addressList = newList
        notifyDataSetChanged()
    }

    class ViewHolder(var view: ItemAddressBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAddressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.view.addressTypeTextView.text = addressList[position].name
        holder.view.addressNameTextView.text = addressList[position].address1

        holder.view.circleImageView.setOnClickListener {

            selected = position
            // unselect previous
            selectedItem?.view?.circleImageView?.setImageResource(R.drawable.empty_circle);

            selectedItem = holder
            itemClick(addressList[position])

            selectedItem?.view?.circleImageView?.setImageResource(R.drawable.checked_circle);
        }

    }

    override fun getItemCount() = addressList.size
}