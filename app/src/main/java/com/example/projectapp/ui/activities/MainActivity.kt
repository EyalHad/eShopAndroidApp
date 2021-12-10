package com.example.projectapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.projectapp.R

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        fullScreen()
        //click sign-in button - move to signIn activity
        val signInBtn = findViewById<Button>(R.id.intro_signInButton)
        signInBtn.setOnClickListener {startActivity(Intent(this, AddProductActivity::class.java))}

    }
}