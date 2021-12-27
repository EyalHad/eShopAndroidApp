package com.example.projectapp.ui.activities

import android.app.Dialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.projectapp.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_base.*

//import com.google.firebase.auth.FirebaseAuth
//import kotlinx.android.synthetic.main.dialog_progress.*

/**
 * A base activity class is used to define the functions and members which we will use in all the activities.
 * It inherits the AppCompatActivity class so in other activity class we will replace the AppCompatActivity with BaseActivity.
 */
open class BaseActivity : AppCompatActivity() {

    // A global variable for double back press feature.
    private var doubleBackToExit = false

    /**
     * This is a progress dialog instance which we will initialize later on.
     */
    private lateinit var progressDialog: Dialog

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_base)
//    }

    /**
     * Show current action in a dialog.
     */
    fun showProgressDialog(text: String) {
        progressDialog = Dialog(this)

        /*
            Set the screen content from a layout resource.
            The resource will be inflated, adding all top-level views to the screen.
         */
        progressDialog.setContentView(R.layout.dialog_progress)
//        progressDialog.tv_progress_text.text = text

        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

        // start the dialog and display it on screen
        progressDialog.show()
    }

    /**
     * This function is used to dismiss the progress dialog if it is visible to user.
     */
    fun dismissDialog() {
        progressDialog.dismiss()
    }


    /**
     * A function to show the success and error messages in snack bar component.
     */
    fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if (errorMessage) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorSnackBarError
                )
            )
        } else {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorSnackBarSuccess
                )
            )
        }
        snackBar.show()
    }

    /**
     * This is used to hide the status bar and make the splash screen as a full screen activity.
     */
    fun fullScreen() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    /**
     * A function to implement the double back press feature to exit the app.
     */
    fun doubleBackToExit() {

        if (doubleBackToExit) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExit = true

        Toast.makeText(
            this,
            resources.getString(R.string.please_click_back_again_to_exit),
            Toast.LENGTH_SHORT
        ).show()

        @Suppress("DEPRECATION")
        Handler().postDelayed({ doubleBackToExit = false }, 2000)
    }
}