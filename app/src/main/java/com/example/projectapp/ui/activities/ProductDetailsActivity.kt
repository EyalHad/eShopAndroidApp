package com.example.projectapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.projectapp.R
import com.example.projectapp.firestore.FirestoreClass
import com.example.projectapp.models.Product
import com.example.projectapp.utils.Constants
import kotlinx.android.synthetic.main.activity_product_details.*


class ProductDetailsActivity : AppCompatActivity() {

    private  lateinit var productDetails: Product
    // A global variable for product id.
    private var productId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        if (intent.hasExtra(Constants.EXTRA_PRODUCT_ID)) {
            productId = intent.getStringExtra(Constants.EXTRA_PRODUCT_ID)!!
        }

        setupActionBar()
    }

//    override fun onClick(v: View?) {
//        if (v != null) {
//            when (v.id) {
//
//                R.id.btn_add_to_cart -> {
//                    addToCart()
//                }
//
//                R.id.btn_go_to_cart->{
//                    startActivity(Intent(this@ProductDetailsActivity, CartListActivity::class.java))
//                }
//            }
//        }
//    }

    /**
     * A function to prepare the cart item to add it to the cart in cloud firestore.
     */
//    private fun addToCart() {
//
//        val addToCart = Cart(
//            FirestoreClass().getCurrentUserID(),
//            mProductId,
//            mProductDetails.title,
//            mProductDetails.price,
//            mProductDetails.image,
//            Constants.DEFAULT_CART_QUANTITY
//        )
//
//        // Show the progress dialog
//        showProgressDialog(resources.getString(R.string.please_wait))
//
//        FirestoreClass().addCartItems(this@ProductDetailsActivity, addToCart)
//    }

    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        setSupportActionBar(toolbar_product_details_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_white_24)
        }

        toolbar_product_details_activity.setNavigationOnClickListener { onBackPressed() }
    }
}