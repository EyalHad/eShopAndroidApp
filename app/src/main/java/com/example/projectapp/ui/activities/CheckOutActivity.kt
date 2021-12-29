package com.example.projectapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.projectapp.R

class CheckOutActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        supportActionBar?.hide()
        fullScreen()
        //click sign-in button - move to signIn activity
        val btn_submit = findViewById<Button>(R.id.btn_submit_order)
        btn_submit.setOnClickListener {(submit_order())}

    }

    private fun submit_order() {
        Toast.makeText(this, "Order processed!", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, ClientShoppingActivity::class.java))
        finish()
    }
}