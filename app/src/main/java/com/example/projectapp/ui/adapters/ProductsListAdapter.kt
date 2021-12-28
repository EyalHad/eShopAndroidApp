package com.example.projectapp.ui.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectapp.R
import com.example.projectapp.models.Address
import com.example.projectapp.models.Product
import com.example.projectapp.ui.activities.AddEditAddressActivity
import com.example.projectapp.utils.Constants
import kotlinx.android.synthetic.main.item_address_layout.view.*
import kotlinx.android.synthetic.main.item_address_layout.view.tv_address_details
import kotlinx.android.synthetic.main.item_address_layout.view.tv_address_full_name
import kotlinx.android.synthetic.main.item_address_layout.view.tv_address_mobile_number
import kotlinx.android.synthetic.main.item_address_layout.view.tv_address_type
import kotlinx.android.synthetic.main.item_product_layout.view.*

/**
 * An adapter class for AddressList adapter.
 */
open class ProductsListAdapter(
    private val context: Context,
    private var list: ArrayList<Product>,
    private val selectAddress: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_product_layout,
                parent,
                false
            )
        )
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            holder.itemView.tv_address_full_name.text = model.title
            holder.itemView.tv_address_type.text = model.price
            holder.itemView.tv_address_details.text =
               model.description
            holder.itemView.tv_address_mobile_number.text = model.stock_quantity
            holder.itemView.tv_address_mobile_number.text = model.stock_quantity

            //for now dont suppport null image field
            Glide.with(context)
                .load(model.image)
                .into(holder.itemView.image_product)

            if (selectAddress) {
                holder.itemView.setOnClickListener {


                }
            }
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * A function to edit the address details and pass the existing details through intent.
     *
     * @param activity
     * @param position
     */
    fun notifyEditItem(activity: Activity, position: Int) {
        val intent = Intent(context, AddEditAddressActivity::class.java)
        intent.putExtra(Constants.EXTRA_ADDRESS_DETAILS, list[position])
        activity.startActivityForResult(intent, Constants.ADD_ADDRESS_REQUEST_CODE)
        notifyItemChanged(position) // Notify any registered observers that the item at position has changed.
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}