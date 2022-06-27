package com.example.amunstore.ui.profile.address

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amunstore.data.model.address.Address
import com.example.amunstore.databinding.ItemAddressInProfileBinding

class AddressesAdapter(
    private var addressesList: MutableList<Address>,
    private val setAsDefault: (Address) -> Unit,
    private val deleteAddress: (Address) -> Unit
) :
    RecyclerView.Adapter<AddressesAdapter.ProductViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun changeList(newList: MutableList<Address>) {
        addressesList.clear()
        addressesList = newList
        notifyDataSetChanged()
    }

    class ProductViewHolder(var view: ItemAddressInProfileBinding) :
        RecyclerView.ViewHolder(view.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ItemAddressInProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.view.defaultAddressTypeTextView.text = addressesList[position].company ?: "Home"
        holder.view.defaultAddressNameTextView.text =
            addressesList[position].firstName + " " + addressesList[position].lastName
        holder.view.defaultAddressAddress1TextView.text =
            addressesList[position].address1 + ", " + addressesList[position].city + ", " + addressesList[position].country + "."

        holder.view.removeAddressLinearLayout.setOnClickListener {
            deleteAddress(addressesList[position])
        }
        holder.view.setAsDefaultLinearLayout.setOnClickListener {
            setAsDefault(addressesList[position])
        }
    }

    override fun getItemCount() = addressesList.size


}