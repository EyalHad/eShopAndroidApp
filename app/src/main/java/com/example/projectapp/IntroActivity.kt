package com.example.projectapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.projectapp.R


class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        //click sign-in button - move to signIn activity
        val signInBtn = findViewById<Button>(R.id.intro_signInButton)
        signInBtn.setOnClickListener {startActivity(Intent(this, SignInActivity::class.java))}

        //click sign-up button - move to signUp activity
        val signUpBtn = findViewById<Button>(R.id.intro_signUpButton)
        signUpBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))}
        }
    }
