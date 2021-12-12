package com.example.projectapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.example.projectapp.R
import com.example.projectapp.firestore.FirestoreClass
import com.example.projectapp.models.Product
import com.google.firebase.auth.FirebaseAuth


class AddProductActivity : BaseActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_add)

    }

//    private fun setupActionBar() {
//
//
//        setSupportActionBar(findViewById(R.id.toolbar_add_product_activity))
//
//        val actionBar = supportActionBar
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true)
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_w)
//        }
//    }

}