package com.example.projectapp.ui.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.projectapp.R
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : BaseActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()
        val btn = findViewById<ImageButton>(R.id.loginsighin)
        val btn2sighup = findViewById<ImageButton>(R.id.button_barsignin)
        val btnback = findViewById<ImageButton>(R.id.login_return)
//        val btnforgot : Button = findViewById(R.id.forgotpss)
        // var btn : Button = findViewById(R.id.signIn_Button) //sign-in button
        btn.setOnClickListener { signIn() }
        //turn to the signup area
        btn2sighup.setOnClickListener { startActivity(Intent(this, SignUpActivity::class.java)) }
        btnback.setOnClickListener { finish() }
//        btnforgot.setOnClickListener {
//            startActivity(Intent(this, ForgotPasswordActivity::class.java))}


    }

    private fun signIn() {
        val email: String =
            findViewById<EditText>(R.id.signIn_Email).text.toString().trim() //email field
        val password: String =
            findViewById<EditText>(R.id.signIn_Pass).text.toString().trim() //password field
        //if the credentials are legit
        if (validateForm(email, password)) {
            showProgressDialog(R.string.wait.toString())
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        Toast.makeText(
                            baseContext, "Authentication succeed!.",
                            Toast.LENGTH_SHORT
                        ).show()
                        dismissDialog()
                        startActivity(Intent(this, ClientShoppingActivity::class.java))
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d(TAG, "signInWithEmail:fail")
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        dismissDialog()
                    }

                }
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter email address", true)
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter a password", true)
                false
            }
            else -> {
                true
            }
        }
    }
}