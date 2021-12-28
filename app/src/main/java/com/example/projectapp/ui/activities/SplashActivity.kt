package com.example.projectapp.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.projectapp.R
import com.example.projectapp.firestore.FirestoreClass


@SuppressLint("CustomSplashScreen")
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
        Handler().postDelayed({
            // If the user is logged in once and did not logged out manually from the app.
            // So, next time when the user is coming into the app user will be redirected to MainScreen.
            // If user is not logged in or logout manually then user will  be redirected to the Login screen as usual.
            startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
            // Get the current logged in user id

            val currentUserID = FirestoreClass().getCurrentUserID()

            if (currentUserID.isNotEmpty()) {
                // Launch ClientShopping screen.
                startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
            } else {
                // Launch the Login Activity
                startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
            }
                              finish()
                              }, //User won't be able to return to splash screen
            2500)
    }

    //END
}
// END

