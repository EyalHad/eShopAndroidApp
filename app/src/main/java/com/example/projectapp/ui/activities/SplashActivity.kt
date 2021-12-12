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


        // TODO (Step 7: Add the file in the custom font file to the assets folder. And add the below line of code to apply it to the title TextView.)
        // Steps for adding the assets folder are :
        // Right click on the "app" package and GO TO ==> New ==> Folder ==> Assets Folder ==> Finish.
        // START
        // This is used to get the file from the assets folder and set it to the title textView.
//        val typeface: Typeface =
//            Typeface.createFromAsset(assets, "carbon bl.ttf")
//        tv_app_name.typeface = typeface
        // END

        //This is used in order to move to the next activity, after fixed time
        //START
        Handler().postDelayed({startActivity(Intent(this, IntroActivity::class.java))
                              finish()}, //User won't be able to return to splash screen
            2500)
    }
    //END
}
// END