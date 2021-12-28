package com.example.projectapp.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectapp.R
import com.example.projectapp.firestore.FirestoreClass
import com.example.projectapp.models.Product
import com.example.projectapp.ui.adapters.CartListAdapter
import com.example.projectapp.ui.adapters.ProductsListAdapter
import com.example.projectapp.utils.Constants
import com.example.projectapp.utils.SwipeToDeleteCallback
import com.example.projectapp.utils.SwipeToEditCallback
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_address_list.*

/**
 * Address list screen.
 */

class CartListActivity : BaseActivity() {


    private var selectProduct: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list)


        if (intent.hasExtra(Constants.EXTRA_SELECT_ADDRESS)) {
            selectProduct =
                intent.getBooleanExtra(Constants.EXTRA_SELECT_ADDRESS, false)
        }

        setupActionBar()


        if (selectProduct) {
            tv_title.text = resources.getString(R.string.title_select_address)
        }



        getAddressList()

    }
    /**
     * Receive the result from a previous call to
     * {@link #startActivityForResult(Intent, int)}.  This follows the
     * related Activity API as described there in
     * {@link Activity#onActivityResult(int, int, Intent)}.
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.ADD_ADDRESS_REQUEST_CODE) {

                getAddressList()
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // A log is printed when user close or cancel the image selection.
            Log.e("Request Cancelled", "To add the address.")
        }
    }



    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        setSupportActionBar(toolbar_address_list_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_white_24)
        }

        toolbar_address_list_activity.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * A function to get the list of address from cloud firestore.
     */
    private fun getAddressList() {

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        val a = FirebaseAuth.getInstance().currentUser!!.email
        FirestoreClass().getCartList(this@CartListActivity,a.toString())
    }

    /**
     * A function to get the success result of address list from cloud firestore.
     *
     * @param addressList
     */
    fun successAddressListFromFirestore(addressList: ArrayList<Product>) {

        // Hide the progress dialog
        dismissDialog()

        if (addressList.size > 0) {

            rv_address_list.visibility = View.VISIBLE
            tv_no_address_found.visibility = View.GONE

            rv_address_list.layoutManager = LinearLayoutManager(this@CartListActivity)
            rv_address_list.setHasFixedSize(true)

            val addressAdapter = CartListAdapter(this@CartListActivity, addressList, selectProduct)
            rv_address_list.adapter = addressAdapter

            if (!selectProduct) {
                val editSwipeHandler = object : SwipeToEditCallback(this) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                        val adapter = rv_address_list.adapter as CartListAdapter
                        adapter.notifyEditItem(
                            this@CartListActivity,
                            viewHolder.adapterPosition
                        )
                    }
                }
//                val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
//                editItemTouchHelper.attachToRecyclerView(rv_address_list)
//
//
//                val deleteSwipeHandler = object : SwipeToDeleteCallback(this) {
//                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//
//                        // Show the progress dialog.
//                        showProgressDialog(resources.getString(R.string.please_wait))
//
//                        FirestoreClass().deleteProduct(
//                            this@CartListActivity,
//                            addressList[viewHolder.adapterPosition].title
//                        )
//                    }
//                }
//                val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
//                deleteItemTouchHelper.attachToRecyclerView(rv_address_list)
            }
        } else {
            rv_address_list.visibility = View.GONE
            tv_no_address_found.visibility = View.VISIBLE
        }
    }

//    /**
//     * A function notify the user that the address is deleted successfully.
//     */
//    fun deleteAddressSuccess() {
//
//        // Hide progress dialog.
//        dismissDialog()
//
//        Toast.makeText(
//            this@CartListActivity,
//            resources.getString(R.string.err_your_address_deleted_successfully),
//            Toast.LENGTH_SHORT
//        ).show()
//
//        getAddressList()
//    }
}