package com.example.projectapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.projectapp.R


class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        fullScreen()

        //This is used in order to move to the next activity, after fixed time
        //START
        Handler().postDelayed({startActivity(Intent(this, DashboardActivity::class.java))
                              finish()}, //User won't be able to return to splash screen
            2500)
    }
    //END
}
// END