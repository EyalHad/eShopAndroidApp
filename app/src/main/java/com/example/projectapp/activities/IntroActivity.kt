package com.example.projectapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.projectapp.R

class IntroActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        supportActionBar?.hide()
        fullScreen()
        //click sign-in button - move to signIn activity
        val signInBtn = findViewById<Button>(R.id.intro_signInButton)
        signInBtn.setOnClickListener {startActivity(Intent(this, SignInActivity::class.java))}

        //click sign-up button - move to signUp activity
        val signUpBtn = findViewById<Button>(R.id.intro_signUpButton)
        signUpBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))}
        }
    }
