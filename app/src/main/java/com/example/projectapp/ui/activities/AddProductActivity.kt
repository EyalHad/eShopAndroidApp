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
        supportActionBar?.hide()
        fullScreen()
        auth = FirebaseAuth.getInstance()
        var btn: Button = findViewById(R.id.add_product_add_product_btn)
        btn.setOnClickListener {
            var id: String = findViewById<EditText>(R.id.signUp_first_name).text.toString().trim()
            var name: String = findViewById<EditText>(R.id.signUp_last_name).text.toString().trim()
            var price: String = findViewById<EditText>(R.id.signUp_email).text.toString().trim()
            var image: String = findViewById<EditText>(R.id.add_product_image_url).text.toString()
            //if product's fields are correctly filled
            if(validateForm( id, name, price)){
                val product = Product( id, name, price,image)
                FirestoreClass().addProduct(this, product )
            }


        }

        var imageBtn: Button = findViewById(R.id.uploadImageBtn)
        imageBtn.setOnClickListener {
            startActivity(Intent(this ,StorageActivity::class.java))
        }

    }

    private fun validateForm( id:String, name:String, price:String ): Boolean {
        return when {
            TextUtils.isEmpty(id) -> {
                showErrorSnackBar("ID for the product is missing")
                false
            }
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Name of the product is missing")
                false
            }
            TextUtils.isEmpty(price) -> {
                showErrorSnackBar("product pricing is missing")
                false
            }
            else -> {
                true
            }
        }
    }
}