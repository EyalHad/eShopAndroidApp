package com.example.projectapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.projectapp.R
import android.widget.ImageButton
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.activity_main.*

class IntroActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        supportActionBar?.hide()
        fullScreen()
        //click sign-in button - move to signIn activity

        val signInBtn = findViewById<ImageButton>(R.id.login_intro)
        signInBtn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            // if button is already in selected state and now it is pressed
            // again,then it will reach in not selected state and vice versa
            //signInBtn.isSelected != signInBtn.isSelected
        }

        //click sign-up button - move to signUp activity
        val signUpBtn = findViewById<ImageButton>(R.id.sighupintro)
        signUpBtn.setOnClickListener {
                startActivity(Intent(this, SignUpActivity::class.java))}
    }
}