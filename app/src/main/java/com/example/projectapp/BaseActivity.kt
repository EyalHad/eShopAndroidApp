package com.example.projectapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.projectapp.R
import com.google.android.material.snackbar.Snackbar
//import com.google.firebase.auth.FirebaseAuth
//import kotlinx.android.synthetic.main.dialog_progress.*

/**
 * This class will inherit to all other activities, giving general used functions.
 */
open class BaseActivity : AppCompatActivity() {

    private lateinit var progressDialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    /**
     * Show current action in a dialog.
     */
    fun showProgressDialog(text:String){
//        progressDialog = Dialog(this)
//        progressDialog.setContentView(R.layout.dialog_progress)
//        progressDialog.progressText.text = text
//        progressDialog.show()
    }

    /**
     * Dismiss current dialog.
     */
    fun dismissDialog(){
        progressDialog.dismiss()
    }

    /**
     * Return current user Id
     */
//    fun getCurrentUserId() : String{
////        return FirebaseAuth.getInstance().currentUser!!.uid
//    }

    /**
     * Print general error in a snackBar.
     */
    fun showErrorSnackBar(message : String){
        val snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_error_color))
        snackBar.show()
    }
}