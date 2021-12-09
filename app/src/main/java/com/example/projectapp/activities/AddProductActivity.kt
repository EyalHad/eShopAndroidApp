package com.example.projectapp.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.projectapp.R
import com.example.projectapp.activities.firestore.FirestoreClass
import com.example.projectapp.activities.models.Product
import com.google.firebase.auth.FirebaseAuth


class AddProductActivity : BaseActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_add)
        supportActionBar?.hide()
        fullScreen()
        auth = FirebaseAuth.getInstance()
        var btn: Button = findViewById(R.id.signUp_Button)
        btn.setOnClickListener {
            var id: String = findViewById<EditText>(R.id.signUp_first_name).text.toString().trim()
            var name: String = findViewById<EditText>(R.id.signUp_last_name).text.toString().trim()
            var price: String = findViewById<EditText>(R.id.signUp_email).text.toString().trim()
            var image: String = findViewById<EditText>(R.id.signUp_pass).text.toString()
            val product = Product( id, name, price,image)
            FirestoreClass().AddProduct(this, product )

        }

    }
}